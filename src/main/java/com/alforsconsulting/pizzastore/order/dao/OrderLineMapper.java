package com.alforsconsulting.pizzastore.order.dao;

import com.alforsconsulting.pizzastore.order.Order;
import com.alforsconsulting.pizzastore.order.OrderLine;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by palfors on 5/13/16.
 */
public class OrderLineMapper implements RowMapper<OrderLine> {
    public OrderLine mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderLine orderLine = new OrderLine(rs.getLong("orderLineId"));
        orderLine.setOrderId(rs.getLong("orderId"));
        orderLine.setMenuItemId(rs.getLong("menuItemId"));
        orderLine.setQuantity(rs.getInt("quantity"));
        orderLine.setPrice(rs.getDouble("price"));

        return orderLine;
    }
}
