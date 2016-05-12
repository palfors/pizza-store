package com.alforsconsulting.pizzastore.order;

import com.alforsconsulting.pizzastore.menu.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by palfors on 5/7/16.
 */
public class Order {
    private long storeId = 0;
    private long orderId = 0;
    private OrderStatus status = OrderStatus.NEW;
    private List<OrderLine> orderLines = new ArrayList<OrderLine>();


    public Order(long storeId) {
        this.storeId = storeId;
        this.orderId = OrderIdGenerator.getInstance().generateId();
    }

    public void addItem(MenuItem item, int quantity) {
        orderLines.add(new OrderLine(item, quantity));
    }

    public void removeOrderLine(OrderLine line) {
        orderLines.remove(line);
    }

    public void placeOrder() {
        updateStatus(OrderStatus.PLACED);
    }

    public void cancelOrder() {
        updateStatus(OrderStatus.CANCELLED);
    }

    protected void updateStatus(OrderStatus orderStatus) {
        status = orderStatus;
    }

    public String toString() {
        // loop through the order and list the items
        StringBuilder builder = new StringBuilder("Order: ").append(
                storeId).append(".").append(orderId).append(" (").append(status).append(")\n");
        for (OrderLine line : orderLines) {
            builder.append("- ").append(line).append("\n");
        }

        return builder.toString();
    }

}
