package com.alforsconsulting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by palfors on 5/7/16.
 */
public class Order<T> {
    private T storeId = null;
    private String orderId = null;
    private OrderStatus status = OrderStatus.NEW;
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();


    public Order(T storeId) {
        this.storeId = storeId;

        generateOrderId(storeId);
    }

    private void generateOrderId(T storeId) {
        // TODO the order number should be incremented
        StringBuilder builder = new StringBuilder(storeId.toString()).append(".test");
        orderId = builder.toString();
    }

    public void addItem(OrderItem item) {
        orderItems.add(item);
    }

    public void updateStatus(OrderStatus orderStatus) {
        status = orderStatus;
    }

    public String toString() {
        // loop through the order and list the items
        StringBuilder builder = new StringBuilder("Order: ").append(orderId).append(" (").append(status).append(")\n");
        for (OrderItem item : orderItems) {
            builder.append("- ").append(item.toString()).append("\n");
        }

        return builder.toString();
    }

}
