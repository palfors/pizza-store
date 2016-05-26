package com.alforsconsulting.pizzastore.order.line.detail.dao;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.order.line.detail.OrderLineDetail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by palfors on 5/13/16.
 */
public class OrderLineDetailMapper implements RowMapper<OrderLineDetail> {
    private static final Logger logger = LogManager.getLogger();

    public OrderLineDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        long orderLineDetailId = rs.getLong("orderLineDetailId");
//        OrderLineDetail orderLineDetail = (OrderLineDetail) AppContext.getInstance(
//            ).getContext().getBean("orderLineDetail", orderLineDetailId);
        OrderLineDetail orderLineDetail = (OrderLineDetail) AppContext.getInstance(
            ).getContext().getBean("orderLineDetail");
        orderLineDetail.setOrderLineDetailId(orderLineDetailId);

        orderLineDetail.setOrderLineId(rs.getLong("orderLineId"));
        orderLineDetail.setMenuItemDetailId(rs.getLong("menuItemDetailId"));
        orderLineDetail.setPlacement(rs.getString("placement"));
        orderLineDetail.setPrice(rs.getDouble("price"));
        orderLineDetail.setCreateDate(rs.getTimestamp("createDate"));
        orderLineDetail.setLastModifiedDate(rs.getTimestamp("lastModifiedDate"));

        logger.debug("Mapped orderLineDetail [{}]", orderLineDetail);
        return orderLineDetail;
    }
}
