package com.alforsconsulting.pizzastore.order;

/**
 * Created by palfors on 5/12/16.
 */
public class OrderIdGenerator {

    private long orderId = 0;

    private static OrderIdGenerator ourInstance = new OrderIdGenerator();

    public static OrderIdGenerator getInstance() {
        return ourInstance;
    }

    private OrderIdGenerator() {
    }

    private void incrementId() {
        orderId++;
    }

    public synchronized long generateId() {
        incrementId();
        return orderId;
    }
}

