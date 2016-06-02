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
import com.alforsconsulting.pizzastore.customer.CustomerUtil;
import org.hibernate.Session;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

public class CustomerHibernateTest extends AbstractHibernateTest {

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

		// create customer to add
        Customer customer = CustomerUtil.create("hibernate-customer");
        CustomerUtil.save(session, customer);
        assertNotNull(customer);
        customer = CustomerUtil.getCustomer(session, customer.getCustomerId());
        assertNotNull(customer);
        logger.debug("added customer [{}]", customer);

        // update customer
        customer.setName("updated-hibernate-customer");
        CustomerUtil.save(session, customer);
        customer = CustomerUtil.getCustomer(session, customer.getCustomerId());
        assertEquals("updated-hibernate-customer", customer.getName());
        logger.debug("updated customer [{}]", customer);

        // list all customers
        List<Customer> customers = CustomerUtil.getCustomers(session);
        assertTrue(customers.size() > 0);
        logger.debug("Loaded customers within transaction");
        for ( Customer cust : customers ) {
            logger.debug(cust.getName());
        }

        // delete the test records
        CustomerUtil.delete(session, customer);
        logger.debug("Deleted customer [{}]", customer);

        // verify record no longer exists
        customer = CustomerUtil.getCustomer(session, customer.getCustomerId());
        assertNull(customer);
        logger.debug("Verified customer no longer exists [{}]", customer);

        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void list() {
        logger.debug("listing customers");
        List<Customer> customers = CustomerUtil.getCustomers();
        assertTrue(customers.size() > 0);
        logger.debug("Loaded customers");
        for ( Customer customer : customers ) {
            logger.debug(customer);
        }
    }

    @Test
    public void load() {
        logger.debug("loading a customer");
        // load a record from the DB
        List<Customer> customers = CustomerUtil.getCustomers();
        assertTrue(customers.size() > 0);

        Customer customer = customers.get(0);
        customer = CustomerUtil.getCustomer(customer.getCustomerId());
        assertNotNull(customer);
    }

    @Test
    public void merge() {
        logger.debug("merge a customer");

        int num = new Double(Math.random()*1000).intValue();
        Customer customer = CustomerUtil.create("TEST_CUSTOMER_" + num);
        CustomerUtil.save(customer);
        assertNotNull(customer);

        customer = CustomerUtil.getCustomer(customer.getCustomerId());
        assertNotNull(customer);

        String origName=customer.getName();
        customer.setName(origName + "_UPDATED");
        CustomerUtil.merge(customer);

        customer = CustomerUtil.getCustomer(customer.getCustomerId());
        assertNotNull(customer);
        assertEquals(customer.getName(), origName + "_UPDATED");

        CustomerUtil.delete(customer);
        customer = CustomerUtil.getCustomer(customer.getCustomerId());
        assertNull(customer);
    }

}
