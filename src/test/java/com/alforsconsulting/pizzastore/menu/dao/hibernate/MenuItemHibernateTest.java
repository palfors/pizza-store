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
import org.junit.*;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

		// create menuItem
		MenuItem menuItem = (MenuItem) MenuItemUtil.create(MenuItemType.PIZZA, "hibernate-pizza", 7.55);
        MenuItemUtil.save(menuItem);
		logger.debug("Created menuItem [{}]", menuItem);

        menuItem = MenuItemUtil.getMenuItem(menuItem.getMenuItemId());
        assertNotNull(menuItem);

        // we just added a second PIZZA type, so look for breadsticks instead
        MenuItem breadsticks = MenuItemUtil.getMenuItem(MenuItemType.BREADSTICKS);
        assertNotNull(breadsticks);

        // list them
        List<MenuItem> menuItems = MenuItemUtil.getMenuItems();
        assertTrue(menuItems.size() > 0);
        logger.debug("Loaded menuItems");
        for ( MenuItem item : menuItems ) {
            logger.debug(item);
        }

        // delete the test records
        MenuItemUtil.delete(menuItem);
        logger.debug("Deleted menuItem [{}]", menuItem);

        // verify record no longer exists
        menuItem = MenuItemUtil.getMenuItem(menuItem.getMenuItemId());
        assertNull(menuItem);
        logger.debug("Verified menuItem no longer exists [{}]", menuItem);
    }
}
