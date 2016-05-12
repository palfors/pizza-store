package com.alforsconsulting.pizzastore;

/**
 * Created by palfors on 5/12/16.
 */
public class StoreIdGenerator {
    private long storeId = 0;

    private static StoreIdGenerator ourInstance = new StoreIdGenerator();

    public static StoreIdGenerator getInstance() {
        return ourInstance;
    }

    private StoreIdGenerator() {
    }

    private void incrementId() {
        storeId++;
    }

    public synchronized long generateId() {
        incrementId();
        return storeId;
    }

}
