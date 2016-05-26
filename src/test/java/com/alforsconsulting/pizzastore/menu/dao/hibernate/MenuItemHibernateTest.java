/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2010, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package com.alforsconsulting.pizzastore.menu.dao.hibernate;

import com.alforsconsulting.pizzastore.AbstractHibernateTest;
import com.alforsconsulting.pizzastore.menu.MenuItem;
import com.alforsconsulting.pizzastore.menu.MenuItemType;
import com.alforsconsulting.pizzastore.menu.MenuItemUtil;
import com.alforsconsulting.pizzastore.menu.pizza.Pizza;
import com.alforsconsulting.pizzastore.menu.sides.Breadsticks;
import org.hibernate.Session;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

public class MenuItemHibernateTest extends AbstractHibernateTest {

    @BeforeClass
    public static void prepareClass() throws Exception {
        setUpClass();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        tearDownClass();
    }

	@Test
	public void testCRUD() {
        logger.debug("testCRUD entry");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

		// create menuItem
		MenuItem menuItem = (MenuItem) MenuItemUtil.create(MenuItemType.PIZZA, "hibernate-pizza", 7.55);
        MenuItemUtil.save(session, menuItem);
        assertNotNull(menuItem);
        menuItem = MenuItemUtil.getMenuItem(session, menuItem.getMenuItemId());
        assertNotNull(menuItem);
		logger.debug("Created menuItem [{}]", menuItem);

        // update
        menuItem.setName("updated-hibernate-pizza");
        MenuItemUtil.save(session, menuItem);
        menuItem = MenuItemUtil.getMenuItem(session, menuItem.getMenuItemId());
        assertEquals("updated-hibernate-pizza", menuItem.getName());

        // list them
        List<MenuItem> items = MenuItemUtil.getMenuItems(session);
        assertTrue(items.size() > 0);
        logger.debug("Loaded menuItems");
        for ( MenuItem item : items ) {
            logger.debug(item);
        }

        // delete the test records
        MenuItemUtil.delete(session, menuItem);
        logger.debug("Deleted menuItem [{}]", menuItem);

        // verify record no longer exists
        menuItem = MenuItemUtil.getMenuItem(session, menuItem.getMenuItemId());
        assertNull(menuItem);
        logger.debug("Verified menuItem no longer exists [{}]", menuItem);

        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void list() {
        logger.debug("Listing menuItems");
        List<MenuItem> menuItems = MenuItemUtil.getMenuItems();
        assertTrue(menuItems.size() > 0);
        logger.debug("Loaded menuItems");
        for ( MenuItem menuItem : menuItems ) {
            logger.debug(menuItem);
        }
    }

    @Test
    public void load() {
        logger.debug("Load() entry");
        // load a record from the DB
        List<MenuItem> menuItems = MenuItemUtil.getMenuItems();
        if (menuItems != null && menuItems.size() > 0) {
            long menuItemId = menuItems.get(0).getMenuItemId();
            String menuItemType = menuItems.get(0).getMenuItemType();

            MenuItem menuItem = MenuItemUtil.getMenuItem(menuItemId);
            assertNotNull(menuItem);
            logger.debug("Found menuItem by Id [{}]", menuItem);

            menuItem = MenuItemUtil.getMenuItem(MenuItemType.valueOf(menuItemType.toUpperCase()));
            assertNotNull(menuItem);
            logger.debug("Found menuItem by menuItemType [{}]", menuItemType);

        } else {
            logger.warn("Load() no menuItems to load!");
        }
    }

}
