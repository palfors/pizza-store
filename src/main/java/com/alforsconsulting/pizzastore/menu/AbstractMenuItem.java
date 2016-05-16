package com.alforsconsulting.pizzastore.menu;

/**
 * Created by palfors on 5/14/16.
 */
public abstract class AbstractMenuItem implements MenuItem {

    protected long menuItemId;
    protected String name;
    protected double price;

    @Override
    public long getMenuItemId() {
        return this.menuItemId;
    }

    @Override
    public void setMenuItemId(long menuItemId) {
        this.menuItemId = menuItemId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

}
