package com.alforsconsulting.pizzastore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;

/**
 * Created by palfors on 5/18/16.
 */
public abstract class AbstractHibernateTest {
    protected static final Logger logger = LogManager.getLogger();
    protected static SessionFactory sessionFactory;
    protected static ApplicationContext applicationContext;

    public static void setUpClass() throws Exception {
        logger.debug("setUpClass entry");

        applicationContext = AppContext.getInstance().getContext();

        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate/hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            logger.debug("sessionFactory failed with exception [{}][{}]",e.getClass().getName(), e.getMessage());
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    public static void tearDownClass() throws Exception {
        logger.debug("tearDownClass entry");
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

}
