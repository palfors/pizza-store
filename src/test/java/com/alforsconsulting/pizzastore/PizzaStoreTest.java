package com.alforsconsulting.pizzastore;

import com.alforsconsulting.pizzastore.customer.Customer;
import com.alforsconsulting.pizzastore.customer.CustomerUtil;
import com.alforsconsulting.pizzastore.menu.MenuItemUtil;
import com.alforsconsulting.pizzastore.menu.MenuItem;
import com.alforsconsulting.pizzastore.menu.MenuItemType;
import com.alforsconsulting.pizzastore.menu.detail.MenuItemDetail;
import com.alforsconsulting.pizzastore.menu.detail.MenuItemDetailType;
import com.alforsconsulting.pizzastore.menu.detail.MenuItemDetailUtil;
import com.alforsconsulting.pizzastore.menu.pizza.Pizza;
import com.alforsconsulting.pizzastore.menu.pizza.topping.ToppingPlacement;
import com.alforsconsulting.pizzastore.order.Order;
import com.alforsconsulting.pizzastore.order.line.OrderLine;
import com.alforsconsulting.pizzastore.order.line.detail.OrderLineDetail;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.hibernate.Session;
import org.junit.*;

import java.util.List;

/**
 * Created by palfors on 5/22/16.
 */
public class PizzaStoreTest extends AbstractHibernateTest {
    CacheManager cacheManager;

    @BeforeClass
    public static void prepareClass() throws Exception {
        setUpClass();
    }

    @AfterClass
    public void tearDown() throws Exception {
        tearDownClass();
    }

    private void initializeCache() {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("preConfigured",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
                                ResourcePoolsBuilder.heap(100))
                                .build())
                .build(true);

        Cache<Long, String> preConfigured
                = cacheManager.getCache("preConfigured", Long.class, String.class);

        Cache<Long, String> myCache = cacheManager.createCache("myCache",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
                        ResourcePoolsBuilder.heap(100)).build());

        myCache.put(1L, "da one!");
        String value = myCache.get(1L);

        cacheManager.close();
    }
}
