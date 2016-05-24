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
import org.junit.*;

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
		// create a couple of stores...

		// create store to add
		PizzaStore pizzaStore = StoreUtil.create("hibernate-store");
        StoreUtil.save(pizzaStore);
		logger.debug("Created pizzaStore [{}]", pizzaStore);

        pizzaStore = StoreUtil.getStore(pizzaStore.getStoreId());
        assertNotNull(pizzaStore);
        logger.debug("Found pizzaStore by Id [{}]", pizzaStore);

        pizzaStore = StoreUtil.getStore(pizzaStore.getName());
        assertNotNull(pizzaStore);
        logger.debug("Found pizzaStore by name [{}]", pizzaStore);

		// list them
        List<PizzaStore> stores = StoreUtil.getStores();
        assertTrue(stores.size() > 0);
        logger.debug("Loaded stores");
		for ( PizzaStore store : stores ) {
            logger.debug(store);
		}

        // delete the test records
        StoreUtil.delete(pizzaStore);
        logger.debug("Deleted pizzaStore [{}]", pizzaStore);

        // verify record no longer exists
        pizzaStore = StoreUtil.getStore(pizzaStore.getStoreId());
        assertNull(pizzaStore);
        logger.debug("Verified store no longer exists [{}]", pizzaStore);
	}


}
