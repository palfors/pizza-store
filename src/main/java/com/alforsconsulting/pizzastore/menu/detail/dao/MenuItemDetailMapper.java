package com.alforsconsulting.pizzastore.menu.detail.dao;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.customer.Customer;
import com.alforsconsulting.pizzastore.menu.detail.MenuItemDetail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by palfors on 5/13/16.
 */
public class MenuItemDetailMapper implements RowMapper<MenuItemDetail> {
    private static final Logger logger = LogManager.getLogger();

    public MenuItemDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        MenuItemDetail menuItemDetail = (MenuItemDetail) AppContext.getInstance(
            ).getContext().getBean("menuItemDetail");
        menuItemDetail.setMenuItemDetailId(rs.getLong("menuItemDetailId"));
        menuItemDetail.setMenuItemId(rs.getLong("menuItemId"));
        menuItemDetail.setDetailType(rs.getString("detailType"));
        menuItemDetail.setName(rs.getString("name"));
        menuItemDetail.setPrice(rs.getDouble("price"));

        logger.debug("Mapped menuItemDetail [{}]", menuItemDetail);
        return menuItemDetail;
    }
}
