package com.alforsconsulting.pizzastore.dao;

import com.alforsconsulting.pizzastore.PizzaStore;
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
public class PizzaStoreJDBCTemplate implements PizzaStoreDAO {
    private static final Logger logger = LogManager.getLogger();

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public void create(PizzaStore pizzaStore) {
        logger.debug("Creating pizzaStore [{}]", pizzaStore);
        create(pizzaStore.getStoreId(), pizzaStore.getName());
    }

    public void create(long storeId, String name) {
        logger.debug("Creating pizzaStore [{}] [{}]", storeId, name);
        String SQL = "insert into STORE (storeId, name) values (?, ?)";

        jdbcTemplateObject.update(SQL, storeId, name);
    }

    public PizzaStore getPizzaStore(long storeId) {
        logger.debug("Retrieving pizzaStore [{}]", storeId);

        String SQL = "select * from STORE where storeId = ?";
        PizzaStore pizzaStore = null;
        try {
            pizzaStore = jdbcTemplateObject.queryForObject(SQL,
                    new Object[]{storeId}, new PizzaStoreMapper());
            logger.debug("Found pizzaStore [{}]", pizzaStore);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("PizzaStore [{}] does not exist", storeId);
            // allow 0 results and return null
        }
        return pizzaStore;
    }

    public List<PizzaStore> list() {
        String SQL = "select * from STORE";
        List<PizzaStore> pizzaStores = jdbcTemplateObject.query(SQL,
                new PizzaStoreMapper());
        logger.debug("Retrieving [{}] PizzaStores", pizzaStores.size());
        return pizzaStores;
    }

    public void delete(PizzaStore pizzaStore) {
        logger.debug("Deleting pizzaStore [{}]", pizzaStore);
        delete(pizzaStore.getStoreId());
    }

    public void delete(long storeId) {
        logger.debug("Deleting pizzaStore [{}]", storeId);
        String SQL = "delete from STORE where storeId = ?";
        jdbcTemplateObject.update(SQL, storeId);
    }

    public void update(PizzaStore pizzaStore) {
        logger.debug("Updating pizzaStore [{}]", pizzaStore);
        update(pizzaStore.getStoreId(),
                pizzaStore.getName());
    }

    public void update(long storeId, String name) {
        logger.debug("Updating pizzaStore [{}] [{}]", storeId, name);
        String SQL = "update STORE set name = ? where storeId = ?";
        jdbcTemplateObject.update(SQL, name, storeId);
    }

    public long getMaxId() {
        long storeId = 0;
        String SQL = "select max(storeId) from STORE";
        Long maxId = jdbcTemplateObject.queryForObject(SQL, Long.class);
        if (maxId == null)
            maxId = new Long(0);

        logger.debug("Retrieved max pizzaStoreId [{}]", maxId);
        return maxId;
    }

}
