package com.alforsconsulting.pizzastore.dao;

import com.alforsconsulting.pizzastore.PizzaStore;
import com.alforsconsulting.pizzastore.customer.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by palfors on 5/13/16.
 */
public class PizzaStoreMapper implements RowMapper<PizzaStore> {
    public PizzaStore mapRow(ResultSet rs, int rowNum) throws SQLException {
        PizzaStore pizzaStore = new PizzaStore(rs.getLong("storeId"));
        pizzaStore.setName(rs.getString("name"));

        return pizzaStore;
    }
}
