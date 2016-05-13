package com.alforsconsulting;

import com.alforsconsulting.pizzastore.PizzaStore;
import com.alforsconsulting.pizzastore.menu.pizza.Pizza;
import com.alforsconsulting.pizzastore.menu.sides.BreadSticks;
import com.alforsconsulting.pizzastore.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by palfors on 5/8/16.
 */
public class PizzaStoreSpringComponentTest {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("component-application-context.xml");
        PizzaStore pizzaStore = (PizzaStore) context.getBean("pizzaStore");

        Order order = (Order) context.getBean("order");

        Pizza pizza1 = (Pizza) context.getBean("pizza");
        pizza1.addTopping("Sausage");
        order.addItem(pizza1, 2);
        pizzaStore.addOrder(order);

        Order order2 = (Order) context.getBean("order");
        Pizza pizza2 = (Pizza) context.getBean("pizza");
        pizza2.addTopping("Onion");
        order2.addItem(pizza2, 2);

        Pizza pizza3 = (Pizza) context.getBean("pepperoni-pizza");
        order2.addItem(pizza3, 2);

        BreadSticks breadsticks = (BreadSticks) context.getBean("breadSticks");
        order2.addItem(breadsticks, 2);

        pizzaStore.addOrder(order2);

        pizzaStore.showOrders();
    }

}
