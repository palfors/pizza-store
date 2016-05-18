package com.alforsconsulting.pizzastore.menu.dao;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.customer.Customer;
import com.alforsconsulting.pizzastore.menu.MenuItem;
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
public class MenuItemJDBCTemplate implements MenuItemDAO {
    private static final Logger logger = LogManager.getLogger();

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public void create(MenuItem menuItem) {
        logger.debug("Creating menuItem [{}]", menuItem);
        create(menuItem.getMenuItemId(),
                menuItem.getMenuItemType(),
                menuItem.getName(),
                menuItem.getPrice());
    }

    public void create(long id,  String menuItemBeanId, String name, double price) {
        logger.debug("Creating menuItem [{}] [{}] [{}] [{}]", id, menuItemBeanId, name, price);
        String SQL = "insert into MENUITEM (menuItemId, menuItemType, name, price) values (?, ?, ?, ?)";

        jdbcTemplateObject.update(SQL, id, menuItemBeanId, name, price);
    }

    public MenuItem getMenuItem(long id) {
        logger.debug("Retrieving menuItem [{}]", id);
        String SQL = "select * from MENUITEM where menuItemId = ?";
        MenuItem menuItem = null;
        try {
            menuItem = jdbcTemplateObject.queryForObject(SQL,
                    new Object[]{id}, new MenuItemMapper());
            logger.debug("Found menuItem [{}]", menuItem);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("MenuItem [{}] does not exist", id);
            // allow 0 results and return null
        }

        return menuItem;
    }

    @Override
    public MenuItem getMenuItem(String menuItemType) {
        logger.debug("Retrieving menuItem [{}]", menuItemType);
        String SQL = "select * from MENUITEM where menuItemType = ?";
        MenuItem menuItem = null;
        try {
            menuItem = jdbcTemplateObject.queryForObject(SQL,
                    new Object[]{menuItemType}, new MenuItemMapper());
            logger.debug("Found menuItem [{}]", menuItem);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("MenuItem [{}] does not exist", menuItemType);
            // allow 0 results and return null
        }

        return menuItem;
    }

    public List<MenuItem> list() {
        logger.debug("Retrieving all menuItems");
        String SQL = "select * from MENUITEM";
        List<MenuItem> menuItems = jdbcTemplateObject.query(SQL, new MenuItemMapper());

        logger.debug("Found [{}] menuItems", menuItems.size());
        return menuItems;
    }

    public void delete(MenuItem menuItem) {
        logger.debug("Deleting menuItem [{}]", menuItem);
        delete(menuItem.getMenuItemId());
    }

    public void delete(long id) {
        logger.debug("Deleting menuItem [{}]", id);
        String SQL = "delete from MENUITEM where menuItemId = ?";
        jdbcTemplateObject.update(SQL, id);
    }

    public void update(MenuItem menuItem) {
        logger.debug("Updating menuItem [{}]", menuItem);
        update(menuItem.getMenuItemId(),
                menuItem.getName(),
                menuItem.getPrice());
    }

    public void update(long id, String name, double price) {
        logger.debug("Deleting menuItem [{}][{}][{}]", id, name, price);
        String SQL = "update MENUITEM set name = ?, price = ? where menuItemId = ?";
        jdbcTemplateObject.update(SQL, name, price, id);
    }

    public long getMaxId() {
        String SQL = "select max(menuItemId) from MENUITEM";
        Long maxId = jdbcTemplateObject.queryForObject(SQL, Long.class);
        if (maxId == null)
            maxId = new Long(0);

        logger.debug("Found max menuItemId [{}]", maxId);
        return maxId;
    }

}
