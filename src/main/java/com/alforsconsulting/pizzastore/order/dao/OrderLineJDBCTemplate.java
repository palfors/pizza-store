package com.alforsconsulting.pizzastore.order.dao;

import com.alforsconsulting.pizzastore.order.Order;
import com.alforsconsulting.pizzastore.order.OrderLine;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by palfors on 5/13/16.
 */
public class OrderLineJDBCTemplate implements OrderLineDAO {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(OrderLine orderLine) {
        create(orderLine);
    }

    public void create(long orderLineId, long orderId, long menuItemId, int quantity, double price) {
        String SQL = "insert into ORDER_LINE (orderLineId, orderId, menuItemId, quantity, price) values (?, ?, ?, ?, ?)";

        jdbcTemplateObject.update(SQL, orderLineId, orderId, menuItemId, quantity, price);

        StringBuilder builder = new StringBuilder("Created OrderLine: \n");
        builder.append("- orderLineId [").append(orderLineId).append("]\n");
        builder.append("- orderId [").append(orderId).append("]\n");
        builder.append("- menuItemId [").append(menuItemId).append("]\n");
        builder.append("- quantity [").append(quantity).append("]\n");
        builder.append("- price [").append(price).append("]\n");
        System.out.println(builder.toString());
    }

    public OrderLine getOrderLine(long orderLineId) {
        String SQL = "select * from ORDER_LINE where orderLineId = ?";
        OrderLine orderLine = jdbcTemplateObject.queryForObject(SQL,
                new Object[]{orderLineId}, new OrderLineMapper());

        return orderLine;
    }

    public List<OrderLine> list() {
        String SQL = "select * from ORDER_LINE";
        List<OrderLine> orderLines = jdbcTemplateObject.query(SQL,
                new OrderLineMapper());
        return orderLines;
    }

    @Override
    public void delete(OrderLine orderLine) {
        delete(orderLine.getOrderLineId());
    }

    public void delete(long orderLineId) {
        String SQL = "delete from ORDER_LINE where orderLineId = ?";
        jdbcTemplateObject.update(SQL, orderLineId);

        StringBuilder builder = new StringBuilder("Deleted OrderLine [");
        builder.append(orderLineId).append("]\n");
        System.out.println(builder.toString());
    }

    @Override
    public void update(OrderLine orderLine) {
        update(orderLine.getOrderLineId(),
                orderLine.getOrderId(),
                orderLine.getMenuItemId(),
                orderLine.getQuantity(),
                orderLine.getPrice());
    }

    public void update(long orderLineId, long orderId, long menuItemId, int quantity, double price) {
        String SQL = "update ORDER_LINE set orderId = ?, menuItemId = ?, quantity = ?, price = ? where orderLineId = ?";
        jdbcTemplateObject.update(SQL, orderId, menuItemId, quantity, price, orderLineId);

        StringBuilder builder = new StringBuilder("Updated OrderLine [");
        builder.append(orderLineId).append("]\n");
        System.out.println(builder.toString());
    }

}
