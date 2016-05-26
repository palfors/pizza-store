package com.alforsconsulting.pizzastore.menu;

import com.alforsconsulting.pizzastore.AppContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;

import java.awt.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by palfors on 5/23/16.
 */
public class MenuItemUtil {
    private static final Logger logger = LogManager.getLogger();
    private static ApplicationContext applicationContext =
            AppContext.getInstance().getContext();
    private static SessionFactory sessionFactory =
            AppContext.getInstance().getSessionFactory();
    private static final String OBJECT_MAPPING = "GenericMenuItem";

    private static MenuItemUtil instance = new MenuItemUtil();

    private MenuItemUtil() {
    }

    public static MenuItemUtil getInstance() {
        return instance;
    }

    public static MenuItem newMenuItem(MenuItemType menuItemType) {
        return (MenuItem) applicationContext.getBean(menuItemType.getBeanName());
    }

    public static MenuItem create(MenuItemType menuItemType, String name, double price) {
        MenuItem menuItem = newMenuItem(menuItemType);
        menuItem.setMenuItemType(menuItemType.getBeanName());
        menuItem.setName(name);
        menuItem.setPrice(price);

        return menuItem;
    }

    public static void save(Session session, MenuItem menuItem) {
        logger.debug("Saving menuItem [{}]", menuItem);

        menuItem.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));

        session.persist(menuItem);

        // currently no children to save
    }

    public static void save(MenuItem menuItem) {
        logger.debug("Saving (in transaction) menuItem [{}]", menuItem);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        save(session, menuItem);

        session.getTransaction().commit();
        session.close();
    }

    public static MenuItem getMenuItem(long menuItemId) {
        logger.debug("Saving (in transaction) menuItem [{}]", menuItemId);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        MenuItem menuItem = getMenuItem(session, menuItemId);

        session.getTransaction().commit();
        session.close();

        return menuItem;
    }

    public static MenuItem getMenuItem(Session session, long menuItemId) {
        logger.debug("Saving menuItem [{}]", menuItemId);
        MenuItem menuItem = null;

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING)
                .append(" where menuItemId = :menuItemId");
        Query query =  session.createQuery(builder.toString());
        query.setParameter("menuItemId", menuItemId);

        List<MenuItem> menuItems = (List<MenuItem>) query.list();
        if (menuItems.size() == 0) {
            logger.debug("Unable to find menuItem [{}]", menuItemId);

        } else if (menuItems.size() == 1) {
            menuItem = menuItems.get(0);
            logger.debug("Found menuItem [{}]", menuItem);
        } else {
            logger.debug("Found [{}] menuItems. Expecting only one", menuItems.size());
            // TODO handle more gracefully
            // for now, return null
        }

        return menuItem;
    }

    public static MenuItem getMenuItem(MenuItemType menuItemType) {
        logger.debug("Saving (in transaction) menuItem [{}]", menuItemType);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        MenuItem menuItem = getMenuItem(session, menuItemType);

        session.getTransaction().commit();
        session.close();

        return menuItem;
    }

    public static MenuItem getMenuItem(Session session, MenuItemType menuItemType) {
        logger.debug("Saving menuItem [{}]", menuItemType);
        MenuItem menuItem = null;

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING)
                .append(" where menuItemType = :menuItemType");
        Query query =  session.createQuery(builder.toString());
        query.setParameter("menuItemType", menuItemType.getBeanName());

        List<MenuItem> menuItems = (List<MenuItem>) query.list();
        if (menuItems.size() == 0) {
            logger.debug("Unable to find menuItem [{}]", menuItemType.getBeanName());

        } else if (menuItems.size() == 1) {
            menuItem = menuItems.get(0);
            logger.debug("Found menuItem [{}]", menuItem);
        } else {
            logger.debug("Found [{}] menuItems. Expecting only one", menuItems.size());
            // TODO handle more gracefully
            // for now, return null
        }

        return menuItem;
    }

    public static List<MenuItem> getMenuItems() {
        logger.debug("Retrieving (in transaction) menuItems");
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<MenuItem> menuItems = getMenuItems(session);

        session.getTransaction().commit();
        session.close();

        return menuItems;
    }

    public static List<MenuItem> getMenuItems(Session session) {
        logger.debug("Retrieving menuItems ");

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING);
        List<MenuItem> menuItems =
                (List<MenuItem>) session.createQuery(builder.toString()).list();

        return menuItems;
    }

    public static void delete(Session session, MenuItem menuItem) {
        logger.debug("Deleting menuItem [{}]", menuItem);

        // currently no children to delete

        session.delete(menuItem);
    }

    public static void delete(MenuItem menuItem) {
        logger.debug("Deleting (in transaction) menuItem [{}]", menuItem);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        delete(session, menuItem);

        session.getTransaction().commit();
        session.close();
    }

}
