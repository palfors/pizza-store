package com.alforsconsulting.pizzastore.menu.pizza;

import com.alforsconsulting.pizzastore.menu.AbstractMenuItem;
import com.alforsconsulting.pizzastore.menu.MenuItem;
import com.alforsconsulting.pizzastore.menu.MenuItemIdGenerator;
import com.alforsconsulting.pizzastore.menu.MenuItemType;
import com.alforsconsulting.pizzastore.menu.pizza.crust.Crust;
import com.alforsconsulting.pizzastore.menu.pizza.crust.CrustType;
import com.alforsconsulting.pizzastore.menu.pizza.toppings.*;
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

    private List<Topping> toppings = new ArrayList<Topping>();
    private CrustType crustType = CrustType.THIN;

    public Pizza() {
        this.setMenuItemId(MenuItemIdGenerator.getInstance().generateId());
    }

    public Pizza(long menuItemId) {
        this.setMenuItemId(menuItemId);
    }

    public Pizza(CrustType crust) {
        this.crustType = crust;
    }

    public CrustType getCrustType() {
        return this.crustType;
    }

    public void setCrustType(CrustType crust) {
        this.crustType = crust;
    }

    public void setTopping(String toppingName) {

        addTopping(toppingName, ToppingPlacement.WHOLE.name());
    }

    public void addTopping(String toppingName) {

        addTopping(toppingName, ToppingPlacement.WHOLE.name());
    }

    /*
    helper method to support Spring XML bean definitions
     */
    public void addTopping(String toppingName, String placement) {
        ToppingPlacement toppingPlacement = null;
        switch (placement.toLowerCase()) {
            case "left":
                toppingPlacement = ToppingPlacement.LEFTHALF;
                break;
            case "right":
                toppingPlacement = ToppingPlacement.RIGHTHALF;
                break;
            case "whole":
                toppingPlacement = ToppingPlacement.WHOLE;
                break;
        }

        Topping topping = null;
        switch (toppingName.toLowerCase()) {
            case "sausage":
                topping = new Sausage();
                break;
            case "pepperoni":
                topping = new Pepperoni();
                break;
            case "onion":
                topping = new Onion();
                break;
            default:
                System.out.println("Unexpected topping! [" + toppingName + "]");
                break;
        }
        topping.setToppingPlacement(toppingPlacement);

        addTopping(topping);
    }

    public void addTopping(Topping topping) {

        toppings.add(topping);
    }

    @Override
    public MenuItemType getMenuItemType() {
        return MenuItemType.PIZZA;
    }

    @Override
    public double getPrice() {
        // base price:
        double price = 5.00;
        // add cost for crust
        price = price + this.crustType.getPrice();
        // add price for toppings
        for (Topping topping : toppings) {
            price = price + topping.getPrice();
        }

        return price;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder().append(
                this.getCrustType().getDisplayName()).append(" ").append("Pizza: ");
        builder.append(toppings);
        builder.append(" (").append(getPrice()).append(")");
        return builder.toString();
    }
}
