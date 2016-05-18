package com.alforsconsulting.pizzastore.order.dao;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.customer.Customer;
import com.alforsconsulting.pizzastore.customer.dao.CustomerJDBCTemplate;
import com.alforsconsulting.pizzastore.order.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by palfors on 5/13/16.
 */
public class OrderMapper implements RowMapper<Order> {
    private static final Logger logger = LogManager.getLogger();

    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order(rs.getLong("orderId"));
        order.setStoreId(rs.getLong("storeId"));
        order.setCustomerId(rs.getLong("customerId"));
        order.setStatus(rs.getString("status"));
        order.setPrice(rs.getDouble("price"));

        logger.debug("Mapped orderLine [{}]", order);
        return order;
    }
}
