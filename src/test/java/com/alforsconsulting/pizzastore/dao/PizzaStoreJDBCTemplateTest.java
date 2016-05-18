package com.alforsconsulting.pizzastore.dao;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.PizzaStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by palfors on 5/15/16.
 */
public class PizzaStoreJDBCTemplateTest {
    private static final Logger logger = LogManager.getLogger();

    private static ApplicationContext context = null;
    private static PizzaStoreJDBCTemplate jdbcTemplate = null;
    private static PizzaStore pizzaStore = null;

    @BeforeClass
    public static void setupClass() {
        context = AppContext.getInstance().getContext();
        jdbcTemplate = (PizzaStoreJDBCTemplate) context.getBean("pizzaStoreJDBCTemplate");

        pizzaStore = (PizzaStore) context.getBean("pizzaStore");
        pizzaStore.generateId();
        pizzaStore.setName("JUnit-Store");
        logger.debug("setupClass(): create pizzaStore [{}]", pizzaStore);
    }

    @AfterClass
    public static void teardownClass() {
        logger.debug("teardownClass(): pizzaStore [{}]", pizzaStore);
        jdbcTemplate.delete(pizzaStore);
    }

    @Before
    public void setupTest() {
    }

    @After
    public void teardownTest() {
    }

    @Test
    public void create() {
        logger.debug("Creating pizzaStore [{}]", pizzaStore);
        jdbcTemplate.create(pizzaStore);
        // verify the store exists
        PizzaStore junitStore =
                jdbcTemplate.getPizzaStore(pizzaStore.getStoreId());
        assertNotNull("Unable to find store [JUnit-Store] created in test!", junitStore);
    }

    @Test
    public void update() {
        logger.debug("Updating pizzaStore (orig) [{}]", pizzaStore);
        String newName = "JUnit-updated-store";
        pizzaStore.setName(newName);
        jdbcTemplate.update(pizzaStore);
        logger.debug("Updated pizzaStore (updated) [{}]", pizzaStore);

        PizzaStore junitStore =
                jdbcTemplate.getPizzaStore(pizzaStore.getStoreId());
        assertNotNull("Unable to find store [" + pizzaStore.getStoreId() + "]", junitStore);
        assertEquals(newName, junitStore.getName());
    }

    @Test
    public void list() {
        List<PizzaStore> pizzaStores = jdbcTemplate.list();

        assertTrue(pizzaStores.size() > 0);
    }

    @Test
    public void delete() {
        PizzaStore deleteStore = (PizzaStore) context.getBean("pizzaStore");
        deleteStore.generateId();
        deleteStore.setName("deleteStore");

        jdbcTemplate.create(deleteStore);
        PizzaStore deleted = jdbcTemplate.getPizzaStore(deleteStore.getStoreId());
        assertNotNull("Unable to find store [" + deleted.getStoreId() + "]", deleted);

        jdbcTemplate.delete(deleteStore);
        deleted = jdbcTemplate.getPizzaStore(deleteStore.getStoreId());
        assertNull("Expecting store [" + deleteStore.getStoreId() + "] to be deleted", deleted);
    }

}
