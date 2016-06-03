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
import com.alforsconsulting.pizzastore.StoreUtil;
import org.hibernate.Session;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PizzaStoreHibernateTest extends AbstractHibernateTest {

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

		// create store to add
		PizzaStore pizzaStore = StoreUtil.create("hibernate-store");
        StoreUtil.save(session, pizzaStore);
        assertNotNull(pizzaStore);
        pizzaStore = StoreUtil.getStore(session, pizzaStore.getStoreId());
        assertNotNull(pizzaStore);
        logger.debug("Created pizzaStore [{}]", pizzaStore);

        // update
        pizzaStore.setName("updated-hibernate-store");
        StoreUtil.save(session, pizzaStore);
        pizzaStore = StoreUtil.getStore(session, pizzaStore.getStoreId());
        assertEquals("updated-hibernate-store", pizzaStore.getName());

        // list them
        List<PizzaStore> stores = StoreUtil.getStores(session);
        assertTrue(stores.size() > 0);
        logger.debug("Loaded stores");
        for ( PizzaStore store : stores ) {
            logger.debug(store);
        }

        // delete the test records
        StoreUtil.delete(session, pizzaStore);
        logger.debug("Deleted pizzaStore [{}]", pizzaStore);

        // verify record no longer exists
        pizzaStore = StoreUtil.getStore(session, pizzaStore.getStoreId());
        assertNull(pizzaStore);
        logger.debug("Verified store no longer exists [{}]", pizzaStore);

        session.getTransaction().commit();
        session.close();


	}

    @Test
    public void list() {
        logger.debug("Listing stores");
        List<PizzaStore> stores = StoreUtil.getStores();
        assertTrue(stores.size() > 0);
        logger.debug("Loaded stores");
        for ( PizzaStore store : stores ) {
            logger.debug(store);
        }
    }

    @Test
    public void load() {
        logger.debug("Load() entry");
        // load a record from the DB
        List<PizzaStore> stores = StoreUtil.getStores();
        assertTrue(stores.size() > 0);

        long pizzaStoreId = stores.get(0).getStoreId();
        String storeName = stores.get(0).getName();

        PizzaStore pizzaStore = StoreUtil.getStore(pizzaStoreId);
        assertNotNull(pizzaStore);
        logger.debug("Found pizzaStore by Id [{}]", pizzaStore);

        pizzaStore = StoreUtil.getStore(storeName);
        assertNotNull(pizzaStore);
        logger.debug("Found pizzaStore by name [{}]", pizzaStore);
    }

    @Test
    public void merge() {
        logger.debug("merge a store");

        int num = new Double(Math.random()*1000).intValue();
        PizzaStore store = StoreUtil.create("TEST_STORE_" + num);
        StoreUtil.save(store);
        assertNotNull(store);

        store = StoreUtil.getStore(store.getStoreId());
        assertNotNull(store);

        // merge an updated detached store
        String origName=store.getName();
        PizzaStore newStore = StoreUtil.newPizzaStore();
        newStore.setStoreId(store.getStoreId());
        newStore.setName(origName + "_UPDATED");
        StoreUtil.merge(newStore);

        store = StoreUtil.getStore(store.getStoreId());
        assertNotNull(store);
        assertEquals(store.getName(), origName + "_UPDATED");

        StoreUtil.delete(store);
        store = StoreUtil.getStore(store.getStoreId());
        assertNull(store);
    }

}
