package com.alforsconsulting.pizzastore.dao;

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
     * a record in the Order table.
     */
    public void create(long orderId, long storeId, long customerId);
    /**
     * This is the method to be used to list down
     * a record from the Student table corresponding
     * to a passed student id.
     */
    public Order getOrder(long orderId);
    /**
     * This is the method to be used to list down
     * all the records from the Student table.
     */
    public List<Order> listOrders();
    /**
     * This is the method to be used to delete
     * a record from the Student table corresponding
     * to a passed order id.
     */
    public void delete(long orderId);
    /**
     * This is the method to be used to update
     * a record in the Order table.
     */
    public void update(long orderId, long storeId, long customerId);
}
