package com.alforsconsulting.pizzastore.order.line;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.order.line.dao.OrderLineJDBCTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by palfors on 5/12/16.
 */
public class OrderLineIdGenerator {
    private static final Logger logger = LogManager.getLogger();
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
        logger.debug("Generated next orderLineId [{}]", orderLineId);
        return orderLineId;
    }
}

