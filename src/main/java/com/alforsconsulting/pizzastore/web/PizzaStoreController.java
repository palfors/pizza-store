package com.alforsconsulting.pizzastore.web;

import com.alforsconsulting.pizzastore.PizzaStore;
import com.alforsconsulting.pizzastore.StoreUtil;
import com.alforsconsulting.pizzastore.customer.Customer;
import com.alforsconsulting.pizzastore.customer.CustomerUtil;
import com.alforsconsulting.pizzastore.menu.MenuItem;
import com.alforsconsulting.pizzastore.menu.MenuItemUtil;
import com.alforsconsulting.pizzastore.menu.detail.MenuItemDetail;
import com.alforsconsulting.pizzastore.menu.detail.MenuItemDetailUtil;
import com.alforsconsulting.pizzastore.menu.pizza.ToppingPlacement;
import com.alforsconsulting.pizzastore.order.Order;
import com.alforsconsulting.pizzastore.order.OrderUtil;
import com.alforsconsulting.pizzastore.order.line.OrderLine;
import com.alforsconsulting.pizzastore.order.line.OrderLineUtil;
import com.alforsconsulting.pizzastore.order.line.detail.OrderLineDetail;
import com.alforsconsulting.pizzastore.order.line.detail.OrderLineDetailUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

/**
 * Created by palfors on 5/28/16.
 */
@RequestMapping("/")
@Controller
public class PizzaStoreController {
    private static final Logger logger = LogManager.getLogger();

    @RequestMapping("/")
    public String home(Model model) {
        logger.info("Going Home");

        List<PizzaStore> stores = StoreUtil.getStores();
        model.addAttribute("stores", stores);

        List<Customer> customers = CustomerUtil.getCustomers();
        model.addAttribute("customers", customers);

        // return the view name
        return "home";
    }

    @RequestMapping("/getStore")
    public String getStore(@RequestParam(value="storeId", required=true) long storeId, Model model) {
        logger.info("Retrieving store [{}]", storeId);

        PizzaStore store = StoreUtil.getStore(storeId);
        model.addAttribute("store", store);

        List<Order> orders = OrderUtil.getStoreOrders(storeId);
        model.addAttribute("orders", orders);

        // return the view name
        return "store";
    }

    @RequestMapping("/createStore")
    public String createStore(Model model) {
        logger.info("Creating store");

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
        logger.info("Deleting store [{}]", storeId);

        StoreUtil.delete(storeId);

        return "redirect:/";
    }

    @RequestMapping("/getCustomer")
    public String getCustomer(@RequestParam(value="customerId", required=true) long customerId, Model model) {
        logger.info("Retrieving customer [{}]", customerId);

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
        logger.info("Creating customer");

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
        logger.info("Deleting customer [{}]", customerId);

        CustomerUtil.delete(customerId);

        return "redirect:/";
    }

    @RequestMapping("/getOrder")
    public String getOrder(@RequestParam(value="orderId", required=true) long orderId, Model model) {
        logger.info("Retrieving order [{}]", orderId);

        Order order = OrderUtil.loadOrder(orderId);
        model.addAttribute("order", order);

        model.addAttribute("orderLines", order.getOrderLines());

        PizzaStore store = StoreUtil.getStore(order.getStoreId());
        model.addAttribute("store", store);

        Customer customer = CustomerUtil.getCustomer(order.getCustomerId());
        model.addAttribute("customer", customer);

        // return the view name
        return "order";
    }

    @RequestMapping("/createOrder")
    public String createOrder(@RequestParam(value="storeId", required=false, defaultValue="-1") long storeId,
                              @RequestParam(value="customerId", required=false, defaultValue="-1") long customerId,
                              Model model) {
        logger.info("Creating order");

        Order order = new Order();

        if (storeId >= 0) {
            order.setStoreId(storeId);
            PizzaStore store = StoreUtil.getStore(storeId);
            model.addAttribute("store", store);
        } else {
            List<PizzaStore> stores = StoreUtil.getStores();
            HashMap<Long, String> storeMap = new HashMap<>();
            for (PizzaStore store : stores) {
                storeMap.put(store.getStoreId(), store.getName());
            }
            model.addAttribute("stores", storeMap);
        }

        if (customerId >= 0) {
            order.setCustomerId(customerId);
            Customer customer = CustomerUtil.getCustomer(customerId);
            model.addAttribute("customer", customer);
        } else {
            List<Customer> customers = CustomerUtil.getCustomers();
            HashMap<Long,String> customerMap = new HashMap<>();
            for (Customer customer : customers) {
                customerMap.put(customer.getCustomerId(), customer.getName());
            }
            model.addAttribute("customers", customerMap);
        }

        model.addAttribute("order", order);

        // show the order page
        return "order";
    }

