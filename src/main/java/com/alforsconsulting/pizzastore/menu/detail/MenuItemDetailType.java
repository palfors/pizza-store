package com.alforsconsulting.pizzastore.menu.detail;

/**
 * Created by palfors on 5/12/16.
 */
public enum MenuItemDetailType {

    CRUST ("crust"),
    TOPPING ("topping");

    private final String beanName;

    MenuItemDetailType(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return this.beanName;
    }

}
