package com.alforsconsulting.pizzastore.menu.sides;

import com.alforsconsulting.pizzastore.menu.MenuItem;
import com.alforsconsulting.pizzastore.menu.MenuItemType;

/**
 * Created by palfors on 5/8/16.
 */
public class BreadSticks implements MenuItem {

    public BreadSticks() {
    }

    public String getItemType() {
        return MenuItemType.BREADSTICKS.name();
    }

    public double getPrice() {
        return 2.00;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder().append("BreadSticks (").append(getPrice()).append(")");
        return builder.toString();
    }

}
