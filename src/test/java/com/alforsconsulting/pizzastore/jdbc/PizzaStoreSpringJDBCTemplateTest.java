package com.alforsconsulting.pizzastore.jdbc;

import com.alforsconsulting.pizzastore.PizzaStore;
import com.alforsconsulting.pizzastore.customer.Customer;
import com.alforsconsulting.pizzastore.dao.OrderJDBCTemplate;
import com.alforsconsulting.pizzastore.menu.pizza.Pizza;
import com.alforsconsulting.pizzastore.order.Order;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by palfors on 5/8/16.
 */
public class PizzaStoreSpringJDBCTemplateTest {

    public static void main(String[] args) {

        PizzaStoreSpringJDBCTemplateTest testClass = new PizzaStoreSpringJDBCTemplateTest();
        testClass.run();
    }

    public void run() {
        ApplicationContext context = new ClassPathXmlApplicationContext("annotation-application-context.xml");
//        ApplicationContext context = new AnnotationConfigApplicationContext("com.alforsconsulting");

        // get the Order JDBC Template
        OrderJDBCTemplate orderJDBCTemplate =
                (OrderJDBCTemplate)context.getBean("orderJDBCTemplate");

        PizzaStore pizzaStore = (PizzaStore) context.getBean("pizzaStore");

        Order order = (Order) context.getBean("order");
        Customer customer = order.getCustomer();

        Pizza pizza1 = (Pizza) context.getBean("pizza");
        pizza1.addTopping("Sausage");
        order.addItem(pizza1, 2);

        pizzaStore.addOrder(order);

        pizzaStore.showOrders();

        // list them
        List<Order> orders= orderJDBCTemplate.listOrders();
        System.out.println("Original Order List:");
        for (Order o : orders) {
            System.out.println("- " + o.getOrderId());
        }
        // save it
        System.out.println("Adding order: " + order.getOrderId());
        orderJDBCTemplate.create(order.getOrderId(), pizzaStore.getStoreId(), customer.getCustomerId());
        // load it
        Order o = orderJDBCTemplate.getOrder(order.getOrderId());
        System.out.println("Pulled order: " + o.toString());
        // list them
        orders= orderJDBCTemplate.listOrders();
        System.out.println("Order List:");
        for (Order _o : orders) {
            System.out.println("- " + _o.getOrderId());
        }
        // update it
        orderJDBCTemplate.update(order.getOrderId(), 100, 200);
        o = orderJDBCTemplate.getOrder(order.getOrderId());
        System.out.println("Updated order: " + o.toString());
        // delete it
        orderJDBCTemplate.delete(order.getOrderId());
        // list them
        orders= orderJDBCTemplate.listOrders();
        System.out.println("Order List:");
        for (Order _o : orders) {
            System.out.println("- " + _o.getOrderId());
        }
    }
}
