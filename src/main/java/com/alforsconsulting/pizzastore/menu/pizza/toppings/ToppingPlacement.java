package com.alforsconsulting.pizzastore.menu.pizza.toppings;

/**
 * Created by palfors on 5/12/16.
 */
public enum ToppingPlacement {
    LEFTHALF ("Left Half"),
    RIGHTHALF ("Right Half"),
    WHOLE ("Whole Pizza");

    private String displayName = null;

    ToppingPlacement(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

}
