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


    public static Customer create(String name) {
        Customer customer = (Customer) applicationContext.getBean("customer");
        customer.setName(name);

        return save(customer);
    }

    public static Customer save(Customer customer) {
        logger.debug("Saving customer [{}]", customer);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(customer);

        session.getTransaction().commit();
        session.close();

        return customer;
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

    public static boolean delete(Customer customer) {
        logger.debug("Deleting customer [{}]", customer);

        boolean result = true;

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // TODO: check for success
        session.delete(customer);

        session.getTransaction().commit();
        session.close();

        return result;
    }
}
