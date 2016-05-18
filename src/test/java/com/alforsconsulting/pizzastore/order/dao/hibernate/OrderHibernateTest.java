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
package com.alforsconsulting.pizzastore.order.dao.hibernate;

import com.alforsconsulting.pizzastore.AbstractHibernateTest;
import com.alforsconsulting.pizzastore.PizzaStore;
import com.alforsconsulting.pizzastore.customer.Customer;
import com.alforsconsulting.pizzastore.order.Order;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class OrderHibernateTest extends AbstractHibernateTest {
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
		// create orders...
		Session session = sessionFactory.openSession();
		session.beginTransaction();

        // create store for order
        PizzaStore pizzaStore = (PizzaStore) applicationContext.getBean("pizzaStore");
        pizzaStore.generateId();
        pizzaStore.setName("hibernate-store");
        session.save(pizzaStore);
        logger.debug("Saved pizzaStore [{}]", pizzaStore);

		// create customers for order
        Customer customer = (Customer) applicationContext.getBean("customer");
        customer.setName("hibernate-customer");
        logger.debug("Saved pizzaStore [{}]", customer);
        session.save(customer);

        // create the order
        Order order = (Order) applicationContext.getBean("order");
        order.setStoreId(pizzaStore.getStoreId());
        order.setCustomerId(customer.getCustomerId());
        order.setPrice(23.45);
        session.save(order);
        logger.debug("Saved order [{}]", order);

        // TODO: load the record from the DB

		// list them
        List<Order> orders = (List<Order>) session.createQuery( "from Order" ).list();
        logger.debug("Loading orders");
		for ( Order ord : orders ) {
            logger.debug(ord);
		}

        // delete the test records
        session.beginTransaction();
        session.delete(order);
        session.delete(customer);
        session.delete(pizzaStore);

        session.getTransaction().commit();
        session.close();

        // TODO: verify record no longer exists
	}
}
