package com.alforsconsulting.pizzastore.loader;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.PizzaStore;
import com.alforsconsulting.pizzastore.StoreUtil;
import com.alforsconsulting.pizzastore.customer.Customer;
import com.alforsconsulting.pizzastore.customer.CustomerUtil;
import com.alforsconsulting.pizzastore.menu.MenuItem;
import com.alforsconsulting.pizzastore.menu.MenuItemType;
import com.alforsconsulting.pizzastore.menu.MenuItemUtil;
import com.alforsconsulting.pizzastore.menu.detail.MenuItemDetail;
import com.alforsconsulting.pizzastore.menu.detail.MenuItemDetailUtil;
import com.alforsconsulting.pizzastore.menu.pizza.ToppingPlacement;
import com.alforsconsulting.pizzastore.order.Order;
import com.alforsconsulting.pizzastore.order.OrderUtil;
import com.alforsconsulting.pizzastore.order.line.OrderLine;
import com.alforsconsulting.pizzastore.order.line.OrderLineUtil;
import com.alforsconsulting.pizzastore.order.line.detail.OrderLineDetail;
import com.alforsconsulting.pizzastore.order.line.detail.OrderLineDetailUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by palfors on 5/18/16.
 */
public class PizzaStoreDataLoader {
    private static final Logger logger = LogManager.getLogger();
    protected SessionFactory sessionFactory;
    protected static ApplicationContext applicationContext;

    // amount of time to sleep between creation of orders
    private static long sleepTime = 0;
    // the maximum number of orders to create
    private static int maxOrders = 10;
    // the maximum number of orders to create
    private static int maxLineCount = 3;
    // the maximum number of orders to create
    private static int maxLineDetailCount = 5;
    // the number of threads to spawn
    private static int threadCount = 5;

    public static void main(String[] args) {
        if (args != null) {
            for (String arg : args) {
                processArgument(arg);
            }
        }

        PizzaStoreDataLoader loader = new PizzaStoreDataLoader();
        try {
            loader.placeOrders(threadCount, sleepTime, maxOrders, maxLineCount, maxLineDetailCount);
        } catch (Exception e) {
            System.out.println("Loader caught exception! [" + e.getMessage() + "]");
            e.printStackTrace();
        }

        System.out.println("Done!");
    }

    private static void processArgument(String arg) {
        if (arg != null) {
            String[] split = arg.split("=");
            if (split.length == 2) {
                String key = split[0];
                String value = split[1];

                switch (key) {
                    case "sleep" :
                        sleepTime=Long.parseLong(value);
                        System.out.println("sleep: [" + sleepTime + "]");
                        break;
                    case "orders" :
                        maxOrders=Integer.parseInt(value);
                        System.out.println("orders: [" + maxOrders + "]");
                        break;
                    case "threads" :
                        threadCount=Integer.parseInt(value);
                        System.out.println("threads: [" + threadCount + "]");
                        break;
                    default :
                        System.out.println("Invalid argument [" + arg + "]");
                        break;
                }
            }
            else
            {
                System.out.println("Invalid argument [" + arg + "]");
            }
        }
    }

