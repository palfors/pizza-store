package com.alforsconsulting.pizzastore;

import com.alforsconsulting.pizzastore.menu.MenuItem;
import com.alforsconsulting.pizzastore.order.Order;
import com.alforsconsulting.pizzastore.order.OrderStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by palfors on 5/8/16.
 */
@Component
public class PizzaStore {

    private long storeId = 0;

    private List<Order> orders = new ArrayList<Order>();
    protected String storeName = "PizzaStore";

    public PizzaStore() {
        storeId = StoreIdGenerator.getInstance().generateId();
    }

    public Order createOrder() {
        Order order = new Order(storeId);
        orders.add(order);

        return order;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public void placeOrder(Order order) {

        order.placeOrder();
    }

    public void cancelOrder(Order order) {
        order.cancelOrder();
    }

    public void showOrders() {
        System.out.println("Store: " + storeId + " Orders:");
        for (Order order : orders) {
            System.out.println(order.toString());
        }
    }

}
