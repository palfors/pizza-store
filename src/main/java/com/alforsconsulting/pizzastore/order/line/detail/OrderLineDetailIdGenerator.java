package com.alforsconsulting.pizzastore.order.line.detail;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.order.line.detail.dao.OrderLineDetailJDBCTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by palfors on 5/12/16.
 */
public class OrderLineDetailIdGenerator {
    private static final Logger logger = LogManager.getLogger();
    private long orderLineDetailId = 0;

    private static OrderLineDetailIdGenerator ourInstance = new OrderLineDetailIdGenerator();

    public static OrderLineDetailIdGenerator getInstance() {
        return ourInstance;
    }

    private OrderLineDetailIdGenerator() {
        orderLineDetailId = getCurrentMaxId();
    }

    private long getCurrentMaxId() {
        logger.debug("OrderLineDetailIdGenerator.getCurrentMaxId() entry");
        OrderLineDetailJDBCTemplate jdbcTemplate = (OrderLineDetailJDBCTemplate) AppContext.getInstance(
            ).getContext().getBean("orderLineDetailJDBCTemplate");
        return jdbcTemplate.getMaxId();
    }

    private void incrementId() {
        orderLineDetailId++;
    }

    public synchronized long generateId() {
        incrementId();
        logger.debug("Generated next orderLineDetailId [{}]", orderLineDetailId);
        return orderLineDetailId;
    }
}

