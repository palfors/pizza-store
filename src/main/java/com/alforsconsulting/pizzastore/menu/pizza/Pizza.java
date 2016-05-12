package com.alforsconsulting.pizzastore.menu.pizza;

import com.alforsconsulting.pizzastore.menu.MenuItem;
import com.alforsconsulting.pizzastore.menu.MenuItemType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by palfors on 5/7/16.
 */
public class Pizza implements MenuItem {

    private String toppings = null;

    public Pizza(String toppings) {
        this.toppings = toppings;
    }

    public String getItemType() {
        return MenuItemType.PIZZA.name();
    }

    public double getPrice() {
        // base price:  TODO add to price for modifications
        return 5.00;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder().append("Pizza: ");
        builder.append(toppings);
        builder.append(" (").append(getPrice()).append(")");
        return builder.toString();
    }
}
