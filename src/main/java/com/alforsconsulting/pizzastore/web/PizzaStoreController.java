package com.alforsconsulting.pizzastore.web;

import com.alforsconsulting.pizzastore.PizzaStore;
import com.alforsconsulting.pizzastore.StoreUtil;
import com.alforsconsulting.pizzastore.customer.Customer;
import com.alforsconsulting.pizzastore.customer.CustomerUtil;
import com.alforsconsulting.pizzastore.order.Order;
import com.alforsconsulting.pizzastore.order.OrderUtil;
import com.alforsconsulting.pizzastore.order.line.OrderLine;
import com.alforsconsulting.pizzastore.order.line.OrderLineUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by palfors on 5/28/16.
 */
@Controller
public class PizzaStoreController {

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