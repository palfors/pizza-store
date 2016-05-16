package com.alforsconsulting.pizzastore;

import com.alforsconsulting.pizzastore.dao.PizzaStoreJDBCTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by palfors on 5/12/16.
 */
public class StoreIdGenerator {
    private long storeId;
    private PizzaStoreJDBCTemplate jdbcTemplate = null;

    private static StoreIdGenerator ourInstance = new StoreIdGenerator();

    public static StoreIdGenerator getInstance() {
        return ourInstance;
    }

    private StoreIdGenerator() {
        storeId = getCurrentMaxId();
    }

    private long getCurrentMaxId() {
        jdbcTemplate = (PizzaStoreJDBCTemplate) AppContext.getInstance(
            ).getContext().getBean("pizzaStoreJDBCTemplate");
        return jdbcTemplate.getMaxId();
    }

    private void incrementId() {
        storeId++;
    }

    public synchronized long generateId() {
        incrementId();
        return storeId;
    }

}
