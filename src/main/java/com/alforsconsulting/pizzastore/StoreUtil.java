package com.alforsconsulting.pizzastore;

import com.alforsconsulting.pizzastore.order.OrderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by palfors on 5/24/16.
 */
public class StoreUtil {
    private static final Logger logger = LogManager.getLogger();
    private static SessionFactory sessionFactory;
    private static ApplicationContext applicationContext;
    private static final String OBJECT_MAPPING = "PizzaStore";

    private static StoreUtil instance = new StoreUtil();

    public static StoreUtil getInstance() {
        return instance;
    }

    private StoreUtil() {
        applicationContext = AppContext.getInstance().getContext();
        sessionFactory = AppContext.getInstance().getSessionFactory();
    }

    public static PizzaStore newPizzaStore() {
        return (PizzaStore) applicationContext.getBean("pizzaStore");
    }

    public static PizzaStore create(String name) {

        PizzaStore pizzaStore = newPizzaStore();
        pizzaStore.setName(name);

        return pizzaStore;
    }

    public static void save(Session session, PizzaStore store) {
        logger.info("Saving pizzaStore [{}]", store);

        store.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));

        session.persist(store);

        // currently no children to save
    }

    public static void save(PizzaStore store) {
        logger.info("Saving (in transaction) pizzaStore [{}]", store);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        save(session, store);

        session.getTransaction().commit();
        session.close();
    }

    public static void merge(PizzaStore store) {
        logger.info("Merging (in transaction) pizzaStore [{}]", store);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.merge(store);

        session.getTransaction().commit();
        session.close();
    }

    public static PizzaStore getStore(long id) {
        logger.info("Retrieving (in transaction) pizzaStore [{}]", id);
        PizzaStore store = null;

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        store = getStore(session, id);

        session.getTransaction().commit();
        session.close();

        return store;
    }

    public static PizzaStore getStore(Session session, long id) {
        logger.info("Retrieving pizzaStore [{}]", id);
        PizzaStore store = null;

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING)
                .append(" where storeId = :storeId");

        Query query =  session.createQuery(builder.toString());
        query.setParameter("storeId", id);

        List<PizzaStore> stores = (List<PizzaStore>) query.list();
        if (stores.size() == 0) {
            logger.debug("Unable to find store [{}]", id);

        } else if (stores.size() == 1) {
            store = stores.get(0);
            logger.debug("Found store [{}]", store);
        } else {
            logger.debug("Found [{}] stores. Expecting only one", stores.size());
            // TODO handle more gracefully
            // for now, return null
        }

        return store;
    }

    public static PizzaStore getStore(String name) {
        logger.info("Retrieving (in transaction) pizzaStore [{}]", name);
        PizzaStore store = null;

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        store = getStore(session, name);

        session.getTransaction().commit();
        session.close();

        return store;
    }

    public static PizzaStore getStore(Session session, String name) {
        logger.info("Retrieving pizzaStore [{}]", name);
        PizzaStore store = null;

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING)
                .append(" where name = :name");

        Query query =  session.createQuery(builder.toString());
        query.setParameter("name", name);

        List<PizzaStore> stores = (List<PizzaStore>) query.list();
        if (stores.size() == 0) {
            logger.debug("Unable to find store [{}]", name);

        } else if (stores.size() == 1) {
            store = stores.get(0);
            logger.debug("Found store [{}]", store);
        } else {
            logger.debug("Found [{}] stores. Expecting only one", stores.size());
            // TODO handle more gracefully
            // for now, return null
        }

        return store;
    }

    public static List<PizzaStore> getStores() {
        logger.info("Retrieving (in transaction) stores");
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<PizzaStore> stores = getStores(session);

        session.getTransaction().commit();
        session.close();

        return stores;
    }

    public static List<PizzaStore> getStores(Session session) {
        logger.info("Retrieving stores");

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING);
        List<PizzaStore> stores =
                (List<PizzaStore>) session.createQuery(builder.toString()).list();

        return stores;
    }

    public static void delete(Session session, PizzaStore store) {
        logger.debug("Deleting store [{}]", store);

        // delete orders tied to this store
        OrderUtil.deleteStoreOrders(store.getStoreId(), session);

        session.delete(store);
    }

    public static void delete(PizzaStore store) {
        logger.debug("Deleting (in transaction) store [{}]", store);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        delete(session, store);

        session.getTransaction().commit();
        session.close();
    }

    public static void delete(long storeId) {
        logger.info("Deleting (in transaction) store [{}]", storeId);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        PizzaStore store = getStore(session, storeId);
        delete(session, store);

        session.getTransaction().commit();
        session.close();
    }


}
