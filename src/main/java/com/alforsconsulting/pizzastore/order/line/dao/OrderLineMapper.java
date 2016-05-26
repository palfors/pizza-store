package com.alforsconsulting.pizzastore.order.line.dao;

import com.alforsconsulting.pizzastore.order.line.OrderLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by palfors on 5/13/16.
 */
public class OrderLineMapper implements RowMapper<OrderLine> {
    private static final Logger logger = LogManager.getLogger();

    public OrderLine mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderLine orderLine = new OrderLine(rs.getLong("orderLineId"));
        orderLine.setOrderId(rs.getLong("orderId"));
        orderLine.setMenuItemId(rs.getLong("menuItemId"));
        orderLine.setQuantity(rs.getInt("quantity"));
        orderLine.setPrice(rs.getDouble("price"));
        orderLine.setCreateDate(rs.getTimestamp("createDate"));
        orderLine.setLastModifiedDate(rs.getTimestamp("lastModifiedDate"));

        logger.debug("Mapped orderLine [{}]", orderLine);
        return orderLine;
    }
}
