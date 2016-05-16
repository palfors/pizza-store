package com.alforsconsulting.pizzastore.customer.dao;

import com.alforsconsulting.pizzastore.customer.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger();

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public void create(Customer customer) {
        logger.debug("Creating customer [{}]", customer);
        create(customer.getCustomerId(), customer.getName());
    }

    public void create(long customerId, String name) {
        logger.debug("Creating customer [{}] [{}]", customerId, name);
        String SQL = "insert into CUSTOMER (customerId, name) values (?, ?)";
        jdbcTemplateObject.update(SQL, customerId, name);
    }

    public Customer getCustomer(long customerId) {
        logger.debug("Retrieving customer [{}]", customerId);
        String SQL = "select * from CUSTOMER where customerId = ?";
        Customer customer = null;
        try {
            customer = jdbcTemplateObject.queryForObject(SQL,
                    new Object[]{customerId}, new CustomerMapper());
            logger.debug("Found customer [{}]", customer);
        } catch (EmptyResultDataAccessException e) {
            // allow 0 results and return null
            logger.debug("Customer [{}] does not exist", customerId);
        }
        return customer;
    }

    public List<Customer> list() {
        logger.debug("Retrieving all customers ");
        String SQL = "select * from CUSTOMER";
        List<Customer> customers = jdbcTemplateObject.query(SQL,
                new CustomerMapper());
        return customers;
    }

    public void delete(Customer customer) {
        logger.debug("Deleting customer [{}]", customer);
        delete(customer.getCustomerId());
    }

    public void delete(long customerId) {
        logger.debug("Deleting customer [{}]", customerId);
        String SQL = "delete from CUSTOMER where customerId = ?";
        jdbcTemplateObject.update(SQL, customerId);
    }

    public void update(Customer customer) {
        logger.debug("Updating customer [{}]", customer);
        update(customer.getCustomerId(),
                customer.getName());
    }

    public void update(long customerId, String name) {
        logger.debug("Updating customer [{}] [{}]", customerId, name);
        String SQL = "update CUSTOMER set name = ? where customerId = ?";
        jdbcTemplateObject.update(SQL, name, customerId);
    }

    public long getMaxId() {
        String SQL = "select max(customerId) from CUSTOMER";
        Long maxId = jdbcTemplateObject.queryForObject(SQL, Long.class);
        if (maxId == null)
            maxId = new Long(0);

        logger.debug("Found max customerId [{}]", maxId);
        return maxId;
    }
}
