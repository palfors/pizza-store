package com.alforsconsulting.pizzastore.dao;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.PizzaStore;
import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by palfors on 5/15/16.
 */
public class PizzaStoreJDBCTemplateTest {
    private static ApplicationContext context = null;
    private static PizzaStoreJDBCTemplate jdbcTemplate = null;
    private static PizzaStore pizzaStore = null;

    @BeforeClass
    public static void setupClass() {
        context = AppContext.getInstance().getContext();
        jdbcTemplate = (PizzaStoreJDBCTemplate)context.getBean("pizzaStoreJDBCTemplate");

        pizzaStore = (PizzaStore) context.getBean("pizzaStore");
        pizzaStore.setName("JUnit-Store");
    }

    @AfterClass
    public static void teardownClass() {
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
        jdbcTemplate.create(pizzaStore);
        // verify the store exists
        PizzaStore junitStore =
                jdbcTemplate.getPizzaStore(pizzaStore.getStoreId());
        assertNotNull("Unable to find store [JUnit-Store] created in test!", junitStore);
    }

    @Test
    public void update() {
        String newName = "JUnit-updated-store";
        pizzaStore.setName(newName);
        jdbcTemplate.update(pizzaStore);

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
        PizzaStore deleteStore = new PizzaStore();
        deleteStore.setName("deleteStore");

        jdbcTemplate.create(deleteStore);
        PizzaStore deleted = jdbcTemplate.getPizzaStore(deleteStore.getStoreId());
        assertNotNull("Unable to find store [" + deleted.getStoreId() + "]", deleted);

        jdbcTemplate.delete(deleteStore);
        deleted = jdbcTemplate.getPizzaStore(deleteStore.getStoreId());
        assertNull("Expecting store [" + deleteStore.getStoreId() + "] to be deleted", deleted);
    }

}
