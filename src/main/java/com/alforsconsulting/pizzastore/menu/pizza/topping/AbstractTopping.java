package com.alforsconsulting.pizzastore.menu.pizza.topping;

/**
 * Created by palfors on 5/12/16.
 */
public abstract class AbstractTopping implements Topping {
    ToppingPlacement toppingPlacement = ToppingPlacement.WHOLE;

    public abstract double getPrice();

    public ToppingPlacement getToppingPlacement() {
        return toppingPlacement;
    }

    public void setToppingPlacement(ToppingPlacement placement) {
        toppingPlacement = placement;
    }

    public abstract String getName();

    public String toString() {
        StringBuilder builder = new StringBuilder(getName()).append(
                " (").append(getPrice()).append(")");
        return builder.toString();
    }
}
