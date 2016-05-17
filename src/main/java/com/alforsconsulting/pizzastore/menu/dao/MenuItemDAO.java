package com.alforsconsulting.pizzastore.menu.dao;

import com.alforsconsulting.pizzastore.menu.MenuItem;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by palfors on 5/13/16.
 */
public interface MenuItemDAO {
    /**
     * This is the method to be used to initialize
     * database resources ie. connection.
     */
    public void setDataSource(DataSource ds);
    /**
     * This is the method to be used to create
     * a record in the table.
     */
    public void create(MenuItem menuItem);
    /**
     * This is the method to be used to create
     * a record in the table.
     */
    public void create(long id,  String menuItemType, String name, double price);
    /**
     * This is the method to be used to list down
     * a record from the table corresponding
     * to a passed id.
     */
    public MenuItem getMenuItem(long id);
    /**
     * This is the method to be used to list down
     * a record from the table corresponding
     * to a passed id.
     */
    public MenuItem getMenuItem(String menuItemType);
    /**
     * This is the method to be used to list down
     * all the records from the table.
     */
    public List<MenuItem> list();
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
    public void update(long id, String name, double price);
}
