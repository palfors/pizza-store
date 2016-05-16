package com.alforsconsulting.pizzastore.dao;

import com.alforsconsulting.pizzastore.PizzaStore;
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
public class PizzaStoreJDBCTemplate implements PizzaStoreDAO {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public void create(PizzaStore pizzaStore) {
        create(pizzaStore.getStoreId(), pizzaStore.getName());
    }

    public void create(long storeId, String name) {
        String SQL = "insert into STORE (storeId, name) values (?, ?)";

        jdbcTemplateObject.update(SQL, storeId, name);
        System.out.println("Created Record storeId = " + storeId + " name = " + name);
    }

    public PizzaStore getPizzaStore(long storeId) {
        String SQL = "select * from STORE where storeId = ?";
        PizzaStore pizzaStore = null;
        try {
            pizzaStore = jdbcTemplateObject.queryForObject(SQL,
                    new Object[]{storeId}, new PizzaStoreMapper());
        } catch (EmptyResultDataAccessException e) {
            // allow 0 results and return null
        }
        return pizzaStore;
    }

    public List<PizzaStore> list() {
        String SQL = "select * from STORE";
        List<PizzaStore> pizzaStores = jdbcTemplateObject.query(SQL,
                new PizzaStoreMapper());
        return pizzaStores;
    }

    public void delete(PizzaStore pizzaStore) {
        delete(pizzaStore.getStoreId());
    }

    public void delete(long storeId) {
        String SQL = "delete from STORE where storeId = ?";
        jdbcTemplateObject.update(SQL, storeId);
        System.out.println("Deleted PizzaStore with ID = " + storeId );
    }

    public void update(PizzaStore pizzaStore) {
        update(pizzaStore.getStoreId(),
                pizzaStore.getName());
    }

    public void update(long storeId, String name) {
        String SQL = "update STORE set name = ? where storeId = ?";
        jdbcTemplateObject.update(SQL, name, storeId);
        System.out.println("Updated PizzaStore with ID = " + storeId );
    }

    public long getMaxId() {
        long storeId = 0;
        String SQL = "select max(storeId) from STORE";
        Long id = jdbcTemplateObject.queryForObject(SQL, Long.class);
        if (id != null) {
            storeId = id.longValue();
        }

        return storeId;
    }

}
