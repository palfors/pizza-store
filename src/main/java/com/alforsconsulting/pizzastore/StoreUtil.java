package com.alforsconsulting.pizzastore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;

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
        logger.debug("Saving pizzaStore [{}]", store);

        session.saveOrUpdate(store);

        // currently no children to save
    }

    public static void save(PizzaStore store) {
        logger.debug("Saving pizzaStore [{}]", store);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        save(session, store);

        session.getTransaction().commit();
        session.close();
    }

    public static PizzaStore getStore(long id) {
        PizzaStore store = null;

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING)
                .append(" where storeId = :storeId");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

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

        session.getTransaction().commit();
        session.close();

        return store;
    }

    public static PizzaStore getStore(String name) {
        PizzaStore store = null;

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING)
                .append(" where name = :name");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

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

        session.getTransaction().commit();
        session.close();

        return store;
    }

    public static List<PizzaStore> getStores() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING);
        List<PizzaStore> stores =
                (List<PizzaStore>) session.createQuery(builder.toString()).list();

        session.getTransaction().commit();
        session.close();

        return stores;
    }

    public static void delete(Session session, PizzaStore store) {
        logger.debug("Deleting customer [{}]", store);

        // currently no children to delete

        session.delete(store);
    }

    public static void delete(PizzaStore store) {
        logger.debug("Deleting customer [{}]", store);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        delete(session, store);

        session.getTransaction().commit();
        session.close();
    }

}
