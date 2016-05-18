package com.alforsconsulting.pizzastore;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by palfors on 5/15/16.
 */
public class AppContext {
    ApplicationContext context = null;

    private static AppContext ourInstance = new AppContext();

    public static AppContext getInstance() {
        return ourInstance;
    }

    private AppContext() {
        context = new ClassPathXmlApplicationContext("spring/application-context.xml");
    }

    public ApplicationContext getContext() {
        return this.context;
    }
}
