package com.alforsconsulting.pizzastore.customer;

import com.alforsconsulting.pizzastore.AppContext;
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
public class CustomerUtil {
    private static final Logger logger = LogManager.getLogger();
    private static ApplicationContext applicationContext =
            AppContext.getInstance().getContext();
    private static SessionFactory sessionFactory =
            AppContext.getInstance().getSessionFactory();
    private static final String OBJECT_MAPPING = "Customer";

    private static CustomerUtil instance = new CustomerUtil();

    private CustomerUtil() {
    }

    public static CustomerUtil getInstance() {
        return instance;
    }

    public static Customer newCustomer() {
        return (Customer) applicationContext.getBean("customer");
    }

    public static Customer create(String name) {
        Customer customer = newCustomer();
        customer.setName(name);

        return customer;
    }

    public static void save(Session session, Customer customer) {
        logger.debug("Saving customer [{}]", customer);

        session.saveOrUpdate(customer);

        // currently no children to save
    }

    public static void save(Customer customer) {
        logger.debug("Saving customer [{}]", customer);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        save(session, customer);

        session.getTransaction().commit();
        session.close();
    }

    public static Customer getCustomer(long id) {
        Customer customer = null;

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING)
                .append(" where customerId = :customerId");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query =  session.createQuery(builder.toString());
        query.setParameter("customerId", id);

        List<Customer> customers = (List<Customer>) query.list();
        if (customers.size() == 0) {
            logger.debug("Unable to find customer [{}]", id);

        } else if (customers.size() == 1) {
            customer = customers.get(0);
            logger.debug("Found customer [{}]", customer);
        } else {
            logger.debug("Found [{}] customers. Expecting only one", customers.size());
            // TODO handle more gracefully
            // for now, return null
        }

        session.getTransaction().commit();
        session.close();

        return customer;
    }

    public static List<Customer> getCustomers() {
        logger.debug("Loading customers");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        StringBuilder builder = new StringBuilder("from ").append(OBJECT_MAPPING);
        List<Customer> customers =
                (List<Customer>) session.createQuery(builder.toString()).list();

        session.getTransaction().commit();
        session.close();

        return customers;
    }

    public static void delete(Session session, Customer customer) {
        logger.debug("Deleting customer [{}]", customer);

        // currently no children to delete

        session.delete(customer);
    }

    public static void delete(Customer customer) {
        logger.debug("Deleting customer [{}]", customer);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        delete(session, customer);

        session.getTransaction().commit();
        session.close();
    }
}
