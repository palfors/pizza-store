package com.alforsconsulting.pizzastore.menu.dao;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.customer.Customer;
import com.alforsconsulting.pizzastore.menu.MenuItem;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by palfors on 5/13/16.
 */
public class MenuItemJDBCTemplate implements MenuItemDAO {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public void create(MenuItem menuItem) {
        create(menuItem.getMenuItemId(),
                menuItem.getMenuItemType().getBeanName(),
                menuItem.getName(),
                menuItem.getPrice());
    }

    public void create(long id,  String menuItemBeanId, String name, double price) {
        String SQL = "insert into MENUITEM (menuItemId, menuItemType, name, price) values (?, ?, ?, ?)";

        jdbcTemplateObject.update(SQL, id, menuItemBeanId, name, price);
        System.out.println("Created Record menuItemId = " + id +
                " menuItemBeanName = " + menuItemBeanId +
                " name = " + name +
                " price = " + price);
    }

    public MenuItem getMenuItem(long id) {
        String SQL = "select * from MENUITEM where menuItemId = ?";
        MenuItem menuItem = null;
        try {
            menuItem = jdbcTemplateObject.queryForObject(SQL,
                    new Object[]{id}, new MenuItemMapper());
        } catch (EmptyResultDataAccessException e) {
            // allow 0 results and return null
        }

        return menuItem;
    }

    public List<MenuItem> list() {
        String SQL = "select * from MENUITEM";
        List<MenuItem> menuItems = jdbcTemplateObject.query(SQL, new MenuItemMapper());
        return menuItems;
    }

    public void delete(long id) {
        String SQL = "delete from MENUITEM where menuItemId = ?";
        jdbcTemplateObject.update(SQL, id);
        System.out.println("Deleted MenuItem with ID = " + id );
    }

    public void update(MenuItem menuItem) {
        update(menuItem.getMenuItemId(),
                menuItem.getName(),
                menuItem.getPrice());
    }

    public void update(long id, String name, double price) {
        String SQL = "update MENUITEM set name = ?, price = ? where menuItemId = ?";
        jdbcTemplateObject.update(SQL, name, price, id);
        System.out.println("Updated MenuItem with ID = " + id );
    }

    public long getMaxId() {
        String SQL = "select max(menuItemId) from MENUITEM";
        Long maxId = jdbcTemplateObject.queryForObject(SQL, Long.class);
        if (maxId == null)
            maxId = new Long(0);

        return maxId;
    }

}
