package com.alforsconsulting.pizzastore.order;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.order.dao.OrderLineJDBCTemplate;

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
        orderLineId = getCurrentMaxId();
    }

    private long getCurrentMaxId() {
        OrderLineJDBCTemplate jdbcTemplate = (OrderLineJDBCTemplate) AppContext.getInstance(
            ).getContext().getBean("orderLineJDBCTemplate");
        return jdbcTemplate.getMaxId();
    }

    private void incrementId() {
        orderLineId++;
    }

    public synchronized long generateId() {
        incrementId();
        return orderLineId;
    }
}

