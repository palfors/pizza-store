package com.alforsconsulting.pizzastore.dao;

import com.alforsconsulting.pizzastore.order.Order;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by palfors on 5/13/16.
 */
public class OrderJDBCTemplate implements OrderDAO {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public void create(long orderId, long storeId, long customerId) {
        String SQL = "insert into STORE_ORDER (orderId, storeId, customerId) values (?, ?, ?)";

        jdbcTemplateObject.update(SQL, orderId, storeId, customerId);
        System.out.println("Created Record orderId = " + orderId + " storeId = " +
                storeId + " customerId = " + customerId);
    }

    public Order getOrder(long orderId) {
        String SQL = "select * from STORE_ORDER where orderId = ?";
        Order order = jdbcTemplateObject.queryForObject(SQL,
                new Object[]{orderId}, new OrderMapper());
        return order;
    }

    public List<Order> listOrders() {
        String SQL = "select * from STORE_ORDER";
        List<Order> orders = jdbcTemplateObject.query(SQL,
                new OrderMapper());
        return orders;
    }

    public void delete(long orderId) {
        String SQL = "delete from STORE_ORDER where orderId = ?";
        jdbcTemplateObject.update(SQL, orderId);
        System.out.println("Deleted Record with ID = " + orderId );
    }

    public void update(long orderId, long storeId, long customerId) {
        String SQL = "update STORE_ORDER set storeId = ?, customerId = ? where orderId = ?";
        jdbcTemplateObject.update(SQL, storeId, customerId, orderId);
        System.out.println("Updated Record with ID = " + orderId );
    }

}
