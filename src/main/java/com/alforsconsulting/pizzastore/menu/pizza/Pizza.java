package com.alforsconsulting.pizzastore.menu.pizza;

import com.alforsconsulting.pizzastore.menu.AbstractMenuItem;
import com.alforsconsulting.pizzastore.menu.MenuItemIdGenerator;
import com.alforsconsulting.pizzastore.menu.MenuItemType;
import com.alforsconsulting.pizzastore.menu.pizza.crust.CrustType;
import com.alforsconsulting.pizzastore.menu.pizza.topping.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by palfors on 5/7/16.
 */
@Component
@Scope("prototype")
public class Pizza extends AbstractMenuItem {

    public Pizza() {
        this.setMenuItemId(MenuItemIdGenerator.getInstance().generateId());
    }

    public Pizza(long menuItemId) {
        this.setMenuItemId(menuItemId);
    }

    @Override
    public MenuItemType getMenuItemType() {
        return MenuItemType.PIZZA;
    }

    @Override
    public double getPrice() {
        // base price:
        double price = 5.00;

        return price;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder().append("Pizza: ");
        builder.append(" (").append(getPrice()).append(")");
        return builder.toString();
    }
}
