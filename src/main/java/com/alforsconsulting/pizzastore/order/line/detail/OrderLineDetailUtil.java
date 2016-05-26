package com.alforsconsulting.pizzastore.order.line.detail;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.menu.pizza.topping.ToppingPlacement;
import com.alforsconsulting.pizzastore.order.line.OrderLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by palfors on 5/23/16.
 */
public class OrderLineDetailUtil {
    private static final Logger logger = LogManager.getLogger();
    private static ApplicationContext applicationContext =
            AppContext.getInstance().getContext();
    private static SessionFactory sessionFactory =
            AppContext.getInstance().getSessionFactory();
    private static final String OBJECT_MAPPING = "OrderLineDetail";

    private static OrderLineDetailUtil instance = new OrderLineDetailUtil();

    private OrderLineDetailUtil() {
    }

    public static OrderLineDetailUtil getInstance() {
        return instance;
    }

    public static OrderLineDetail newOrderLineDetail() {
        return (OrderLineDetail) applicationContext.getBean("orderLineDetail");
    }

    public static OrderLineDetail create(long orderLineId, long menuItemDetailId, double price) {
        return create(orderLineId, menuItemDetailId, ToppingPlacement.WHOLE, price);
    }

    public static OrderLineDetail create(long orderLineId, long menuItemDetailId, ToppingPlacement placement, double price) {
        OrderLineDetail orderLineDetail = newOrderLineDetail();
        orderLineDetail.setOrderLineId(orderLineId);
        orderLineDetail.setMenuItemDetailId(menuItemDetailId);
        orderLineDetail.setPlacement(placement.name());
        orderLineDetail.setPrice(price);

        return orderLineDetail;
    }

    public static void save(Session session, OrderLineDetail orderLineDetail) {
        logger.debug("Saving orderLineDetail [{}]", orderLineDetail);

        session.saveOrUpdate(orderLineDetail);

        orderLineDetail.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));

        // currently no children to save
    }

    public static void save(OrderLineDetail orderLineDetail) {
        logger.debug("Saving orderLineDetail [{}]", orderLineDetail);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        save(session, orderLineDetail);

        session.getTransaction().commit();
        session.close();
    }

    public static OrderLineDetail getOrderLineDetail(long orderLineDetailId) {
        OrderLineDetail orderLineDetail = null;

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING)
                .append(" where orderLineDetailId = :orderLineDetailId");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query =  session.createQuery(builder.toString());
        query.setParameter("orderLineDetailId", orderLineDetailId);

        List<OrderLineDetail> orderLineDetails = (List<OrderLineDetail>) query.list();
        if (orderLineDetails.size() == 0) {
            logger.debug("Unable to find orderLineDetail [{}]", orderLineDetailId);

        } else if (orderLineDetails.size() == 1) {
            orderLineDetail = orderLineDetails.get(0);
            logger.debug("Found orderLineDetail [{}]", orderLineDetail);
        } else {
            logger.debug("Found [{}] orderLines. Expecting only one", orderLineDetails.size());
            // TODO handle more gracefully
            // for now, return null
        }

        session.getTransaction().commit();
        session.close();

        return orderLineDetail;
    }

    public static List<OrderLineDetail> getOrderLineDetails() {
        logger.debug("Loading orderLineDetails");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING);
        List<OrderLineDetail> orderLineDetails =
                (List<OrderLineDetail>) session.createQuery(builder.toString()).list();

        session.getTransaction().commit();
        session.close();

        return orderLineDetails;
    }

    public static List<OrderLine> getOrderLineDetails(long orderLineId) {
        logger.debug("Loading orderLineDetails for orderLine [{}]", orderLineId);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING)
                .append(" where orderLineId = :orderLineId");
        Query query = session.createQuery(builder.toString());
        query.setParameter("orderLineId", orderLineId);

        List<OrderLine> orderLines = (List<OrderLine>) query.list();

        session.getTransaction().commit();
        session.close();

        return orderLines;
    }

    public static void delete(Session session, OrderLineDetail orderLineDetail) {
        logger.debug("Deleting orderLineDetail [{}]", orderLineDetail);

        // currently no children to delete

        session.delete(orderLineDetail);
    }

    public static void delete(OrderLineDetail orderLineDetail) {
        logger.debug("Deleting orderLineDetail [{}]", orderLineDetail);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        delete(session, orderLineDetail);

        session.getTransaction().commit();
        session.close();
    }
}
