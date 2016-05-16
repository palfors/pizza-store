package com.alforsconsulting.pizzastore.jdbc;

import com.alforsconsulting.pizzastore.PizzaStore;
import com.alforsconsulting.pizzastore.customer.Customer;
import com.alforsconsulting.pizzastore.customer.dao.CustomerJDBCTemplate;
import com.alforsconsulting.pizzastore.dao.PizzaStoreJDBCTemplate;
import com.alforsconsulting.pizzastore.order.OrderLine;
import com.alforsconsulting.pizzastore.order.dao.OrderJDBCTemplate;
import com.alforsconsulting.pizzastore.menu.pizza.Pizza;
import com.alforsconsulting.pizzastore.order.Order;
import com.alforsconsulting.pizzastore.order.dao.OrderLineJDBCTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

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
//        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
//
//        // load the pizza store to use
//        PizzaStoreJDBCTemplate pizzaStoreJDBCTemplate =
//                (PizzaStoreJDBCTemplate)context.getBean("pizzaStoreJDBCTemplate");
//        PizzaStore pizzaStore = pizzaStoreJDBCTemplate.getPizzaStore(1);
//        System.out.println("Found pizzaStore: " + pizzaStore);
//
//        // create a customer
//        Customer customer = (Customer) context.getBean("customer");
//        customer.setName("Meghan");
//        // persist the customer
//        CustomerJDBCTemplate customerJDBCTemplate =
//                (CustomerJDBCTemplate)context.getBean("customerJDBCTemplate");
//        customerJDBCTemplate.create(customer);
//
//        // create the menu items to be ordered
//        Pizza pizza1 = (Pizza) context.getBean("pizza");
//        pizza1.addTopping("Sausage");
//
//        // create an order
//        Order order = (Order) context.getBean("order");
//        order.setCustomer(customer);
//        order.addItem(pizza1, 2);
//
//        // persist the order
//        OrderJDBCTemplate orderJDBCTemplate =
//                (OrderJDBCTemplate)context.getBean("orderJDBCTemplate");
//        orderJDBCTemplate.create(order);
//
//        // persist the order lines
//        OrderLineJDBCTemplate orderLineJDBCTemplate =
//                (OrderLineJDBCTemplate)context.getBean("orderLineJDBCTemplate");
//        for (OrderLine line : order.getOrderLines()) {
//            orderLineJDBCTemplate.create(line);
//        }
//
//        // add the order to the store for reporting
//        pizzaStore.addOrder(order);
//
//        // show the orders currently in the store
//        pizzaStore.showOrders();
//
//        // list them
//        List<Order> orders= orderJDBCTemplate.list();
//        System.out.println("Original Order List:");
//        for (Order o : orders) {
//            System.out.println("- " + o.getOrderId());
//        }
//        // save the order
//        System.out.println("Adding order: " + order.getOrderId());
//        orderJDBCTemplate.create(order.getOrderId(), pizzaStore.getStoreId(), customer.getCustomerId());
//        // load it
//        Order o = orderJDBCTemplate.getOrder(order.getOrderId());
//        System.out.println("Pulled order: " + o.toString());
//        // list them
//        orders= orderJDBCTemplate.list();
//        System.out.println("Order List:");
//        for (Order _o : orders) {
//            System.out.println("- " + _o.getOrderId());
//        }
//        // update it
//        orderJDBCTemplate.update(order.getOrderId(), 100, 200);
//        o = orderJDBCTemplate.getOrder(order.getOrderId());
//        System.out.println("Updated order: " + o.toString());
//        // delete it
//        orderJDBCTemplate.delete(order.getOrderId());
//        // list them
//        orders= orderJDBCTemplate.list();
//        System.out.println("Order List:");
//        for (Order _o : orders) {
//            System.out.println("- " + _o.getOrderId());
//        }
    }
}
