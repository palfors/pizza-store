package com.alforsconsulting.pizzastore.order.line.dao;

import com.alforsconsulting.pizzastore.order.line.OrderLine;
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
public class OrderLineJDBCTemplate implements OrderLineDAO {
    private static final Logger logger = LogManager.getLogger();

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(OrderLine orderLine) {
        logger.debug("Creating orderLine [{}]", orderLine);
        create(orderLine.getOrderLineId(),
                orderLine.getOrderId(),
                orderLine.getMenuItemId(),
                orderLine.getQuantity(),
                orderLine.getPrice());
    }

    public void create(long orderLineId, long orderId, long menuItemId, int quantity, double price) {
        logger.debug("Creating orderLine [{}][{}][{}][{}][{}]", orderLineId, orderId, menuItemId, quantity, price);
        String SQL = "insert into ORDER_LINE (orderLineId, orderId, menuItemId, quantity, price) values (?, ?, ?, ?, ?)";

        jdbcTemplateObject.update(SQL, orderLineId, orderId, menuItemId, quantity, price);
    }

    public OrderLine getOrderLine(long orderLineId) {
        logger.debug("Retrieving orderLine [{}]", orderLineId);
        String SQL = "select * from ORDER_LINE where orderLineId = ?";
        OrderLine orderLine = null;
        try {
            orderLine = jdbcTemplateObject.queryForObject(SQL,
                    new Object[]{orderLineId}, new OrderLineMapper());
            logger.debug("Found OrderLine [{}]", orderLine);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("OrderLine [{}] does not exist", orderLineId);
            // allow 0 results and return null
        }

        return orderLine;
    }

    public List<OrderLine> list() {
        logger.debug("Retrieving all OrderLines ");
        String SQL = "select * from ORDER_LINE";
        List<OrderLine> orderLines = jdbcTemplateObject.query(SQL,
                new OrderLineMapper());

        logger.debug("Found [{}] OrderLines", orderLines.size());
        return orderLines;
    }

    @Override
    public void delete(OrderLine orderLine) {
        logger.debug("Deleting OrderLine [{}]", orderLine);
        delete(orderLine.getOrderLineId());
    }

    public void delete(long orderLineId) {
        logger.debug("Deleting OrderLine [{}]", orderLineId);
        String SQL = "delete from ORDER_LINE where orderLineId = ?";
        jdbcTemplateObject.update(SQL, orderLineId);
    }

    @Override
    public void update(OrderLine orderLine) {
        logger.debug("Updating OrderLine [{}]", orderLine);
        update(orderLine.getOrderLineId(),
                orderLine.getOrderId(),
                orderLine.getMenuItemId(),
                orderLine.getQuantity(),
                orderLine.getPrice());
    }

    public void update(long orderLineId, long orderId, long menuItemId, int quantity, double price) {
        logger.debug("Updating OrderLine [{}][{}][{}][{}][{}]", orderLineId, orderId, menuItemId, quantity, price);
        String SQL = "update ORDER_LINE set orderId = ?, menuItemId = ?, quantity = ?, price = ? where orderLineId = ?";
        jdbcTemplateObject.update(SQL, orderId, menuItemId, quantity, price, orderLineId);
    }

    public long getMaxId() {
        String SQL = "select max(orderLineId) from ORDER_LINE";
        Long maxId = jdbcTemplateObject.queryForObject(SQL, Long.class);
        if (maxId == null)
            maxId = new Long(0);

        logger.debug("Found max orderLineId [{}]", maxId);
        return maxId;
    }

}
