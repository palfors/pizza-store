package com.alforsconsulting.pizzastore.order.dao;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.PizzaStore;
import com.alforsconsulting.pizzastore.customer.Customer;
import com.alforsconsulting.pizzastore.customer.dao.CustomerJDBCTemplate;
import com.alforsconsulting.pizzastore.dao.PizzaStoreJDBCTemplate;
import com.alforsconsulting.pizzastore.menu.dao.MenuItemJDBCTemplate;
import com.alforsconsulting.pizzastore.menu.pizza.Pizza;
import com.alforsconsulting.pizzastore.order.Order;
import com.alforsconsulting.pizzastore.order.OrderLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by palfors on 5/15/16.
 */
public class OrderJDBCTemplateTest {
    private static final Logger logger = LogManager.getLogger();

    private static ApplicationContext context = null;
    private static OrderJDBCTemplate jdbcTemplate = null;
    private static Order order = null;

    private static OrderLineJDBCTemplate orderLineJDBCTemplate = null;
    private static MenuItemJDBCTemplate menuItemJDBCTemplate = null;

    private static CustomerJDBCTemplate customerJDBCTemplate = null;
    private static Customer customer = null;

    private static PizzaStoreJDBCTemplate pizzaStoreJDBCTemplate = null;
    private static PizzaStore pizzaStore = null;

    @BeforeClass
    public static void setupClass() {
        logger.debug("setupClass entry");
        context = AppContext.getInstance().getContext();

        pizzaStore = (PizzaStore) context.getBean("pizzaStore");
        pizzaStore.setName("JUnit-Store");
        pizzaStoreJDBCTemplate = (PizzaStoreJDBCTemplate) context.getBean("pizzaStoreJDBCTemplate");
        pizzaStoreJDBCTemplate.create(pizzaStore);

        customer = (Customer) context.getBean("customer");
        customer.setName("JUnit-Customer");
        customerJDBCTemplate = (CustomerJDBCTemplate) context.getBean("customerJDBCTemplate");
        customerJDBCTemplate.create(customer);

        jdbcTemplate = (OrderJDBCTemplate) context.getBean("orderJDBCTemplate");
        orderLineJDBCTemplate = (OrderLineJDBCTemplate) context.getBean("orderLineJDBCTemplate");
        menuItemJDBCTemplate = (MenuItemJDBCTemplate) context.getBean("menuItemJDBCTemplate");
    }

    @AfterClass
    public static void teardownClass() {
        logger.debug("teardownClass entry");
        logger.debug("deleting orderlines for order: {}", order);
        deleteOrderLines(order);
        logger.debug("deleting order: {}", order);
        jdbcTemplate.delete(order);
        logger.debug("deleting customer: {}", customer);
        customerJDBCTemplate.delete(customer);
        logger.debug("deleting pizzaStore: {}", pizzaStore);
        pizzaStoreJDBCTemplate.delete(pizzaStore);
    }

    @Before
    public void setupTest() {
    }

    @After
    public void teardownTest() {
    }

    @Test
    public void create() {
        order = (Order) context.getBean("order");
        order.setStoreId(pizzaStore.getStoreId());
        order.setCustomer(customer);
        order.setPrice(12.35);
        jdbcTemplate.create(order);

        logger.info("created order: {}", order);

        // verify the order exists
        Order junitOrder = jdbcTemplate.getOrder(order.getOrderId());
        assertNotNull("Unable to find order [JUnit-Order] created in test!", junitOrder);

        // add lines
        Pizza pizza = (Pizza) context.getBean("pizza");
        pizza.setName("JUnit-Pizza");
        menuItemJDBCTemplate.create(pizza);

        OrderLine orderLine = (OrderLine) context.getBean("orderLine");
        orderLine.setOrderId(order.getOrderId());
        orderLine.setMenuItem(pizza);
        orderLine.setQuantity(1);
        orderLine.setPrice(pizza.getPrice());
        order.addLine(orderLine);

        orderLineJDBCTemplate.create(orderLine);
        OrderLine junitOrderLine =
                orderLineJDBCTemplate.getOrderLine(orderLine.getOrderLineId());
        assertNotNull("Unable to find order line created in test!", junitOrderLine);
    }

    @Test
    public void update() {
        double newPrice = 15.75;
        order.setPrice(newPrice);
        jdbcTemplate.update(order);

        Order junitOrder =
                jdbcTemplate.getOrder(order.getOrderId());
        assertNotNull("Unable to find order [" + order.getOrderId() + "]", junitOrder);
        assertEquals(newPrice, junitOrder.getPrice(), 0);
    }

    @Test
    public void list() {
        List<Order> orders = jdbcTemplate.list();

        assertTrue(orders.size() > 0);
    }

    @Test
    public void delete() {
        Order deleteOrder = (Order) context.getBean("order");
        deleteOrder.setStoreId(pizzaStore.getStoreId());
        deleteOrder.setCustomer(customer);
        deleteOrder.setPrice(13.45);

        jdbcTemplate.create(deleteOrder);
        Order deleted = jdbcTemplate.getOrder(deleteOrder.getOrderId());
        assertNotNull("Unable to find order [" + deleted.getOrderId() + "]", deleted);

        deleteOrderLines(deleteOrder);

        jdbcTemplate.delete(deleteOrder);
        deleted = jdbcTemplate.getOrder(deleteOrder.getOrderId());
        assertNull("Expecting order [" + deleteOrder.getOrderId() + "] to be deleted", deleted);
    }

    private static void deleteOrderLines(Order order) {
        logger.debug("deleteOrderLines entry");
        for (OrderLine line : order.getOrderLines()) {
            logger.debug("deleting orderline: {}", line);
            orderLineJDBCTemplate.delete(line);
            logger.debug("deleting menuItem: {}", line.getMenuItem().getMenuItemId());
            menuItemJDBCTemplate.delete(line.getMenuItem().getMenuItemId());
        }
    }

}
