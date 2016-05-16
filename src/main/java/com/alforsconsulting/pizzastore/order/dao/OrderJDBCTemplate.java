package com.alforsconsulting.pizzastore.order.dao;

import com.alforsconsulting.pizzastore.order.Order;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public void create(Order order) {
        create(order.getOrderId(),
                order.getStoreId(),
                order.getCustomer().getCustomerId(),
                order.getPrice());
    }

    public void create(long orderId, long storeId, long customerId, double price) {
        String SQL = "insert into STORE_ORDER (orderId, storeId, customerId, price) values (?, ?, ?, ?)";

        jdbcTemplateObject.update(SQL, orderId, storeId, customerId, price);
        System.out.println("Created Record orderId = " + orderId + " storeId = " +
                storeId + " customerId = " + customerId);
    }

    public Order getOrder(long orderId) {
        String SQL = "select * from STORE_ORDER where orderId = ?";
        Order order = null;
        try {
            order = jdbcTemplateObject.queryForObject(SQL,
                new Object[]{orderId}, new OrderMapper());
        } catch (EmptyResultDataAccessException e) {
            // allow 0 results and return null
        }

        return order;
    }

    public List<Order> list() {
        String SQL = "select * from STORE_ORDER";
        List<Order> orders = jdbcTemplateObject.query(SQL,
                new OrderMapper());
        return orders;
    }

    public void delete(Order order) {
        delete(order.getOrderId());
    }

    public void delete(long orderId) {
        String SQL = "delete from STORE_ORDER where orderId = ?";
        jdbcTemplateObject.update(SQL, orderId);
        System.out.println("Deleted Record with ID = " + orderId );
    }

    public void update(Order order) {
        update(order.getOrderId(),
                order.getStoreId(),
                order.getCustomer().getCustomerId(),
                order.getPrice());
    }

    public void update(long orderId, long storeId, long customerId, double price) {
        String SQL = "update STORE_ORDER set storeId = ?, customerId = ?, price = ? where orderId = ?";

        jdbcTemplateObject.update(SQL, storeId, customerId, price, orderId);
        System.out.println("Updated Record with ID = " + orderId );
    }

    public long getMaxId() {
        String SQL = "select max(orderId) from STORE_ORDER";
        Long maxId = jdbcTemplateObject.queryForObject(SQL, Long.class);
        if (maxId == null)
            maxId = new Long(0);

        return maxId;
    }

}
