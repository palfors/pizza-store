package com.alforsconsulting.pizzastore.order.line.detail.dao;

import com.alforsconsulting.pizzastore.menu.detail.MenuItemDetail;
import com.alforsconsulting.pizzastore.order.line.detail.OrderLineDetail;
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
public class OrderLineDetailJDBCTemplate implements OrderLineDetailDAO {
    private static final Logger logger = LogManager.getLogger();

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public void create(OrderLineDetail orderLineDetail) {
        logger.debug("Creating orderLineDetail [{}]", orderLineDetail);
        create(orderLineDetail.getOrderLineDetailId(),
                orderLineDetail.getOrderLineId(),
                orderLineDetail.getMenuItemDetailId(),
                orderLineDetail.getPlacement());
    }

    @Override
    public void create(long orderLineDetailId, long orderLineId, long menuItemDetailId, String placement) {
        logger.debug("Creating orderLineDetail [{}][{}][{}][{}]", orderLineDetailId, orderLineId, menuItemDetailId, placement);
        String SQL = "insert into ORDER_LINE_DETAIL (orderLineDetailId, orderLineId, menuItemDetailId, placement) values (?, ?, ?, ?)";
        jdbcTemplateObject.update(SQL, orderLineDetailId, orderLineId, menuItemDetailId, placement);
    }

    public OrderLineDetail getOrderLineDetail(long orderLineDetailId) {
        logger.debug("Retrieving orderLineDetail [{}]", orderLineDetailId);
        String SQL = "select * from ORDER_LINE_DETAIL where orderLineDetailId = ?";
        OrderLineDetail orderLineDetail = null;
        try {
            orderLineDetail = jdbcTemplateObject.queryForObject(SQL,
                    new Object[]{orderLineDetailId}, new OrderLineDetailMapper());
            logger.debug("Found orderLineDetail [{}]", orderLineDetail);
        } catch (EmptyResultDataAccessException e) {
            // allow 0 results and return null
            logger.debug("OrderLineDetail [{}] does not exist", orderLineDetailId);
        }
        return orderLineDetail;
    }

    public List<OrderLineDetail> list() {
        logger.debug("Retrieving all orderLineDetails ");
        String SQL = "select * from ORDER_LINE_DETAIL";
        List<OrderLineDetail> orderLineDetails = jdbcTemplateObject.query(SQL,
                new OrderLineDetailMapper());
        return orderLineDetails;
    }

    public void delete(OrderLineDetail orderLineDetail) {
        logger.debug("Deleting orderLineDetail [{}]", orderLineDetail);
        delete(orderLineDetail.getOrderLineDetailId());
    }

    public void delete(long orderLineDetailId) {
        logger.debug("Deleting orderLineDetail [{}]", orderLineDetailId);
        String SQL = "delete from ORDER_LINE_DETAIL where orderLineDetailId = ?";
        jdbcTemplateObject.update(SQL, orderLineDetailId);
    }

    public void update(OrderLineDetail orderLineDetail) {
        logger.debug("Updating customer [{}]", orderLineDetail);
        update(orderLineDetail.getOrderLineDetailId(),
                orderLineDetail.getOrderLineId(),
                orderLineDetail.getMenuItemDetailId(),
                orderLineDetail.getPlacement());
    }

    @Override
    public void update(long orderLineDetailId, long orderLineId, long menuItemDetailId, String placement) {
        logger.debug("Updating orderLineDetail [{}][{}][{}][{}]", orderLineDetailId, orderLineId, menuItemDetailId, placement);
        String SQL = "update ORDER_LINE_DETAIL set orderLineId = ?, menuItemDetailId = ?, placement = ? where orderLineDetailId = ?";
        jdbcTemplateObject.update(SQL, orderLineId, menuItemDetailId, placement, orderLineDetailId);
    }

    public long getMaxId() {
        logger.debug("OrderLineDetailJDBCTemplate.getMaxId() entry");
        String SQL = "select max(orderLineDetailId) from ORDER_LINE_DETAIL";
        Long maxId = jdbcTemplateObject.queryForObject(SQL, Long.class);
        if (maxId == null)
            maxId = new Long(0);

        logger.debug("Found max orderLineDetailId [{}]", maxId);
        return maxId;
    }

}
