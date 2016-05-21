package com.alforsconsulting.pizzastore.order.line.detail.dao;

import com.alforsconsulting.pizzastore.menu.detail.MenuItemDetail;
import com.alforsconsulting.pizzastore.order.line.detail.OrderLineDetail;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by palfors on 5/13/16.
 */
public interface OrderLineDetailDAO {
    /**
     * This is the method to be used to initialize
     * database resources ie. connection.
     */
    public void setDataSource(DataSource ds);
    /**
     * This is the method to be used to create
     * a record in the table.
     */
    public void create(OrderLineDetail orderLineDetail);
    /**
     * This is the method to be used to create
     * a record in the table.
     */
    public void create(long orderLineDetailId, long orderLineId, long menuItemDetailId, String placement, double price);
    /**
     * This is the method to be used to list down
     * a record from the table corresponding
     * to a passed id.
     */
    public OrderLineDetail getOrderLineDetail(long orderLineDetailId);
    /**
     * This is the method to be used to list down
     * all the records from the table.
     */
    public List<OrderLineDetail> list();
    /**
     * This is the method to be used to delete
     * a record from the table corresponding
     * to a passed id.
     */
    public void delete(OrderLineDetail orderLineDetail);
    /**
     * This is the method to be used to delete
     * a record from the table corresponding
     * to a passed id.
     */
    public void delete(long id);
    /**
     * This is the method to be used to update
     * a record in the table.
     */
    public void update(OrderLineDetail orderLineDetail);
    /**
     * This is the method to be used to update
     * a record in the table.
     */
    public void update(long orderLineDetailId, long orderLineId, long menuItemDetailId, String placement, double price);
}
