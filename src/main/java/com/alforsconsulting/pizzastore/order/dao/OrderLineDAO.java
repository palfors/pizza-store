package com.alforsconsulting.pizzastore.order.dao;

import com.alforsconsulting.pizzastore.order.Order;
import com.alforsconsulting.pizzastore.order.OrderLine;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by palfors on 5/13/16.
 */
public interface OrderLineDAO {
    /**
     * This is the method to be used to initialize
     * database resources ie. connection.
     */
    public void setDataSource(DataSource ds);
    /**
     * This is the method to be used to create
     * a record in the table.
     */
    public void create(OrderLine orderLine);
    /**
     * This is the method to be used to create
     * a record in the table.
     */
    public void create(long orderLineId, long orderId, long menuItemId, int quantity, double price);
    /**
     * This is the method to be used to list down
     * a record from the table corresponding
     * to a passed id.
     */
    public OrderLine getOrderLine(long orderLineId);
    /**
     * This is the method to be used to list down
     * all the records from the table.
     */
    public List<OrderLine> list();
    /**
     * This is the method to be used to delete
     * a record from the table corresponding
     * to a passed order id.
     */
    public void delete(OrderLine orderLine);
    /**
     * This is the method to be used to delete
     * a record from the table corresponding
     * to a passed order id.
     */
    public void delete(long orderLineId);
    /**
     * This is the method to be used to update
     * a record in the table.
     */
    public void update(OrderLine orderLine);
    /**
     * This is the method to be used to update
     * a record in the table.
     */
    public void update(long orderLineId, long orderId, long menuItemId, int quantity, double price);

    public long getMaxId();

}
