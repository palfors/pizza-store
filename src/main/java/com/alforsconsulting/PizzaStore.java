package com.alforsconsulting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by palfors on 5/8/16.
 */
public class PizzaStore<T> {

    private T storeId = null;
    private List<Order> orders = new ArrayList<Order>();

    public PizzaStore(T storeId) {
        this.storeId = storeId;
    }

    public Order createOrder() {
        Order order = new Order(storeId);
        orders.add(order);

        return order;
    }

    public void placeOrder(Order order) {
        order.updateStatus(OrderStatus.PLACED);
    }

    public void showOrders() {
        System.out.println("PizzaStore Orders:");
        for (Order order : orders) {
            System.out.println(order.toString());
        }
    }
}
