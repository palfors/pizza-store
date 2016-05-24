package com.alforsconsulting.pizzastore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by palfors on 5/15/16.
 */
public class AppContext {
    private ApplicationContext applicationContext = null;
    protected SessionFactory sessionFactory;
    private static final Logger logger = LogManager.getLogger();

    private static AppContext ourInstance = new AppContext();

    public static AppContext getInstance() {
        return ourInstance;
    }

    private AppContext() {
        initialize();
    }

    public ApplicationContext getContext() {
        return this.applicationContext;
    }
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }


    private void initialize() {
        logger.debug("initialize entry");

        // only need to load the spring context once
        applicationContext = new ClassPathXmlApplicationContext("spring/application-context.xml");

        // A SessionFactory is set up once for an application
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
            throw e;
        }

    }

}
