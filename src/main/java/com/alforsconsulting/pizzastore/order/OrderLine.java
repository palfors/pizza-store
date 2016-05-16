package com.alforsconsulting.pizzastore.order;

import com.alforsconsulting.pizzastore.menu.MenuItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by palfors on 5/12/16.
 */
@Component
@Scope("prototype")
public class OrderLine {
    private MenuItem menuItem = null;
    private long orderLineId;
    private long orderId;
    private long menuItemId;
    private int quantity;
    private double price;

    public OrderLine() {
        this.orderLineId = OrderLineIdGenerator.getInstance().generateId();
    }

    public OrderLine(long orderLineId) {
        this.orderLineId = orderLineId;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public long getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(long orderLineId) {
        this.orderLineId = orderLineId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String toString() {
        return new StringBuilder().append("[").append(
                menuItem).append("][").append(quantity).append("]").toString();
    }
}
