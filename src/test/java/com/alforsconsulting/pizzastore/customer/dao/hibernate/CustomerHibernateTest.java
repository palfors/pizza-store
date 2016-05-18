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
package com.alforsconsulting.pizzastore.customer.dao.hibernate;

import com.alforsconsulting.pizzastore.AbstractHibernateTest;
import com.alforsconsulting.pizzastore.customer.Customer;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class CustomerHibernateTest extends AbstractHibernateTest {
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

		// create customers to add
		Customer customer = (Customer) applicationContext.getBean("customer");
		customer.setName("hibernate-customer");
		session.save(customer);
        Customer customer2 = (Customer) applicationContext.getBean("customer");
        customer.setName("hibernate-customer2");
        session.save(customer2);

        logger.debug("Saving customer [{}]", customer);

        // TODO: load the record from the DB

		// list them
        List<Customer> customers = (List<Customer>) session.createQuery( "from Customer" ).list();
        logger.debug("Loading customers");
		for ( Customer cust : customers ) {
            logger.debug(cust.getName());
		}

        // delete the test records
        session.beginTransaction();
        session.delete(customer);
        session.delete(customer2);

        session.getTransaction().commit();
        session.close();

        // TODO: verify record no longer exists
	}
}
