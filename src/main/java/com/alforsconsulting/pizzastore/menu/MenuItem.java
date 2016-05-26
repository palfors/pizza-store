package com.alforsconsulting.pizzastore.menu;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

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

    public Timestamp getCreateDate();
    public void setCreateDate(Timestamp createDate);

    public Timestamp getLastModifiedDate();
    public void setLastModifiedDate(Timestamp lastModifiedDate);

    public void generateId();
}
