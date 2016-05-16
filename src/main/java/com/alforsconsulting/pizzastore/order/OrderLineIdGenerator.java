package com.alforsconsulting.pizzastore.order;

/**
 * Created by palfors on 5/12/16.
 */
public class OrderLineIdGenerator {

    private long orderLineId = 0;

    private static OrderLineIdGenerator ourInstance = new OrderLineIdGenerator();

    public static OrderLineIdGenerator getInstance() {
        return ourInstance;
    }

    private OrderLineIdGenerator() {
    }

    private void incrementId() {
        orderLineId++;
    }

    public synchronized long generateId() {
        incrementId();
        return orderLineId;
    }
}

