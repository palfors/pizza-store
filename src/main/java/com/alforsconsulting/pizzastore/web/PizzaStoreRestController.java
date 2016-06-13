package com.alforsconsulting.pizzastore.web;

import com.alforsconsulting.pizzastore.PizzaStore;
import com.alforsconsulting.pizzastore.StoreUtil;
import com.alforsconsulting.pizzastore.customer.Customer;
import com.alforsconsulting.pizzastore.customer.CustomerUtil;
import com.alforsconsulting.pizzastore.order.Order;
import com.alforsconsulting.pizzastore.order.OrderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

/**
 * Created by palfors on 6/11/16.
 */
@RestController
@RequestMapping("/REST")
public class PizzaStoreRestController {

    private static final Logger logger = LogManager.getLogger();

    @RequestMapping(value = "/getStores", method = RequestMethod.GET)
    public List<PizzaStore> getStores() {
        logger.info("Retrieving stores");

        List<PizzaStore> stores = StoreUtil.getStores();
        return stores;
    }

    @RequestMapping(value = "/getStore/{storeId}", method = RequestMethod.GET)
    public PizzaStore getStore(@PathVariable long storeId) {
        logger.info("Retrieving store [{}]", storeId);

        PizzaStore store = StoreUtil.getStore(storeId);
        return store;
    }

    @RequestMapping(value = "/getOrders", method = RequestMethod.GET)
    public List<Order> getOrders() {
        logger.info("Retrieving orders");

        List<Order> orders = OrderUtil.getOrders();
        return orders;
    }

    @RequestMapping(value = "/getOrder/{orderId}", method = RequestMethod.GET)
    public Order getOrder(@PathVariable long orderId) {
        logger.info("Retrieving order [{}]", orderId);

        Order order = OrderUtil.loadOrder(orderId);
        return order;
    }

    @RequestMapping(value = "/getCustomers", method = RequestMethod.GET)
    public List<Customer> getCustomers() {
        logger.info("Retrieving customers");

        List<Customer> customers = CustomerUtil.getCustomers();
        return customers;
    }

    @RequestMapping(value = "/getCustomer/{customerId}", method = RequestMethod.GET)
    public Customer getCustomer(@PathVariable long customerId) {
        logger.info("Retrieving customer [{}]", customerId);

        Customer customer = CustomerUtil.getCustomer(customerId);
        return customer;
    }

}
