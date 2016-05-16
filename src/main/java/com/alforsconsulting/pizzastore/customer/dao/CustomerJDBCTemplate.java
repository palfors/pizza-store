package com.alforsconsulting.pizzastore.customer.dao;

import com.alforsconsulting.pizzastore.customer.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by palfors on 5/13/16.
 */
@Component
public class CustomerJDBCTemplate implements CustomerDAO {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public void create(Customer customer) {
        create(customer.getCustomerId(), customer.getName());
    }

    public void create(long customerId, String name) {
        String SQL = "insert into CUSTOMER (customerId, name) values (?, ?)";

        jdbcTemplateObject.update(SQL, customerId, name);
        System.out.println("Created Record customerId = " + customerId + " name = " + name);
    }

    public Customer getCustomer(long customerId) {
        String SQL = "select * from CUSTOMER where customerId = ?";
        Customer customer = null;
        try {
            customer = jdbcTemplateObject.queryForObject(SQL,
                    new Object[]{customerId}, new CustomerMapper());
        } catch (EmptyResultDataAccessException e) {
            // allow 0 results and return null
        }
        return customer;
    }

    public List<Customer> list() {
        String SQL = "select * from CUSTOMER";
        List<Customer> customers = jdbcTemplateObject.query(SQL,
                new CustomerMapper());
        return customers;
    }

    public void delete(Customer customer) {
        delete(customer.getCustomerId());
    }

    public void delete(long customerId) {
        String SQL = "delete from CUSTOMER where customerId = ?";
        jdbcTemplateObject.update(SQL, customerId);
        System.out.println("Deleted Customer with ID = " + customerId );
    }

    public void update(Customer customer) {
        update(customer.getCustomerId(),
                customer.getName());
    }

    public void update(long customerId, String name) {
        String SQL = "update CUSTOMER set name = ? where customerId = ?";
        jdbcTemplateObject.update(SQL, name, customerId);
        System.out.println("Updated Customer with ID = " + customerId );
    }

    public long getMaxId() {
        String SQL = "select max(customerId) from CUSTOMER";
        return jdbcTemplateObject.queryForObject(SQL, Long.class);
    }
}
