package com.alforsconsulting.pizzastore.customer;

/**
 * Created by palfors on 5/12/16.
 */
public class CustomerIdGenerator {

    private long customerId = 0;

    private static CustomerIdGenerator ourInstance = new CustomerIdGenerator();

    public static CustomerIdGenerator getInstance() {
        return ourInstance;
    }

    private CustomerIdGenerator() {
    }

    private void incrementId() {
        customerId++;
    }

    public synchronized long generateId() {
        incrementId();
        return customerId;
    }
}

