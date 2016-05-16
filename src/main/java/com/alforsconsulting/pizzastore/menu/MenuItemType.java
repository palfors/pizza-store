package com.alforsconsulting.pizzastore.menu;

/**
 * Created by palfors on 5/12/16.
 */
public enum MenuItemType {

    PIZZA ("pizza"),
    BREADSTICKS ("breadSticks");

    private final String beanName;

    MenuItemType(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return this.beanName;
    }

}
