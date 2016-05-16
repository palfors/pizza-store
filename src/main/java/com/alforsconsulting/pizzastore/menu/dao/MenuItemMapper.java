package com.alforsconsulting.pizzastore.menu.dao;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.menu.MenuItem;
import com.alforsconsulting.pizzastore.menu.MenuItemType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by palfors on 5/13/16.
 */
public class MenuItemMapper implements RowMapper<MenuItem> {
    private static final Logger logger = LogManager.getLogger();

    public MenuItem mapRow(ResultSet rs, int rowNum) throws SQLException {

        String menuItemType = rs.getString("menuItemType");
        long menuItemId = rs.getLong("menuItemId");

        if (menuItemType == null) {
            throw new SQLException("menuItemType cannot be null!");
        }

        MenuItem menuItem = (MenuItem) AppContext.getInstance().getContext().getBean(menuItemType);
        menuItem.setMenuItemId(menuItemId);
        menuItem.setName(rs.getString("name"));
        menuItem.setPrice(rs.getDouble("price"));

        return menuItem;
    }
}
