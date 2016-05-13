package com.alforsconsulting.pizzastore.menu.pizza.crust;

/**
 * Created by palfors on 5/12/16.
 */
public enum CrustType {
    THIN ("Thin", 0.00),
    REGULAR ("Regular", 0.50),
    PAN ("Pan", 1.00);

    private String displayName = null;
    private double price = 0.00;

    CrustType(String displayName, double price) {
        this.displayName = displayName;
        this.price = price;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public double getPrice() {
        return this.price;
    }

}
