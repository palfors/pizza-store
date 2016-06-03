package com.alforsconsulting.pizzastore.web;

import com.alforsconsulting.pizzastore.PizzaStore;
import com.alforsconsulting.pizzastore.StoreUtil;
import com.alforsconsulting.pizzastore.customer.Customer;
import com.alforsconsulting.pizzastore.customer.CustomerUtil;
import com.alforsconsulting.pizzastore.order.Order;
import com.alforsconsulting.pizzastore.order.OrderUtil;
import com.alforsconsulting.pizzastore.order.line.OrderLine;
import com.alforsconsulting.pizzastore.order.line.OrderLineUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by palfors on 5/28/16.
 */
@Controller
public class PizzaStoreController {
    private static final Logger logger = LogManager.getLogger();

    @RequestMapping("/")
    public String home(Model model) {

        List<PizzaStore> stores = StoreUtil.getStores();
        model.addAttribute("stores", stores);

        List<Customer> customers = CustomerUtil.getCustomers();
        model.addAttribute("customers", customers);

        // return the view name
        return "home";
    }

    @RequestMapping("/getStore")
    public String getStore(@RequestParam(value="storeId", required=true) long storeId, Model model) {

        PizzaStore store = StoreUtil.getStore(storeId);
        model.addAttribute("store", store);

        List<Order> orders = OrderUtil.getStoreOrders(storeId);
        model.addAttribute("orders", orders);

        // return the view name
        return "store";
    }

    @RequestMapping("/createStore")
    public String createStore(Model model) {

        model.addAttribute("store", new PizzaStore());

        // show the store page
        return "store";
    }

    @RequestMapping("/saveStore")
    public String saveStore(@ModelAttribute("store") PizzaStore store_attr,
                               BindingResult result, Model model) {
        logger.info("Saving store_attr [{}]", store_attr);

        PizzaStore store = null;
        if (store_attr.getStoreId() >= 0) {
            // updating store
            store = StoreUtil.getStore(store_attr.getStoreId());
            // merge changes
            store.setName(store_attr.getName());
            logger.info("Updating existing store [{}]", store);
            StoreUtil.merge(store);
        } else {
            // new store
            store = StoreUtil.newPizzaStore();
            store.setName(store_attr.getName());
            StoreUtil.save(store);
        }

        // reload the customer
        return "redirect:/getStore/?storeId=" + store.getStoreId();
    }

    @RequestMapping("/deleteStore")
    public String deleteStore(@RequestParam(value="storeId", required=true) long storeId, Model model) {

        StoreUtil.delete(storeId);

        return "redirect:/";
    }

    @RequestMapping("/getCustomer")
    public String getCustomer(@RequestParam(value="customerId", required=true) long customerId, Model model) {
        model.addAttribute("customerId", customerId);

        Customer customer = CustomerUtil.getCustomer(customerId);
        model.addAttribute("customer", customer);

        List<Order> orders = OrderUtil.getCustomerOrders(customerId);
        model.addAttribute("orders", orders);

        // return the view name
        return "customer";
    }

    @RequestMapping("/createCustomer")
    public String createCustomer(Model model) {

        model.addAttribute("customer", new Customer());

        // show the customer page
        return "customer";
    }

    @RequestMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute("customer") Customer customer_attr,
                               BindingResult result, Model model) {
        logger.info("Saving customer_attr [{}]", customer_attr);

        Customer customer = null;
        if (customer_attr.getCustomerId() >= 0) {
            // updating customer
            customer = CustomerUtil.getCustomer(customer_attr.getCustomerId());
            // merge changes
            customer.setName(customer_attr.getName());
            logger.info("Updating existing customer [{}]", customer);
            CustomerUtil.merge(customer);
        } else {
            // new customer
            customer = CustomerUtil.newCustomer();
            customer.setName(customer_attr.getName());
            CustomerUtil.save(customer);
        }

        // reload the customer
        return "redirect:/getCustomer/?customerId=" + customer.getCustomerId();
    }

    @RequestMapping("/deleteCustomer")
    public String deleteCustomer(@RequestParam(value="customerId", required=true) long customerId, Model model) {

        CustomerUtil.delete(customerId);

        return "redirect:/";
    }

    @RequestMapping("/getOrder")
    public String getOrder(@RequestParam(value="orderId", required=true) long orderId, Model model) {

        Order order = OrderUtil.getOrder(orderId);
        model.addAttribute("order", order);

        List<OrderLine> lines = OrderLineUtil.getOrderLines(orderId);
        model.addAttribute("orderLines", lines);

        // return the view name
        return "order";
    }

}