package com.alforsconsulting.pizzastore.customer.dao;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.customer.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by palfors on 5/15/16.
 */
public class CustomerJDBCTemplateTest {
    private static final Logger logger = LogManager.getLogger();

    private static ApplicationContext context = null;
    private static CustomerJDBCTemplate jdbcTemplate = null;
    private static Customer customer = null;

    @BeforeClass
    public static void setupClass() {
        logger.debug("setupClass entry");
        context = AppContext.getInstance().getContext();
        jdbcTemplate = (CustomerJDBCTemplate)context.getBean("customerJDBCTemplate");

        customer = (Customer) context.getBean("customer");
        customer.setName("JUnit-Customer");
    }

    @AfterClass
    public static void teardownClass() {
        logger.debug("teardownClass entry");

        jdbcTemplate.delete(customer);
    }

    @Before
    public void setupTest() {
    }

    @After
    public void teardownTest() {
    }

    @Test
    public void create() {
        jdbcTemplate.create(customer);
        // verify the customer exists
        Customer junitCustomer =
                jdbcTemplate.getCustomer(customer.getCustomerId());
        assertNotNull("Unable to find customer [JUnit-Customer] created in test!", junitCustomer);
    }

    @Test
    public void update() {
        String newName = "JUnit-updated-customer";
        customer.setName(newName);
        jdbcTemplate.update(customer);

        Customer junitCustomer =
                jdbcTemplate.getCustomer(customer.getCustomerId());
        assertNotNull("Unable to find customer [" + customer.getCustomerId() + "]", junitCustomer);
        assertEquals(newName, junitCustomer.getName());

    }

    @Test
    public void list() {
        List<Customer> customers = jdbcTemplate.list();

        assertTrue(customers.size() > 0);
    }

    @Test
    public void delete() {
        Customer deleteCustomer = (Customer) context.getBean("customer");
        deleteCustomer.setName("deleteMe");

        jdbcTemplate.create(deleteCustomer);
        Customer deleted = jdbcTemplate.getCustomer(deleteCustomer.getCustomerId());
        assertNotNull("Unable to find customer [" + deleted.getCustomerId() + "]", deleted);

        jdbcTemplate.delete(deleteCustomer);
        deleted = jdbcTemplate.getCustomer(deleteCustomer.getCustomerId());
        assertNull("Expecting customer [" + deleteCustomer.getCustomerId() + "] to be deleted", deleted);
    }

}
