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
package com.alforsconsulting.pizzastore.menu.detail.hibernate;

import com.alforsconsulting.pizzastore.AbstractHibernateTest;
import com.alforsconsulting.pizzastore.menu.MenuItem;
import com.alforsconsulting.pizzastore.menu.MenuItemType;
import com.alforsconsulting.pizzastore.menu.MenuItemUtil;
import com.alforsconsulting.pizzastore.menu.detail.MenuItemDetail;
import com.alforsconsulting.pizzastore.menu.detail.MenuItemDetailType;
import com.alforsconsulting.pizzastore.menu.detail.MenuItemDetailUtil;
import com.alforsconsulting.pizzastore.menu.pizza.Pizza;
import com.alforsconsulting.pizzastore.menu.sides.Breadsticks;
import org.hibernate.Session;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

public class MenuItemDetailHibernateTest extends AbstractHibernateTest {

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

        // create a menuItem to reference
        MenuItem menuItem = (MenuItem) MenuItemUtil.create(MenuItemType.PIZZA, "menuItemDetailTest-pizza", 7.55);
        MenuItemUtil.save(session, menuItem);
        assertNotNull(menuItem);
        menuItem = MenuItemUtil.getMenuItem(session, menuItem.getMenuItemId());
        assertNotNull(menuItem);
        logger.debug("Created menuItem [{}]", menuItem);

        // create menuItemDetail
        MenuItemDetail menuItemDetail =
                MenuItemDetailUtil.create(menuItem.getMenuItemId(),
                                            MenuItemDetailType.TOPPING,
                                            "menuItemDetailTest-topping",
                                            1.75);
        MenuItemDetailUtil.save(session, menuItemDetail);
        assertNotNull(menuItemDetail);
        menuItemDetail = MenuItemDetailUtil.getMenuItemDetail(session, menuItemDetail.getMenuItemDetailId());
        logger.debug("Created menuItemDetail [{}]", menuItemDetail);

        // update
        menuItemDetail.setName("updated-menuItemDetailTest-topping");
        MenuItemDetailUtil.save(session, menuItemDetail);
        menuItemDetail = MenuItemDetailUtil.getMenuItemDetail(session, menuItemDetail.getMenuItemDetailId());
        assertEquals("updated-menuItemDetailTest-topping", menuItemDetail.getName());

        // list them
        List<MenuItemDetail> menuItemDetails = MenuItemDetailUtil.getMenuItemDetails(session);
        assertTrue(menuItemDetails.size() > 0);
        logger.debug("Loaded menuItemDetails");
        for ( MenuItemDetail details : menuItemDetails ) {
            logger.debug(details);
        }

        // delete the test records
        MenuItemDetailUtil.delete(session, menuItemDetail);
        logger.debug("Deleted menuItemDetail [{}]", menuItemDetail);
        assertNull(MenuItemDetailUtil.getMenuItemDetail(menuItemDetail.getMenuItemDetailId()));
        logger.debug("Verified menuItemDetail no longer exists [{}]", menuItemDetail);

        MenuItemUtil.delete(session, menuItem);
        logger.debug("Deleted menuItem [{}]", menuItem);
        assertNull(MenuItemUtil.getMenuItem(menuItem.getMenuItemId()));
        logger.debug("Verified menuItem no longer exists [{}]", menuItem);

        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void list() {
        logger.debug("Listing menuItemDetails");
        List<MenuItemDetail> menuItemDetails = MenuItemDetailUtil.getMenuItemDetails();
        assertTrue(menuItemDetails.size() > 0);
        logger.debug("Loaded menuItemDetails");
        for ( MenuItemDetail menuItemDetail : menuItemDetails ) {
            logger.debug(menuItemDetail);
        }
    }

    @Test
    public void load() {
        logger.debug("Load() entry");
        // load a record from the DB
        List<MenuItemDetail> menuItemDetails = MenuItemDetailUtil.getMenuItemDetails();
        assertTrue(menuItemDetails.size() > 0);

        MenuItemDetail menuItemDetail = menuItemDetails.get(0);
        long menuItemDetailId = menuItemDetail.getMenuItemDetailId();
        long menuItemId = menuItemDetail.getMenuItemId();
        String detailType = menuItemDetail.getDetailType();
        String name = menuItemDetail.getName();

        menuItemDetail = MenuItemDetailUtil.getMenuItemDetail(menuItemDetailId);
        assertNotNull(menuItemDetail);
        logger.debug("Found menuItemDetail by Id [{}]", menuItemDetail);

        menuItemDetail = MenuItemDetailUtil.getMenuItemDetail(menuItemId, MenuItemDetailType.valueOf(detailType.toUpperCase()), name);
        assertNotNull(menuItemDetail);
        logger.debug("Found menuItemDetail by menuItem,detailType,name [{}]", menuItemDetail);
    }

}
