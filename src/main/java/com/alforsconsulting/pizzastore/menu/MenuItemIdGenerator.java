package com.alforsconsulting.pizzastore.menu;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.menu.dao.MenuItemJDBCTemplate;

/**
 * Created by palfors on 5/12/16.
 */
public class MenuItemIdGenerator {

    private long menuItemId;

    private static MenuItemIdGenerator ourInstance = new MenuItemIdGenerator();

    public static MenuItemIdGenerator getInstance() {
        return ourInstance;
    }

    private MenuItemIdGenerator() {
        menuItemId = getCurrentMaxId();
    }

    private long getCurrentMaxId() {
System.out.println("getCurrentMaxId: entry");
        MenuItemJDBCTemplate jdbcTemplate = (MenuItemJDBCTemplate) AppContext.getInstance(
            ).getContext().getBean("menuItemJDBCTemplate");
System.out.println("getCurrentMaxId: exit");
        return jdbcTemplate.getMaxId();
    }

    private void incrementId() {
        menuItemId++;
    }

    public synchronized long generateId() {
        incrementId();
        return menuItemId;
    }
}

