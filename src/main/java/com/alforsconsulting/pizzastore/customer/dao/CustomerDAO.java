package com.alforsconsulting.pizzastore.customer.dao;

import com.alforsconsulting.pizzastore.customer.Customer;
import com.alforsconsulting.pizzastore.customer.CustomerIdGenerator;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by palfors on 5/13/16.
 */
public interface CustomerDAO {
    /**
     * This is the method to be used to initialize
     * database resources ie. connection.
     */
    public void setDataSource(DataSource ds);
    /**
     * This is the method to be used to create
     * a record in the table.
     */
    public void create(Customer customer);
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
    public Customer getCustomer(long customerId);
    /**
     * This is the method to be used to list down
     * all the records from the table.
     */
    public List<Customer> list();
    /**
     * This is the method to be used to delete
     * a record from the table corresponding
     * to a passed id.
     */
    public void delete(Customer customer);
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
    public void update(Customer customer);
    /**
     * This is the method to be used to update
     * a record in the table.
     */
    public void update(long id, String name);

}
