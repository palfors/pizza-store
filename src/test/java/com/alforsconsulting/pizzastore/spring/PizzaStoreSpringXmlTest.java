package com.alforsconsulting.pizzastore.spring;

import com.alforsconsulting.PizzaStoreTest;
import com.alforsconsulting.pizzastore.PizzaStore;
import com.alforsconsulting.pizzastore.menu.pizza.Pizza;
import com.alforsconsulting.pizzastore.menu.sides.BreadSticks;
import com.alforsconsulting.pizzastore.order.Order;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by palfors on 5/8/16.
 */
public class PizzaStoreSpringXmlTest extends PizzaStoreTest {

    public static void main(String[] args) {

        ApplicationContext context = new FileSystemXmlApplicationContext("src/test/resources/xml-application-context.xml");
        PizzaStore pizzaStore = (PizzaStore) context.getBean("store");

        Order order = (Order) context.getBean("order");
        Pizza pizza1 = (Pizza) context.getBean("pizza");
        pizza1.addTopping("Sausage");
        pizza1.addTopping("Onion");
        order.addItem(pizza1, 2);

        Pizza pizza2 = (Pizza) context.getBean("sausage-pizza");
        order.addItem(pizza2, 2);
        pizzaStore.addOrder(order);

        Order order2 = (Order) context.getBean("order");
        Pizza pizza3 = (Pizza) context.getBean("pepperoni-pizza");
        order2.addItem(pizza3, 2);

        BreadSticks breadsticks = (BreadSticks) context.getBean("breadsticks");
        order2.addItem(breadsticks, 2);
        pizzaStore.addOrder(order2);

        pizzaStore.listOrders();
    }



}