    @RequestMapping("/saveOrder")
    public String saveOrder(@ModelAttribute("order") Order order_attr,
                               BindingResult result, Model model) {
        logger.info("Saving order_attr [{}]", order_attr);

        Order order = null;
        if (order_attr.getOrderId() >= 0) {
            // updating customer
            order = OrderUtil.getOrder(order_attr.getOrderId());
            // merge changes
            order.setStoreId(order_attr.getStoreId());
            order.setCustomerId(order_attr.getCustomerId());
            order.setPrice(order_attr.getPrice());
            logger.info("Updating existing order [{}]", order);
            OrderUtil.merge(order);
        } else {
            // new order
            order = OrderUtil.newOrder();
            order.setStoreId(order_attr.getStoreId());
            order.setCustomerId(order_attr.getCustomerId());
            order.setPrice(order_attr.getPrice());
            OrderUtil.save(order);
        }

        // reload the order
        return "redirect:/getOrder/?orderId=" + order.getOrderId();
    }

    @RequestMapping("/deleteOrder")
    public String deleteOrder(@RequestParam(value="orderId", required=true) long orderId, Model model) {
        logger.info("Deleting order [{}]", orderId);

        OrderUtil.delete(orderId);

        return "redirect:/";
    }

    @RequestMapping("/getOrderLine")
    public String getOrderLine(@RequestParam(value="orderLineId", required=true) long orderLineId, Model model) {
        logger.info("Retrieving orderLine [{}]", orderLineId);

        OrderLine orderLine = OrderLineUtil.getOrderLine(orderLineId);
        model.addAttribute("orderLine", orderLine);

        List<OrderLineDetail> details = OrderLineDetailUtil.getOrderLineDetails(orderLineId);
        model.addAttribute("orderLineDetails", details);

        MenuItem menuItem = MenuItemUtil.getMenuItem(orderLine.getMenuItemId());
        model.addAttribute("menuItem", menuItem);

        // return the view name
        return "orderLine";
    }

    @RequestMapping("/createOrderLine")
    public String createOrderLine(@RequestParam(value="orderId", required=true) long orderId, Model model) {
        logger.info("Creating orderLine");

        OrderLine orderLine = new OrderLine();
        orderLine.setOrderId(orderId);

        model.addAttribute("orderLine", orderLine);

        List<MenuItem> menuItems = MenuItemUtil.getMenuItems();
        HashMap<Long, String> menuItemMap = new HashMap<>();
        for (MenuItem menuItem : menuItems) {
            menuItemMap.put(menuItem.getMenuItemId(), menuItem.getName());
        }
        model.addAttribute("menuItems", menuItemMap);

        // show the order page
        return "orderLine";
    }

    @RequestMapping("/saveOrderLine")
    public String saveOrderLine(@ModelAttribute("orderLine") OrderLine orderLine_attr,
                            BindingResult result, Model model) {
        logger.info("Saving orderLine_attr [{}]", orderLine_attr);

        OrderLine orderLine = null;
        if (orderLine_attr.getOrderLineId() >= 0) {
            // updating orderLine
            orderLine = OrderLineUtil.getOrderLine(orderLine_attr.getOrderLineId());
            // merge changes
            orderLine.setMenuItemId(orderLine_attr.getMenuItemId());
            orderLine.setQuantity(orderLine_attr.getQuantity());
            orderLine.setPrice(orderLine_attr.getPrice());
            logger.info("Updating existing orderLine [{}]", orderLine);
            OrderLineUtil.merge(orderLine);
        } else {
            // new orderLine
            orderLine = OrderLineUtil.newOrderLine();
            orderLine.setOrderId(orderLine_attr.getOrderId());
            orderLine.setMenuItemId(orderLine_attr.getMenuItemId());
            orderLine.setQuantity(orderLine_attr.getQuantity());
            orderLine.setPrice(orderLine_attr.getPrice());
            OrderLineUtil.save(orderLine);
        }

        // reload the orderLine
        return "redirect:/getOrderLine/?orderLineId=" + orderLine.getOrderLineId();
    }

    @RequestMapping("/deleteOrderLine")
    public String deleteOrderLine(@RequestParam(value="orderLineId", required=true) long orderLineId, Model model) {
        logger.info("Deleting orderLine [{}]", orderLineId);

        OrderLineUtil.delete(orderLineId);

        return "redirect:/";
    }

