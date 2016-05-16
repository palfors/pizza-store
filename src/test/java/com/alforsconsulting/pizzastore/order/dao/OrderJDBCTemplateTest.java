package com.alforsconsulting.pizzastore.order.dao;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.PizzaStore;
import com.alforsconsulting.pizzastore.customer.Customer;
import com.alforsconsulting.pizzastore.customer.dao.CustomerJDBCTemplate;
import com.alforsconsulting.pizzastore.dao.PizzaStoreJDBCTemplate;
import com.alforsconsulting.pizzastore.order.Order;
import com.alforsconsulting.pizzastore.spring.PizzaStoreSpringAnnotationTest;
import org.junit.*;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by palfors on 5/15/16.
 */
public class OrderJDBCTemplateTest {
    private static ApplicationContext context = null;
    private static OrderJDBCTemplate jdbcTemplate = null;
    private static Order order = null;

    private static CustomerJDBCTemplate customerJDBCTemplate = null;
    private static Customer customer = null;

    private static PizzaStoreJDBCTemplate pizzaStoreJDBCTemplate = null;
    private static PizzaStore pizzaStore = null;

    @BeforeClass
    public static void setupClass() {
        context = AppContext.getInstance().getContext();

        pizzaStore = (PizzaStore) context.getBean("pizzaStore");
        pizzaStore.setName("JUnit-Store");
        pizzaStoreJDBCTemplate = (PizzaStoreJDBCTemplate)context.getBean("pizzaStoreJDBCTemplate");
        pizzaStoreJDBCTemplate.create(pizzaStore);
System.out.println("setupClass: pizzaStore: id: " + pizzaStore.getStoreId());

        customer = (Customer) context.getBean("customer");
        customer.setName("JUnit-Customer");
        customerJDBCTemplate = (CustomerJDBCTemplate)context.getBean("customerJDBCTemplate");
        customerJDBCTemplate.create(customer);

        order = (Order) context.getBean("order");
        order.setStoreId(pizzaStore.getStoreId());
System.out.println("setupClass: order storeId:" + order.getStoreId());
        order.setCustomer(customer);
        order.setPrice(12.35);
        jdbcTemplate = (OrderJDBCTemplate)context.getBean("orderJDBCTemplate");
    }

    @AfterClass
    public static void teardownClass() {
        jdbcTemplate.delete(order);
        customerJDBCTemplate.delete(customer);
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
        jdbcTemplate.create(order);
        // verify the customer exists
        Order junitOrder =
                jdbcTemplate.getOrder(order.getOrderId());
System.out.println("created junitOrder: " + junitOrder);

        assertNotNull("Unable to find order [JUnit-Order] created in test!", junitOrder);
    }

    @Test
    public void update() {
        double newPrice = 15.75;
        order.setPrice(newPrice);
        jdbcTemplate.update(order);
System.out.println("updated order: " + order);

        Order junitOrder =
                jdbcTemplate.getOrder(order.getOrderId());
System.out.println("updated junitOrder: " + junitOrder);
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

        jdbcTemplate.delete(deleteOrder);
        deleted = jdbcTemplate.getOrder(deleteOrder.getOrderId());
        assertNull("Expecting order [" + deleteOrder.getOrderId() + "] to be deleted", deleted);
    }

}
