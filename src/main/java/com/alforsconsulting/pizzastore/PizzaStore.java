package com.alforsconsulting.pizzastore;

import com.alforsconsulting.pizzastore.order.Order;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by palfors on 5/8/16.
 */
@Component
@Scope("prototype")
public class PizzaStore {

    private long storeId = 0;

    private List<Order> orders = new ArrayList<Order>();
    protected String name = "PizzaStore";

    public PizzaStore() {
        storeId = StoreIdGenerator.getInstance().generateId();
    }
    public PizzaStore(long storeId) {
        this.storeId = storeId;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        order.placeOrder();
        orders.add(order);
    }

    public void showOrders() {
        System.out.println("Store: " + storeId + " Orders:");
        for (Order order : orders) {
            System.out.println(order.toString());
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("PizzaStore: \n");
        builder.append("- id [").append(this.storeId).append("]\n");
        builder.append("- name [").append(this.name).append("]");
        return builder.toString();
    }

}