    @RequestMapping("/getOrderLineDetail")
    public String getOrderLineDetail(@RequestParam(value="orderLineDetailId", required=true) long orderLineDetailId, Model model) {
        logger.info("Retrieving orderLineDetail [{}]", orderLineDetailId);

        OrderLineDetail orderLineDetail = OrderLineDetailUtil.getOrderLineDetail(orderLineDetailId);
        model.addAttribute("orderLineDetail", orderLineDetail);

        MenuItemDetail menuItemDetail = MenuItemDetailUtil.getMenuItemDetail(orderLineDetail.getMenuItemDetailId());
        model.addAttribute("menuItemDetail", menuItemDetail);

        MenuItem menuItem = MenuItemUtil.getMenuItem(menuItemDetail.getMenuItemId());
        model.addAttribute("menuItem", menuItem);

        HashMap<String, String> placementMap = new HashMap<>();
        for (ToppingPlacement toppingPlacement : ToppingPlacement.values()) {
            placementMap.put(toppingPlacement.name(), toppingPlacement.name());
        }
        model.addAttribute("placementOptions", placementMap);

        // return the view name
        return "orderLineDetail";
    }

    @RequestMapping("/createOrderLineDetail")
    public String createOrderLineDetail(@RequestParam(value="orderLineId", required=true) long orderLineId, Model model) {
        logger.info("Creating orderLineDetail");

        OrderLineDetail orderLineDetail = new OrderLineDetail();
        orderLineDetail.setOrderLineId(orderLineId);

        model.addAttribute("orderLineDetail", orderLineDetail);

        List<MenuItemDetail> menuItemDetails = MenuItemDetailUtil.getMenuItemDetails();
        HashMap<Long, String> menuItemDetailMap = new HashMap<>();
        for (MenuItemDetail menuItemDetail : menuItemDetails) {
            menuItemDetailMap.put(menuItemDetail.getMenuItemDetailId(), menuItemDetail.getName());
        }
        model.addAttribute("menuItemDetails", menuItemDetailMap);

        HashMap<String, String> placementMap = new HashMap<>();
        for (ToppingPlacement toppingPlacement : ToppingPlacement.values()) {
            placementMap.put(toppingPlacement.name(), toppingPlacement.name());
        }
        model.addAttribute("placementOptions", placementMap);

        // show the detail page
        return "orderLineDetail";
    }

    @RequestMapping("/saveOrderLineDetail")
    public String saveOrderLineDetail(@ModelAttribute("orderLineDetail") OrderLineDetail orderLineDetail_attr,
                                BindingResult result, Model model) {
        logger.info("Saving orderLineDetail_attr [{}]", orderLineDetail_attr);

        OrderLineDetail orderLineDetail = null;
        if (orderLineDetail_attr.getOrderLineDetailId() >= 0) {
            // updating orderLineDetail
            orderLineDetail = OrderLineDetailUtil.getOrderLineDetail(orderLineDetail_attr.getOrderLineDetailId());
            // merge changes
            orderLineDetail.setPlacement(orderLineDetail_attr.getPlacement());
            orderLineDetail.setPrice(orderLineDetail_attr.getPrice());
            logger.info("Updating existing orderLineDetail [{}]", orderLineDetail);
            OrderLineDetailUtil.merge(orderLineDetail);
        } else {
            // new orderLine
            orderLineDetail = OrderLineDetailUtil.newOrderLineDetail();
            orderLineDetail.setOrderLineId(orderLineDetail_attr.getOrderLineId());
            orderLineDetail.setMenuItemDetailId(orderLineDetail_attr.getMenuItemDetailId());
            orderLineDetail.setPlacement(orderLineDetail_attr.getPlacement());
            orderLineDetail.setPrice(orderLineDetail_attr.getPrice());
            OrderLineDetailUtil.save(orderLineDetail);
        }

        // reload the orderLineDetail
        return "redirect:/getOrderLineDetail/?orderLineDetailId=" + orderLineDetail.getOrderLineDetailId();
    }

    @RequestMapping("/deleteOrderLineDetail")
    public String deleteOrderLineDetail(@RequestParam(value="orderLineDetailId", required=true) long orderLineDetailId, Model model) {
        logger.info("Deleting orderLineDetail [{}]", orderLineDetailId);

        OrderLineDetail orderLineDetail = OrderLineDetailUtil.getOrderLineDetail(orderLineDetailId);

        OrderLineDetailUtil.delete(orderLineDetail);

        return "redirect:/getOrderLine/?orderLineId=" + orderLineDetail.getOrderLineId();
    }

}