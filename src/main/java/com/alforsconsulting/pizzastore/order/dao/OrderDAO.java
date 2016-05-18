package com.alforsconsulting.pizzastore.order.dao;

import com.alforsconsulting.pizzastore.order.Order;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by palfors on 5/13/16.
 */
public interface OrderDAO {
    /**
     * This is the method to be used to initialize
     * database resources ie. connection.
     */
    public void setDataSource(DataSource ds);
    /**
     * This is the method to be used to create
     * a record in the table.
     */
    public void create(Order order);
    /**
     * This is the method to be used to create
     * a record in the table.
     */
    public void create(long orderId, long storeId, String status, long customerId, double price);
    /**
     * This is the method to be used to list down
     * a record from the table corresponding
     * to a passed id.
     */
    public Order getOrder(long orderId);
    /**
     * This is the method to be used to list down
     * all the records from the table.
     */
    public List<Order> list();
    /**
     * This is the method to be used to delete
     * a record from the table corresponding
     * to a passed order id.
     */
    public void delete(Order order);
    /**
     * This is the method to be used to delete
     * a record from the table corresponding
     * to a passed order id.
     */
    public void delete(long orderId);
    /**
     * This is the method to be used to update
     * a record in the table.
     */
    public void update(Order order);
    /**
     * This is the method to be used to update
     * a record in the table.
     */
    public void update(long orderId, long storeId, String status, long customerId, double price);

    public long getMaxId();

}
