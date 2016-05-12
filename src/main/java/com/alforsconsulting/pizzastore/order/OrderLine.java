package com.alforsconsulting.pizzastore.order;

import com.alforsconsulting.pizzastore.menu.MenuItem;

/**
 * Created by palfors on 5/12/16.
 */
public class OrderLine {
    private MenuItem menuItem = null;
    private int quantity = 0;

    public OrderLine(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public void updateQuantity(int qty) {
        this.quantity = qty;
    }

    public String toString() {
        return new StringBuilder().append("\t[").append(
                menuItem).append("][").append(quantity).append("]").toString();
    }
}
