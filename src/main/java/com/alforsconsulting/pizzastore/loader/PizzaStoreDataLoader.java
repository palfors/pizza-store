package com.alforsconsulting.pizzastore.loader;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.PizzaStore;
import com.alforsconsulting.pizzastore.customer.Customer;
import com.alforsconsulting.pizzastore.menu.MenuItem;
import com.alforsconsulting.pizzastore.menu.MenuItemType;
import com.alforsconsulting.pizzastore.menu.detail.MenuItemDetail;
import com.alforsconsulting.pizzastore.menu.pizza.topping.ToppingPlacement;
import com.alforsconsulting.pizzastore.order.Order;
import com.alforsconsulting.pizzastore.order.line.OrderLine;
import com.alforsconsulting.pizzastore.order.line.detail.OrderLineDetail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.ApplicationContext;

import java.util.*;
import java.util.concurrent.*;

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
        loader.placeOrders(threadCount, sleepTime, maxOrders, maxLineCount, maxLineDetailCount);

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
        logger.debug("initialize entry");
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

    public void placeOrders(int threadCount, long sleepTime, int maxOrders, int maxLineCount, int maxLineDetailCount) {
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

        fixedThreadPoolService.shutdown();
        while (!fixedThreadPoolService.isTerminated()) {
        }

        System.out.println("All threads are done!");
    }

    private PizzaStore createPizzaStore(String name) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // check if this store exists. if not, create it
        PizzaStore pizzaStore = null;
        Query query = session.createQuery("from PizzaStore where name = :param ");
        query.setParameter("param", name);
        List<PizzaStore> list = (List<PizzaStore>) query.list();
        if (list != null && list.size() > 0) {
            pizzaStore = list.get(0);
        } else {
            // create store to add
            pizzaStore = (PizzaStore) applicationContext.getBean("pizzaStore");
            pizzaStore.generateId();
            pizzaStore.setName(name);
            session.save(pizzaStore);

            session.getTransaction().commit();
        }
        session.close();

        return pizzaStore;
    }

    private Customer createCustomer(String name) {
        Session session = sessionFactory.openSession();

        Customer customer = null;
        // check if this customer exists. if not, create it
        Query query = session.createQuery("from Customer where name = :param ");
        query.setParameter("param", name);
        List<Customer> list = (List<Customer>) query.list();
        if (list != null && list.size() > 0) {
            customer = list.get(0);
        } else {
            // create customers for order
            session.beginTransaction();

            customer = (Customer) applicationContext.getBean("customer");
            customer.setName(name);
            logger.debug("Saved customer [{}]", customer);
            session.save(customer);

            session.getTransaction().commit();
        }

        session.close();

        return customer;
    }

    private List<MenuItem> loadMenuItems() {
        Session session = this.sessionFactory.openSession();
        List<MenuItem> menuItems = (List<MenuItem>) session.createQuery( "from GenericMenuItem" ).list();
        logger.debug("Loaded menuItems:");
        for ( MenuItem item : menuItems ) {
            logger.debug(item);
        }
        session.close();

        return menuItems;
    }

    private List<MenuItemDetail> loadMenuItemDetails() {
        Session session = this.sessionFactory.openSession();
        List<MenuItemDetail> details = (List<MenuItemDetail>) session.createQuery( "from MenuItemDetail" ).list();
        logger.debug("Loaded menuItemDetails:");
        for ( MenuItemDetail detail : details ) {
            logger.debug(detail);
        }
        session.close();

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
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            Order order = createOrder(maxLineCount, maxLineDetailCount);

            // persist everything
            logger.debug("Preparing to save order [{}]", order);
            session.save(order);
            logger.debug("Saved order [{}]", order);

            for (OrderLine orderLine : order.getOrderLines()) {
                // update the orderId now that hibernate has generated it (after saving)
                orderLine.setOrderId(order.getOrderId());
                logger.debug("Preparing to save order line [{}]", orderLine);
                session.save(orderLine);
                logger.debug("Saved orderLine [{}]", orderLine);
                for (OrderLineDetail orderLineDetail : orderLine.getOrderLineDetails()) {
                    // update the orderId now that hibernate has generated it (after saving)
                    orderLineDetail.setOrderLineId(orderLine.getOrderLineId());
                    logger.debug("Preparing to save order line detail [{}]", orderLineDetail);
                    session.save(orderLineDetail);
                    logger.debug("Saved orderLineDetail [{}]", orderLineDetail);
                }
            }

            session.getTransaction().commit();
            session.close();
        }

        private Order createOrder(int lineCount, int lineDetailCount) {

            Order order = (Order) applicationContext.getBean("order");
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
            OrderLine orderLine = (OrderLine) applicationContext.getBean("orderLine");
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
            OrderLineDetail orderLineDetail = (OrderLineDetail) applicationContext.getBean("orderLineDetail");
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
