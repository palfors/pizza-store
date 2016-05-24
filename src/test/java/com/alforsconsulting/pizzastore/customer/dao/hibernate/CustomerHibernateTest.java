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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

		// create customer to add
        Customer customer = CustomerUtil.create("hibernate-customer");
        assertNotNull(customer);

        // load the record from the DB
        customer = CustomerUtil.getCustomer(customer.getCustomerId());
        assertNotNull(customer);

		// list them
        List<Customer> customers = CustomerUtil.getCustomers();
        assertTrue(customers.size() > 0);
        logger.debug("Loaded customers");
		for ( Customer cust : customers ) {
            logger.debug(cust.getName());
		}

        // delete the test records
        boolean result = CustomerUtil.delete(customer);
        assertTrue(result);
        logger.debug("Deleted customer [{}]", customer);

        // verify record no longer exists
        customer = CustomerUtil.getCustomer(customer.getCustomerId());
        assertNull(customer);
        logger.debug("Verified customer no longer exists [{}]", customer);

    }
}
