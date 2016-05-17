package com.alforsconsulting.pizzastore.order.dao;

import com.alforsconsulting.pizzastore.order.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by palfors on 5/13/16.
 */
@Component
public class OrderJDBCTemplate implements OrderDAO {
    private static final Logger logger = LogManager.getLogger();

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public void create(Order order) {
        logger.debug("Creating order [{}]", order);
        create(order.getOrderId(),
                order.getStoreId(),
                order.getCustomer().getCustomerId(),
                order.getPrice());
    }

    public void create(long orderId, long storeId, long customerId, double price) {
        logger.debug("Creating order [{}][{}][{}][{}]", orderId, storeId, customerId, price);
        String SQL = "insert into STORE_ORDER (orderId, storeId, customerId, price) values (?, ?, ?, ?)";

        jdbcTemplateObject.update(SQL, orderId, storeId, customerId, price);
    }

    public Order getOrder(long orderId) {
        logger.debug("Retrieving order [{}]", orderId);
        String SQL = "select * from STORE_ORDER where orderId = ?";
        Order order = null;
        try {
            order = jdbcTemplateObject.queryForObject(SQL,
                new Object[]{orderId}, new OrderMapper());
            logger.debug("Found order [{}]", order);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("Order [{}] does not exist", orderId);
            // allow 0 results and return null
        }

        return order;
    }

    public List<Order> list() {
        logger.debug("Retrieving all orders");
        String SQL = "select * from STORE_ORDER";
        List<Order> orders = jdbcTemplateObject.query(SQL,
                new OrderMapper());

        logger.debug("Found [{}] orders", orders.size());
        return orders;
    }

    public void delete(Order order) {
        logger.debug("Deleting order [{}]", order);
        delete(order.getOrderId());
    }

    public void delete(long orderId) {
        logger.debug("Deleting order [{}]", orderId);
        String SQL = "delete from STORE_ORDER where orderId = ?";
        jdbcTemplateObject.update(SQL, orderId);
    }

    public void update(Order order) {
        logger.debug("Updating order [{}]", order);
        update(order.getOrderId(),
                order.getStoreId(),
                order.getCustomer().getCustomerId(),
                order.getPrice());
    }

    public void update(long orderId, long storeId, long customerId, double price) {
        logger.debug("Updating order [{}][{}][{}][{}]", orderId, storeId, customerId, price);
        String SQL = "update STORE_ORDER set storeId = ?, customerId = ?, price = ? where orderId = ?";

        jdbcTemplateObject.update(SQL, storeId, customerId, price, orderId);
    }

    public long getMaxId() {
        String SQL = "select max(orderId) from STORE_ORDER";
        Long maxId = jdbcTemplateObject.queryForObject(SQL, Long.class);
        if (maxId == null)
            maxId = new Long(0);

        logger.debug("Found max orderId [{}]", maxId);
        return maxId;
    }

}
