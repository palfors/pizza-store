package com.alforsconsulting.pizzastore.menu.detail;

import com.alforsconsulting.pizzastore.AppContext;
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
public class MenuItemDetailUtil {
    private static final Logger logger = LogManager.getLogger();
    private static ApplicationContext applicationContext =
            AppContext.getInstance().getContext();
    private static SessionFactory sessionFactory =
            AppContext.getInstance().getSessionFactory();
    private static final String OBJECT_MAPPING = "MenuItemDetail";

    private static MenuItemDetailUtil instance = new MenuItemDetailUtil();

    private MenuItemDetailUtil() {
    }

    public static MenuItemDetailUtil getInstance() {
        return instance;
    }

    public static MenuItemDetail newMenuItemDetail() {
        return (MenuItemDetail) applicationContext.getBean("menuItemDetail");
    }

    public static MenuItemDetail create(long menuItemId, MenuItemDetailType menuItemDetailType, String name, double price) {
        MenuItemDetail menuItemDetail = newMenuItemDetail();
        menuItemDetail.setMenuItemId(menuItemId);
        menuItemDetail.setDetailType(menuItemDetailType.getBeanName());
        menuItemDetail.setName(name);
        menuItemDetail.setPrice(price);

        return menuItemDetail;
    }

    public static void save(Session session, MenuItemDetail menuItemDetail) {
        logger.debug("Saving menuItemDetail [{}]", menuItemDetail);

        menuItemDetail.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));

        session.persist(menuItemDetail);

        // currently no children to save
    }

    public static void save(MenuItemDetail menuItemDetail) {
        logger.debug("Saving (in transaction) menuItemDetail [{}]", menuItemDetail);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        save(session, menuItemDetail);

        session.getTransaction().commit();
        session.close();
    }

    public static MenuItemDetail getMenuItemDetail(long menuItemDetailId) {
        logger.debug("Retrieving (in transaction) menuItemDetail [{}]", menuItemDetailId );
        MenuItemDetail menuItemDetail = null;

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        menuItemDetail = getMenuItemDetail(session, menuItemDetailId);

        session.getTransaction().commit();
        session.close();

        return menuItemDetail;
    }

    public static MenuItemDetail getMenuItemDetail(Session session, long menuItemDetailId) {
        logger.debug("Retrieving menuItemDetail [{}]", menuItemDetailId );
        MenuItemDetail menuItemDetail = null;

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING)
                .append(" where menuItemDetailId = :menuItemDetailId");
        Query query =  session.createQuery(builder.toString());
        query.setParameter("menuItemDetailId", menuItemDetailId);

        List<MenuItemDetail> menuItemDetails = (List<MenuItemDetail>) query.list();
        if (menuItemDetails.size() == 0) {
            logger.debug("Unable to find menuItemDetail [{}]", menuItemDetailId);

        } else if (menuItemDetails.size() == 1) {
            menuItemDetail = menuItemDetails.get(0);
            logger.debug("Found menuItemDetail [{}]", menuItemDetail);
        } else {
            logger.debug("Found [{}] menuItemDetails. Expecting only one", menuItemDetails.size());
            // TODO handle more gracefully
            // for now, return null
        }

        for ( MenuItemDetail itemDetail : menuItemDetails ) {
            logger.debug(itemDetail);
        }

        return menuItemDetail;
    }

    public static MenuItemDetail getMenuItemDetail(long menuItemId, MenuItemDetailType menuItemDetailType, String name) {
        logger.debug("Retrieving (in transaction) menuItemDetail [{}][{}][{}]", menuItemId, menuItemDetailType, name);
        MenuItemDetail menuItemDetail = null;

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        menuItemDetail = getMenuItemDetail(session, menuItemId, menuItemDetailType, name);

        session.getTransaction().commit();
        session.close();

        return menuItemDetail;
    }

    public static MenuItemDetail getMenuItemDetail(Session session, long menuItemId, MenuItemDetailType menuItemDetailType, String name) {
        logger.debug("Retrieving menuItemDetail [{}][{}][{}]", menuItemId, menuItemDetailType, name);
        MenuItemDetail menuItemDetail = null;

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING)
                .append(" where menuItemId = :menuItemId")
                .append(" and detailType = :detailType")
                .append(" and name = :name");

        Query query =  session.createQuery(builder.toString());
        query.setParameter("menuItemId", menuItemId);
        query.setParameter("detailType", menuItemDetailType.getBeanName());
        query.setParameter("name", name);

        List<MenuItemDetail> menuItemDetails = (List<MenuItemDetail>) query.list();
        if (menuItemDetails.size() == 0) {
            logger.debug("Unable to find menuItemDetail [{}][{}][{}]", menuItemId, menuItemDetailType.name(), name);

        } else if (menuItemDetails.size() == 1) {
            menuItemDetail = menuItemDetails.get(0);
            logger.debug("Found menuItemDetail [{}]", menuItemDetail);
        } else {
            logger.debug("Found [{}] menuItemDetails. Expecting only one", menuItemDetails.size());
            // TODO handle more gracefully
            // for now, return null
        }

        for ( MenuItemDetail itemDetail : menuItemDetails ) {
            logger.debug(itemDetail);
        }

        return menuItemDetail;
    }

    public static List<MenuItemDetail> getMenuItemDetails() {
        logger.debug("Retrieving (in transaction) menuItemDetails");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<MenuItemDetail> details = getMenuItemDetails(session);

        session.getTransaction().commit();
        session.close();

        return details;
    }

    public static List<MenuItemDetail> getMenuItemDetails(Session session) {
        logger.debug("Retrieving menuItemDetails");

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING);
        List<MenuItemDetail> details =
                (List<MenuItemDetail>) session.createQuery(builder.toString()).list();

        return details;
    }

    public static List<MenuItemDetail> getMenuItemDetails(long menuItemId) {
        logger.debug("Retrieving (in transaction) menuItemDetails by menuItem [{}]", menuItemId);
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<MenuItemDetail> details = getMenuItemDetails(session, menuItemId);

        session.getTransaction().commit();
        session.close();

        return details;
    }

    public static List<MenuItemDetail> getMenuItemDetails(Session session, long menuItemId) {
        logger.debug("Retrieving menuItemDetails by menuItem [{}]", menuItemId);

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING)
                .append(" where menuItemId = :menuItemId");
        Query query =  session.createQuery(builder.toString());
        query.setParameter("menuItemId", menuItemId);
        List<MenuItemDetail> details = (List<MenuItemDetail>) query.list();

        return details;
    }

    public static List<MenuItemDetail> getMenuItemDetails(MenuItemDetailType detailType) {
        logger.debug("Retrieving (in transaction) menuItemDetails by detailType [{}]", detailType);
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<MenuItemDetail> details = getMenuItemDetails(session, detailType);

        session.getTransaction().commit();
        session.close();

        return details;
    }

    public static List<MenuItemDetail> getMenuItemDetails(Session session, MenuItemDetailType detailType) {
        logger.debug("Retrieving menuItemDetails by detailType [{}]", detailType);

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING)
                .append(" and detailType = :detailType");
        Query query =  session.createQuery(builder.toString());
        query.setParameter("detailType", detailType.getBeanName());
        List<MenuItemDetail> details = (List<MenuItemDetail>) query.list();

        return details;
    }

    public static void delete(Session session, MenuItemDetail menuItemDetail) {
        logger.debug("Deleting menuItemDetail [{}]", menuItemDetail);

        // currently no children to delete

        session.delete(menuItemDetail);
    }

    public static void delete(MenuItemDetail menuItemDetail) {
        logger.debug("Deleting (in transaction) menuItemDetail [{}]", menuItemDetail);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        delete(session, menuItemDetail);

        session.getTransaction().commit();
        session.close();
    }

}
