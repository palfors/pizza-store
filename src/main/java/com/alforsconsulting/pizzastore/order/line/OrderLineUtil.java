package com.alforsconsulting.pizzastore.order.line;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.order.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;

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


    public static OrderLine create(long orderId, long menuItemId, int quantity, double price) {
        OrderLine orderLine = (OrderLine) applicationContext.getBean("orderLine");
        orderLine.setOrderId(orderId);
        orderLine.setMenuItemId(menuItemId);
        orderLine.setQuantity(quantity);
        orderLine.setPrice(price);

        return save(orderLine);
    }

    public static OrderLine save(OrderLine orderLine) {
        logger.debug("Saving orderLine [{}]", orderLine);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(orderLine);

        session.getTransaction().commit();
        session.close();

        return orderLine;
    }

    public static OrderLine getOrderLine(long orderLineId) {
        OrderLine orderLine = null;

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING)
                .append(" where orderLineId = :orderLineId");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

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

        session.getTransaction().commit();
        session.close();

        return orderLine;
    }

    public static List<OrderLine> getOrderLines() {
        logger.debug("Loading orderLines");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING);
        List<OrderLine> orderLines =
                (List<OrderLine>) session.createQuery(builder.toString()).list();

        session.getTransaction().commit();
        session.close();

        return orderLines;
    }

    public static List<OrderLine> getOrderLines(long orderId) {
        logger.debug("Loading orderLines for order [{}]", orderId);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING)
                .append(" where orderId = :orderId");
        Query query = session.createQuery(builder.toString());
        query.setParameter("orderId", orderId);

        List<OrderLine> orderLines = (List<OrderLine>) query.list();

        session.getTransaction().commit();
        session.close();

        return orderLines;
    }

    public static boolean delete(OrderLine orderLine) {
        logger.debug("Deleting orderLine [{}]", orderLine);

        boolean result = true;

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // TODO: check for success
        session.delete(orderLine);

        session.getTransaction().commit();
        session.close();

        return result;
    }
}
