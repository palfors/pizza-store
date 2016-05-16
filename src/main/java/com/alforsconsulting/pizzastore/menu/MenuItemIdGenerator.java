package com.alforsconsulting.pizzastore.menu;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.menu.dao.MenuItemJDBCTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by palfors on 5/12/16.
 */
public class MenuItemIdGenerator {
    private static final Logger logger = LogManager.getLogger();

    private long menuItemId;

    private static MenuItemIdGenerator ourInstance = new MenuItemIdGenerator();

    public static MenuItemIdGenerator getInstance() {
        return ourInstance;
    }

    private MenuItemIdGenerator() {
        menuItemId = getCurrentMaxId();
    }

    private long getCurrentMaxId() {
        MenuItemJDBCTemplate jdbcTemplate = (MenuItemJDBCTemplate) AppContext.getInstance(
            ).getContext().getBean("menuItemJDBCTemplate");
        return jdbcTemplate.getMaxId();
    }

    private void incrementId() {
        menuItemId++;
    }

    public synchronized long generateId() {
        incrementId();
        logger.debug("Generated next customerId [{}]", menuItemId);
        return menuItemId;
    }
}

