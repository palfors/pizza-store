package com.alforsconsulting.pizzastore.menu.detail;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.menu.MenuItem;
import com.alforsconsulting.pizzastore.menu.MenuItemType;
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

    public static MenuItemDetail create(long menuItemId, MenuItemDetailType menuItemDetailType, String name, double price) {
        MenuItemDetail menuItemDetail = (MenuItemDetail) applicationContext.getBean("menuItemDetail");
        menuItemDetail.setMenuItemId(menuItemId);
        menuItemDetail.setDetailType(menuItemDetailType.getBeanName());
        menuItemDetail.setName(name);
        menuItemDetail.setPrice(price);

        return save(menuItemDetail);
    }

    public static MenuItemDetail save(MenuItemDetail menuItemDetail) {
        logger.debug("Saving menuItemDetail [{}]", menuItemDetail);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(menuItemDetail);

        session.getTransaction().commit();
        session.close();

        return menuItemDetail;
    }

    public static MenuItemDetail getMenuItemDetail(long menuItemDetailId) {
        MenuItemDetail menuItemDetail = null;

        Session session = sessionFactory.openSession();
        session.beginTransaction();

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

        session.getTransaction().commit();
        session.close();

        return menuItemDetail;
    }

    public static MenuItemDetail getMenuItemDetail(long menuItemId, MenuItemDetailType menuItemDetailType, String name) {
        MenuItemDetail menuItemDetail = null;

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING)
                .append(" where menuItemId = :menuItemId")
                .append(" and detailType = :detailType")
                .append(" and name = :name");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

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

        session.getTransaction().commit();
        session.close();

        return menuItemDetail;
    }

    public static List<MenuItemDetail> getMenuItemDetails() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING);
        List<MenuItemDetail> details =
                (List<MenuItemDetail>) session.createQuery(builder.toString()).list();

        session.getTransaction().commit();
        session.close();

        return details;
    }

    public static List<MenuItemDetail> getMenuItemDetails(long menuItemId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING)
                .append(" where menuItemId = :menuItemId");
        Query query =  session.createQuery(builder.toString());
        query.setParameter("menuItemId", menuItemId);
        List<MenuItemDetail> details = (List<MenuItemDetail>) query.list();

        session.getTransaction().commit();
        session.close();

        return details;
    }

    public static List<MenuItemDetail> getMenuItemDetails(MenuItemDetailType detailType) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING)
                .append(" and detailType = :detailType");
        Query query =  session.createQuery(builder.toString());
        query.setParameter("detailType", detailType.getBeanName());
        List<MenuItemDetail> details = (List<MenuItemDetail>) query.list();

        session.getTransaction().commit();
        session.close();

        return details;
    }

    public static boolean delete(MenuItemDetail menuItemDetail) {
        logger.debug("Deleting menuItemDetail [{}]", menuItemDetail);

        boolean result = true;

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // TODO: check for success
        session.delete(menuItemDetail);

        session.getTransaction().commit();
        session.close();

        return result;
    }

}