    private void initialize() {
        logger.info("initialize entry");
        applicationContext = AppContext.getInstance().getContext();

        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate/hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            logger.debug("sessionFactory failed with exception [{}][{}]",e.getClass().getName(), e.getMessage());
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
            throw e;
        }

    }

    public void cleanup() throws Exception {
        logger.info("cleanup entry");
        if ( sessionFactory != null ) {
            logger.info("closing sessionFactory: [{}]", sessionFactory);
            sessionFactory.close();
        }
    }

    public void placeOrders(int threadCount, long sleepTime, int maxOrders, int maxLineCount, int maxLineDetailCount) throws Exception {
        long startTime = System.currentTimeMillis();

        // initialize class variables
        initialize();

        // create a store to use for the new orders for easy cleanup
        PizzaStore store = createPizzaStore("dataload-store");

        Customer customer = createCustomer("dataload-customer");

        // load the menu that will be used for orders
        List<MenuItem> menuItems = loadMenuItems();
        List<MenuItemDetail> menuItemDetails = loadMenuItemDetails();

        // create a fixed thread pool for order generation
        ExecutorService fixedThreadPoolService = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            fixedThreadPoolService.execute(
                    new PlaceOrderThread(sessionFactory,
                                            store.getStoreId(),
                                            customer.getCustomerId(),
                                            menuItems,
                                            menuItemDetails,
                                            maxLineCount,
                                            maxLineDetailCount));
        }

        fixedThreadPoolService.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!fixedThreadPoolService.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("still waiting for pool to shutdown!");
                fixedThreadPoolService.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!fixedThreadPoolService.awaitTermination(10, TimeUnit.SECONDS))
                    System.out.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            System.out.println("InterruptedException [" + ie + "] occurred closing down thread pool!");
            // (Re-)Cancel if current thread also interrupted
            fixedThreadPoolService.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }

        System.out.println("All threads are done!");

        cleanup();
    }

    private PizzaStore createPizzaStore(String name) {
        // check if this store exists. if not, create it
        PizzaStore store = StoreUtil.getStore(name);
        if (store == null) {
            // create store to add
            store = StoreUtil.create(name);
            StoreUtil.save(store);
            logger.debug("Created store [{}]", store);
        }

        return store;
    }

    private Customer createCustomer(String name) {

        Customer customer = CustomerUtil.getCustomer(name);
        if (customer == null) {
            customer = CustomerUtil.create(name);
            CustomerUtil.save(customer);
            logger.debug("Created customer [{}]", customer);
        }

        return customer;
    }

    private List<MenuItem> loadMenuItems() {
        List<MenuItem> menuItems = MenuItemUtil.getMenuItems();
        logger.debug("Loaded menuItems:");
        for ( MenuItem item : menuItems ) {
            logger.debug(item);
        }

        return menuItems;
    }

    private List<MenuItemDetail> loadMenuItemDetails() {
        List<MenuItemDetail> details = MenuItemDetailUtil.getMenuItemDetails();
        logger.debug("Loaded menuItemDetails:");
        for ( MenuItemDetail detail : details ) {
            logger.debug(detail);
        }

        return details;
    }

    private class PlaceOrderThread implements Runnable {
        private SessionFactory sessionFactory;
        private long storeId;
        private long customerId;
        private List<MenuItem> menuItems;
        private List<MenuItemDetail> menuItemDetails;
        private int maxLineCount;
        private int maxLineDetailCount;

        public PlaceOrderThread(SessionFactory sessionFactory,
                                long storeId,
                                long customerId,
                                List<MenuItem> menuItems,
                                List<MenuItemDetail> menuItemDetails,
                                int maxLineCount,
                                int maxLineDetailCount) {
            this.sessionFactory = sessionFactory;
            this.storeId = storeId;
            this.customerId = customerId;
            this.menuItems = menuItems;
            this.menuItemDetails = menuItemDetails;
            this.maxLineCount = maxLineCount;
            this.maxLineDetailCount = maxLineDetailCount;
        }

        @Override
        public void run() {
            logger.info("Thread using sessionFactory: [{}]", sessionFactory);

            Session session = sessionFactory.openSession();
            session.beginTransaction();

            Order order = createOrder(maxLineCount, maxLineDetailCount);

            // persist everything
            logger.debug("Preparing to save order [{}]", order);
            OrderUtil.save(session, order);
            logger.debug("Saved order [{}]", order);

            session.getTransaction().commit();
            session.close();
        }

        private Order createOrder(int lineCount, int lineDetailCount) {

            Order order = OrderUtil.newOrder();
            order.setStoreId(storeId);
            order.setCustomerId(customerId);

            // calculate price as the sum of each line
            double orderPrice = 0;
            // add some line(s)
            for (int i = 0 ; i < lineCount; i++) {
                OrderLine orderLine = createOrderLine(order.getOrderId(), lineDetailCount);
                order.addLine(orderLine);

                orderPrice += orderLine.getPrice();
            }

            order.setPrice(orderPrice);

            return order;
        }

        private OrderLine createOrderLine(long orderId, int lineDetailCount) {
            OrderLine orderLine = OrderLineUtil.newOrderLine();
            orderLine.setOrderId(orderId);
            MenuItem menuItem = chooseMenuItem();
            orderLine.setMenuItemId(menuItem.getMenuItemId());
            int quantity = new Long(Math.round(new Double(Math.random()+1))).intValue();
            orderLine.setQuantity(quantity);

            double linePrice = menuItem.getPrice();
            if (MenuItemType.PIZZA.getBeanName().equals(menuItem.getMenuItemType())) {
                // add some line detail(s)
                for (int i = 0 ; i < lineDetailCount; i++) {
                    OrderLineDetail orderLineDetail =
                            createOrderLineDetail(orderLine.getOrderLineId(), menuItem);
                    orderLine.addOrderLineDetail(orderLineDetail);

                    linePrice += orderLineDetail.getPrice();
                }
            }

            orderLine.setPrice(linePrice*quantity);

            return orderLine;
        }

        private OrderLineDetail createOrderLineDetail(long orderLineId, MenuItem menuItem) {
            OrderLineDetail orderLineDetail = OrderLineDetailUtil.newOrderLineDetail();
            orderLineDetail.setOrderLineId(orderLineId);

            MenuItemDetail menuItemDetail = chooseMenuItemDetail();
            orderLineDetail.setMenuItemDetailId(menuItemDetail.getMenuItemDetailId());

            orderLineDetail.setPlacement(chooseToppingPlacement().name());
            orderLineDetail.setPrice(menuItemDetail.getPrice());

            return orderLineDetail;
        }

        // randomly choose a menu item
        private MenuItem chooseMenuItem() {
            int count = this.menuItems.size()-1;
            int index = new Long(Math.round(new Double(Math.random()*count))).intValue();

            return menuItems.get(index);
        }

        private MenuItemDetail chooseMenuItemDetail() {
            int count = this.menuItemDetails.size()-1;
            int index = new Long(Math.round(new Double(Math.random()*count))).intValue();

            return menuItemDetails.get(index);
        }

        private ToppingPlacement chooseToppingPlacement() {
            int count = ToppingPlacement.values().length-1;
            int index = new Long(Math.round(new Double(Math.random()*count))).intValue();

            ToppingPlacement[] placements = ToppingPlacement.values();
            return placements[index];
        }
    }




}
