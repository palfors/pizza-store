package com.alforsconsulting.pizzastore.menu.detail.dao;

import com.alforsconsulting.pizzastore.customer.Customer;
import com.alforsconsulting.pizzastore.menu.detail.MenuItemDetail;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by palfors on 5/13/16.
 */
public interface MenuItemDetailDAO {
    /**
     * This is the method to be used to initialize
     * database resources ie. connection.
     */
    public void setDataSource(DataSource ds);
    /**
     * This is the method to be used to list down
     * a record from the table corresponding
     * to a passed id.
     */
    public MenuItemDetail getMenuItemDetail(long id);
    /**
     * This is the method to be used to list down
     * a record from the table corresponding
     * to a passed id.
     */
    public MenuItemDetail getMenuItemDetail(long menuItemId, String detailType, String name);
    /**
     * This is the method to be used to list down
     * all the records from the table.
     */
    public List<MenuItemDetail> list();
}
