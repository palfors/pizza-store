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
package com.alforsconsulting.pizzastore.dao.hibernate;

import com.alforsconsulting.pizzastore.AbstractHibernateTest;
import com.alforsconsulting.pizzastore.PizzaStore;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class PizzaStoreHibernateTest extends AbstractHibernateTest {

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
		// create a couple of stores...
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		// create store to add
		PizzaStore pizzaStore = (PizzaStore) applicationContext.getBean("pizzaStore");
        pizzaStore.generateId();
		pizzaStore.setName("hibernate-store");
		session.save(pizzaStore);
		logger.debug("Saving pizzaStore [{}]", pizzaStore);

		PizzaStore pizzaStore2 = (PizzaStore) applicationContext.getBean("pizzaStore");
        pizzaStore2.generateId();
		pizzaStore2.setName("hibernate-store2");
        session.save(pizzaStore2);

        logger.debug("Saving pizzaStore2 [{}]", pizzaStore2);

        // TODO: load the record from the DB

		// list them
        List<PizzaStore> stores = (List<PizzaStore>) session.createQuery( "from PizzaStore" ).list();
        logger.debug("Loading stores");
		for ( PizzaStore store : stores ) {
            logger.debug(store.getName());
		}

        // delete the test records
        session.delete(pizzaStore);
		session.delete(pizzaStore2);

        session.getTransaction().commit();
        session.close();

        // TODO: verify record no longer exists
	}
}
