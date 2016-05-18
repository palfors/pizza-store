package com.alforsconsulting.pizzastore.menu.dao;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.menu.MenuItem;
import com.alforsconsulting.pizzastore.menu.pizza.Pizza;
import com.alforsconsulting.pizzastore.menu.sides.Breadsticks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by palfors on 5/15/16.
 */
public class MenuJDBCTemplateTest {
    private static final Logger logger = LogManager.getLogger();

    private static ApplicationContext context = null;
    private static MenuItemJDBCTemplate jdbcTemplate = null;
    private static MenuItem pizza = null;
    private static MenuItem breadsticks = null;

    @BeforeClass
    public static void setupClass() {
        logger.debug("setupClass entry");
        context = AppContext.getInstance().getContext();
        jdbcTemplate = (MenuItemJDBCTemplate)context.getBean("menuItemJDBCTemplate");

        pizza = (Pizza) context.getBean("pizza");
        pizza.generateId();
        pizza.setName("JUnit-Pizza");
        pizza.setPrice(7.25);

        breadsticks = (Breadsticks) context.getBean("breadsticks");
        breadsticks.generateId();
        breadsticks.setName("JUnit-Breadsticks");
        breadsticks.setPrice(2.75);
    }

    @AfterClass
    public static void teardownClass() {
        logger.debug("teardownClass entry");
        jdbcTemplate.delete(pizza.getMenuItemId());
        jdbcTemplate.delete(breadsticks.getMenuItemId());
    }

    @Before
    public void setupTest() {
    }

    @After
    public void teardownTest() {
    }

    @Test
    public void create() {
        jdbcTemplate.create(pizza);
        // verify the item exists
        MenuItem junitItem = jdbcTemplate.getMenuItem(pizza.getMenuItemId());
        assertNotNull("Unable to find store [JUnit-Pizza] created in test!", junitItem);

        jdbcTemplate.create(breadsticks);
        // verify the item exists
        junitItem = jdbcTemplate.getMenuItem(breadsticks.getMenuItemId());
        assertNotNull("Unable to find store [JUnit-Breadsticks] created in test!", junitItem);
    }

    @Test
    public void update() {
        String newName = "JUnit-updated-pizza";
        pizza.setName(newName);
        jdbcTemplate.update(pizza);

        MenuItem junitItem = jdbcTemplate.getMenuItem(pizza.getMenuItemId());
        assertNotNull("Unable to find store [" + pizza.getMenuItemId() + "]", junitItem);
        assertEquals(newName, junitItem.getName());
    }

    @Test
    public void list() {
        List<MenuItem> menuItems = jdbcTemplate.list();

        assertTrue(menuItems.size() > 0);
    }

    @Test
    public void delete() {
        MenuItem deleteItem = (Pizza) context.getBean("pizza");
        deleteItem.generateId();
        deleteItem.setName("deleteItem");

        jdbcTemplate.create(deleteItem);
        MenuItem deleted = jdbcTemplate.getMenuItem(deleteItem.getMenuItemId());
        assertNotNull("Unable to find menuItem [" + deleted.getMenuItemId() + "]", deleted);

        jdbcTemplate.delete(deleteItem.getMenuItemId());
        deleted = jdbcTemplate.getMenuItem(deleteItem.getMenuItemId());
        assertNull("Expecting menuItem [" + deleteItem.getMenuItemId() + "] to be deleted", deleted);
    }

}
