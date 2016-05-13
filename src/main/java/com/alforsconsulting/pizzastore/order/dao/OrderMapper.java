package com.alforsconsulting.pizzastore.order.dao;

import com.alforsconsulting.pizzastore.order.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by palfors on 5/13/16.
 */
public class OrderMapper implements RowMapper<Order> {
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order(rs.getLong("orderId"));
        order.setStoreId(rs.getLong("storeId"));

        return order;
    }
}
