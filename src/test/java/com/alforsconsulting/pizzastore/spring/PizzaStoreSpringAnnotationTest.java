package com.alforsconsulting.pizzastore.spring;

import com.alforsconsulting.pizzastore.PizzaStore;
import com.alforsconsulting.pizzastore.customer.Customer;
import com.alforsconsulting.pizzastore.menu.pizza.Pizza;
import com.alforsconsulting.pizzastore.menu.sides.BreadSticks;
import com.alforsconsulting.pizzastore.order.Order;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by palfors on 5/8/16.
 */
public class PizzaStoreSpringAnnotationTest {

    public static void main(String[] args) {

        PizzaStoreSpringAnnotationTest testClass = new PizzaStoreSpringAnnotationTest();
        testClass.run();
    }

    public void run() {
        ApplicationContext context = new FileSystemXmlApplicationContext("src/test/resources/annotation-application-context.xml");
//        ApplicationContext context = new ClassPathXmlApplicationContext("src/test/resources/annotation-application-context.xml");
//        ApplicationContext context = new AnnotationConfigApplicationContext("com.alforsconsulting");
        PizzaStore pizzaStore = (PizzaStore) context.getBean("pizzaStore");

        Order order = (Order) context.getBean("order");
        Customer customer = order.getCustomer();

        Pizza pizza1 = (Pizza) context.getBean("pizza");
        pizza1.addTopping("Sausage");
        order.addItem(pizza1, 2);

        Pizza pizza2 = (Pizza) context.getBean("pizza");
        pizza2.addTopping("Onion");
        order.addItem(pizza2, 2);

        pizzaStore.addOrder(order);

        Order order2 = (Order) context.getBean("order");
        Pizza pizza3 = (Pizza) context.getBean("pepperoni-pizza");
        order2.addItem(pizza3, 2);

        BreadSticks breadsticks = (BreadSticks) context.getBean("breadSticks");
        order2.addItem(breadsticks, 2);

        pizzaStore.addOrder(order2);

        pizzaStore.showOrders();
    }

}
