package com.alforsconsulting.pizzastore.customer.dao;

import com.alforsconsulting.pizzastore.customer.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by palfors on 5/13/16.
 */
public class CustomerMapper implements RowMapper<Customer> {
    private static final Logger logger = LogManager.getLogger();

    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer(rs.getLong("customerId"));
        customer.setName(rs.getString("name"));

        logger.debug("Mapped customer [{}]", customer);
        return customer;
    }
}
