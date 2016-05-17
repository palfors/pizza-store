package com.alforsconsulting.pizzastore.menu.detail.dao;

import com.alforsconsulting.pizzastore.menu.detail.MenuItemDetail;
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
public class MenuItemDetailJDBCTemplate implements MenuItemDetailDAO {
    private static final Logger logger = LogManager.getLogger();

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public MenuItemDetail getMenuItemDetail(long menuItemDetailId) {
        logger.debug("Retrieving menuItemDetail [{}]", menuItemDetailId);
        String SQL = "select * from MENUITEM_DETAIL where menuItemDetailId = ?";
        MenuItemDetail menuItemDetail = null;
        try {
            menuItemDetail = jdbcTemplateObject.queryForObject(SQL,
                    new Object[]{menuItemDetailId}, new MenuItemDetailMapper());
            logger.debug("Found MenuItemDetail [{}]", menuItemDetail);
        } catch (EmptyResultDataAccessException e) {
            // allow 0 results and return null
            logger.debug("MenuItemDetail [{}] does not exist", menuItemDetailId);
        }
        return menuItemDetail;
    }

    @Override
    public MenuItemDetail getMenuItemDetail(long menuItemId, String detailType, String name) {
        logger.debug("Retrieving menuItemDetail [{}][{}][{}]", menuItemId, detailType, name);
        String SQL = "select * from MENUITEM_DETAIL where menuItemId = ? and detailType = ? and name = ?";
        MenuItemDetail menuItemDetail = null;
        try {
            menuItemDetail = jdbcTemplateObject.queryForObject(SQL,
                    new Object[]{menuItemId, detailType, name}, new MenuItemDetailMapper());
            logger.debug("Found MenuItemDetail [{}]", menuItemDetail);
        } catch (EmptyResultDataAccessException e) {
            // allow 0 results and return null
            logger.debug("MenuItemDetail [{}][{}][{}] does not exist", menuItemId, detailType, name);
        }
        return menuItemDetail;
    }

    public List<MenuItemDetail> list() {
        logger.debug("Retrieving all customers ");
        String SQL = "select * from MENUITEM_DETAIL";
        List<MenuItemDetail> menuItemDetails = jdbcTemplateObject.query(SQL,
                new MenuItemDetailMapper());
        return menuItemDetails;
    }

}
