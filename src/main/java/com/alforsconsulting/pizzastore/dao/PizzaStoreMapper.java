package com.alforsconsulting.pizzastore.dao;

import com.alforsconsulting.pizzastore.PizzaStore;
import com.alforsconsulting.pizzastore.customer.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by palfors on 5/13/16.
 */
public class PizzaStoreMapper implements RowMapper<PizzaStore> {
    private static final Logger logger = LogManager.getLogger();

    public PizzaStore mapRow(ResultSet rs, int rowNum) throws SQLException {
        PizzaStore pizzaStore = new PizzaStore(rs.getLong("storeId"));
        pizzaStore.setName(rs.getString("name"));

        logger.debug("Mapped pizzaStore [{}]", pizzaStore);
        return pizzaStore;
    }
}
