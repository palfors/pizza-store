package com.alforsconsulting.pizzastore.order.line;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.order.line.detail.OrderLineDetail;
import com.alforsconsulting.pizzastore.order.line.detail.OrderLineDetailUtil;
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
public class OrderLineUtil {
    private static final Logger logger = LogManager.getLogger();
    private static ApplicationContext applicationContext =
            AppContext.getInstance().getContext();
    private static SessionFactory sessionFactory =
            AppContext.getInstance().getSessionFactory();
    private static final String OBJECT_MAPPING = "OrderLine";

    private static OrderLineUtil instance = new OrderLineUtil();

    private OrderLineUtil() {
    }

    public static OrderLineUtil getInstance() {
        return instance;
    }

    public static OrderLine newOrderLine() {
        return (OrderLine) applicationContext.getBean("orderLine");
    }

    public static OrderLine create(long orderId, long menuItemId, int quantity, double price) {
        OrderLine orderLine = newOrderLine();
        orderLine.setOrderId(orderId);
        orderLine.setMenuItemId(menuItemId);
        orderLine.setQuantity(quantity);
        orderLine.setPrice(price);

        return orderLine;
    }

    public static void save(Session session, OrderLine orderLine) {
        logger.debug("Saving orderLine [{}]", orderLine);

        orderLine.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));

        session.persist(orderLine);

        // save order line details
        for (OrderLineDetail detail : orderLine.getOrderLineDetails()) {
            detail.setOrderLineId(orderLine.getOrderLineId());
            OrderLineDetailUtil.save(session, detail);
        }
    }

    public static void save(OrderLine orderLine) {
        logger.debug("Saving (in transaction) orderLine [{}]", orderLine);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        save(session, orderLine);

        session.getTransaction().commit();
        session.close();
    }

    public static void merge(OrderLine orderLine) {
        logger.info("Merging (in transaction) orderLine [{}]", orderLine);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.merge(orderLine);

        session.getTransaction().commit();
        session.close();
    }

    public static OrderLine getOrderLine(long orderLineId) {
        logger.debug("Retrieving (in transaction) orderLine [{}]", orderLineId);
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        OrderLine orderLine = getOrderLine(session, orderLineId);

        session.getTransaction().commit();
        session.close();

        return orderLine;
    }

    public static OrderLine getOrderLine(Session session, long orderLineId) {
        logger.debug("Retrieving orderLine [{}]", orderLineId);
        OrderLine orderLine = null;

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING)
                .append(" where orderLineId = :orderLineId");

        Query query =  session.createQuery(builder.toString());
        query.setParameter("orderLineId", orderLineId);

        List<OrderLine> orderLines = (List<OrderLine>) query.list();
        if (orderLines.size() == 0) {
            logger.debug("Unable to find orderLine [{}]", orderLineId);

        } else if (orderLines.size() == 1) {
            orderLine = orderLines.get(0);
            logger.debug("Found orderLine [{}]", orderLine);
        } else {
            logger.debug("Found [{}] orderLines. Expecting only one", orderLines.size());
            // TODO handle more gracefully
            // for now, return null
        }

        return orderLine;
    }

    public static List<OrderLine> getOrderLines() {
        logger.debug("Loading (in transaction) orderLines");
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<OrderLine> orderLines = getOrderLines(session);

        session.getTransaction().commit();
        session.close();

        return orderLines;
    }

    public static List<OrderLine> getOrderLines(Session session) {
        logger.debug("Loading orderLines");

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING);
        List<OrderLine> orderLines =
                (List<OrderLine>) session.createQuery(builder.toString()).list();

        return orderLines;
    }

    public static List<OrderLine> getOrderLines(long orderId) {
        logger.debug("Loading (in transaction) orderLines for order [{}]", orderId);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<OrderLine> orderLines = getOrderLines(session, orderId);

        session.getTransaction().commit();
        session.close();

        return orderLines;
    }

    public static List<OrderLine> getOrderLines(Session session, long orderId) {
        logger.debug("Loading orderLines for order [{}]", orderId);

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING)
                .append(" where orderId = :orderId");
        Query query = session.createQuery(builder.toString());
        query.setParameter("orderId", orderId);

        List<OrderLine> orderLines = (List<OrderLine>) query.list();

        return orderLines;
    }

    public static List<OrderLine> loadOrderLines(Session session, long orderId) {
        logger.debug("Loading orderLine details for order [{}]", orderId);

        List<OrderLine> lines = getOrderLines(session, orderId);
        for (OrderLine line : lines) {
            List<OrderLineDetail> details = OrderLineDetailUtil.getOrderLineDetails(session, line.getOrderLineId());
            line.addOrderLineDetails(details);
        }

        return lines;
    }

    public static void delete(Session session, OrderLine orderLine) {
        logger.debug("Deleting orderLine [{}]", orderLine);

        List<OrderLineDetail> details =
                OrderLineDetailUtil.getOrderLineDetails(session, orderLine.getOrderLineId());
        // delete line details first
        for (OrderLineDetail detail : details) {
            OrderLineDetailUtil.delete(session, detail);
        }
        session.delete(orderLine);
    }

    public static void delete(OrderLine orderLine) {
        logger.debug("Deleting (in transaction) orderLine [{}]", orderLine);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        delete(session, orderLine);

        session.getTransaction().commit();
        session.close();
    }

    public static void delete(long orderLineId) {
        logger.info("Deleting (in transaction) orderLine [{}]", orderLineId);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        OrderLine orderLine = getOrderLine(session, orderLineId);
        delete(session, orderLine);

        session.getTransaction().commit();
        session.close();
    }

}
