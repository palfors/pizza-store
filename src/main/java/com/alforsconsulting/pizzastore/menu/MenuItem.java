package com.alforsconsulting.pizzastore.menu;

/**
 * Created by palfors on 5/12/16.
 */
public interface MenuItem {

    public long getMenuItemId();
    public void setMenuItemId(long menuItemId);

    public MenuItemType getMenuItemType();

    public String getName();
    public void setName(String name);

    public double getPrice();
    public void setPrice(double price);

}
