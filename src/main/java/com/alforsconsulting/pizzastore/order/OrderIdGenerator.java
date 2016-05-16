package com.alforsconsulting.pizzastore.order;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.order.dao.OrderJDBCTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by palfors on 5/12/16.
 */
public class OrderIdGenerator {
    private static final Logger logger = LogManager.getLogger();

    private long orderId = 0;

    private static OrderIdGenerator ourInstance = new OrderIdGenerator();

    public static OrderIdGenerator getInstance() {
        return ourInstance;
    }

    private OrderIdGenerator() {
        orderId = getCurrentMaxId();
    }

    private long getCurrentMaxId() {
        OrderJDBCTemplate jdbcTemplate = (OrderJDBCTemplate) AppContext.getInstance(
            ).getContext().getBean("orderJDBCTemplate");
        return jdbcTemplate.getMaxId();
    }

    private void incrementId() {
        orderId++;
    }

    public synchronized long generateId() {
        incrementId();
        logger.debug("Generated next orderId [{}]", orderId);
        return orderId;
    }
}

