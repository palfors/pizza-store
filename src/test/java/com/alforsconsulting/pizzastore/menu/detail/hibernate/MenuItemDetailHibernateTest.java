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
import com.alforsconsulting.pizzastore.menu.detail.MenuItemDetail;
import com.alforsconsulting.pizzastore.menu.detail.MenuItemDetailType;
import com.alforsconsulting.pizzastore.menu.pizza.Pizza;
import com.alforsconsulting.pizzastore.menu.sides.Breadsticks;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class MenuItemDetailHibernateTest extends AbstractHibernateTest {

    @BeforeClass
    public static void prepareClass() {
        AbstractHibernateTest.prepareClass();
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

	@Test
	public void testBasicUsage() {
        logger.debug("testBasicUsage entry");
		// create a couple of events...
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		// add a menu item
        Pizza pizza = (Pizza) applicationContext.getBean("pizza");
        pizza.setName("hibernate-pizza");
        pizza.setPrice(7.55);
        session.save(pizza);
        logger.debug("Saving pizza [{}]", pizza);

        // add a detail to the menu item
        MenuItemDetail menuItemDetail =
                (MenuItemDetail) applicationContext.getBean("menuItemDetail");
        menuItemDetail.setMenuItemId(pizza.getMenuItemId());
        menuItemDetail.setDetailType(MenuItemDetailType.TOPPING.getBeanName());
        menuItemDetail.setName("hibernate-menuitemdetail");
        menuItemDetail.setPrice(3.25);

        // TODO: load the record from the DB

		// list them
        List<MenuItemDetail> details = (List<MenuItemDetail>) session.createQuery( "from MenuItemDetail" ).list();
        logger.debug("Loading menuItemDetails");
		for ( MenuItemDetail detail : details ) {
            logger.debug(detail);
		}

        // delete the test records
        session.delete(menuItemDetail);
        session.delete(pizza);

        session.getTransaction().commit();
        session.close();

        // TODO: verify record no longer exists
	}
}
