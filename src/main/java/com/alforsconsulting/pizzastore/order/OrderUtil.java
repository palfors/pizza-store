package com.alforsconsulting.pizzastore.order;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.order.line.OrderLine;
import com.alforsconsulting.pizzastore.order.line.OrderLineUtil;
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
public class OrderUtil {
    private static final Logger logger = LogManager.getLogger();
    private static ApplicationContext applicationContext =
            AppContext.getInstance().getContext();
    private static SessionFactory sessionFactory =
            AppContext.getInstance().getSessionFactory();
    private static final String OBJECT_MAPPING = "Order";

    private static OrderUtil instance = new OrderUtil();

    private OrderUtil() {
    }

    public static OrderUtil getInstance() {
        return instance;
    }


    public static Order newOrder() {
        return (Order) applicationContext.getBean("order");
    }

    public static Order create(long storeId, long customerId, double price) {
        Order order = newOrder();
        order.setStoreId(storeId);
        order.setCustomerId(customerId);
        order.setPrice(price);

        return order;
    }

    public static void save(Session session, Order order) {
        logger.debug("Saving order [{}]", order);

        session.saveOrUpdate(order);

        // save order lines
        for (OrderLine line : order.getOrderLines()) {
            line.setOrderId(order.getOrderId());
            OrderLineUtil.save(session, line);
        }
    }

    public static void save(Order order) {
        logger.debug("Saving order [{}]", order);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        save(session, order);

        session.getTransaction().commit();
        session.close();
    }

    public static Order getOrder(long orderId) {
        Order order = null;

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING)
                .append(" where orderId = :orderId");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query =  session.createQuery(builder.toString());
        query.setParameter("orderId", orderId);

        List<Order> orders = (List<Order>) query.list();
        if (orders.size() == 0) {
            logger.debug("Unable to find order [{}]", orderId);

        } else if (orders.size() == 1) {
            order = orders.get(0);
            logger.debug("Found order [{}]", order);
        } else {
            logger.debug("Found [{}] orders. Expecting only one", orders.size());
            // TODO handle more gracefully
            // for now, return null
        }

        session.getTransaction().commit();
        session.close();

        return order;
    }

    public static List<Order> getOrders() {
        logger.debug("Loading orders");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING);
        List<Order> orders =
                (List<Order>) session.createQuery(builder.toString()).list();

        session.getTransaction().commit();
        session.close();

        return orders;
    }

    public static void delete(Session session, Order order) {
        logger.debug("Deleting order [{}]", order);

        // delete the order lines first
        for (OrderLine line : order.getOrderLines()) {
            OrderLineUtil.delete(session, line);
        }

        session.delete(order);
    }

    public static void delete(Order order) {
        logger.debug("Deleting order [{}]", order);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        delete(session, order);

        session.getTransaction().commit();
        session.close();
    }
}
