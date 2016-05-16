package com.alforsconsulting.pizzastore.customer;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.customer.dao.CustomerJDBCTemplate;

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
        customerId = getCurrentMaxId();
    }

    private long getCurrentMaxId() {
        CustomerJDBCTemplate jdbcTemplate = (CustomerJDBCTemplate) AppContext.getInstance(
            ).getContext().getBean("customerJDBCTemplate");
        return jdbcTemplate.getMaxId();
    }

    private void incrementId() {
        customerId++;
    }

    public synchronized long generateId() {
        incrementId();
        return customerId;
    }
}

