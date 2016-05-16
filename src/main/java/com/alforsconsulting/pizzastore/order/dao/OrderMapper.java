package com.alforsconsulting.pizzastore.order.dao;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.customer.Customer;
import com.alforsconsulting.pizzastore.customer.dao.CustomerJDBCTemplate;
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
        order.setPrice(rs.getDouble("price"));

        // customer
        long customerId = rs.getLong("customerId");
        CustomerJDBCTemplate customerJDBCTemplate =
                (CustomerJDBCTemplate) AppContext.getInstance().getContext(
                    ).getBean("customerJDBCTemplate");
        Customer customer = customerJDBCTemplate.getCustomer(customerId);
        order.setCustomer(customer);

        return order;
    }
}
