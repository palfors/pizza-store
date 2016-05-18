package com.alforsconsulting.pizzastore.menu;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by palfors on 5/12/16.
 */
public interface MenuItem {

    public long getMenuItemId();
    public void setMenuItemId(long menuItemId);

    /** The spring bean mapping */
    public String getMenuItemType();
    public void setMenuItemType(String menuItemType);

    public String getName();
    public void setName(String name);

    public double getPrice();
    public void setPrice(double price);

    public void generateId();
}
