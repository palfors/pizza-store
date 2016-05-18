package com.alforsconsulting.pizzastore.dao;

import com.alforsconsulting.pizzastore.PizzaStore;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by palfors on 5/13/16.
 */
public interface PizzaStoreDAO {
    /**
     * This is the method to be used to initialize
     * database resources ie. connection.
     */
    public void setDataSource(DataSource ds);
    /**
     * This is the method to be used to create
     * a record in the table.
     */
    public void create(PizzaStore pizzaStore);
    /**
     * This is the method to be used to create
     * a record in the table.
     */
    public void create(long id, String name);
    /**
     * This is the method to be used to list down
     * a record from the table corresponding
     * to a passed id.
     */
    public PizzaStore getPizzaStore(long id);
    /**
     * This is the method to be used to list down
     * all the records from the table.
     */
    public List<PizzaStore> list();
    /**
     * This is the method to be used to delete
     * a record from the table corresponding
     * to a passed id.
     */
    public void delete(PizzaStore pizzaStore);
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
    public void update(PizzaStore pizzaStore);
    /**
     * This is the method to be used to update
     * a record in the table.
     */
    public void update(long id, String name);

}
