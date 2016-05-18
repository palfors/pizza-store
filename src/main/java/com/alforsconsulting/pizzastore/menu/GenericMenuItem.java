package com.alforsconsulting.pizzastore.menu;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by palfors on 5/14/16.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn (name = "DTYPE")
@Table( name = "MENUITEM" )
public class GenericMenuItem implements MenuItem {

    protected long menuItemId;
    protected String name;
    protected double price;
    protected String menuItemType;

    @Override
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public long getMenuItemId() {
        return this.menuItemId;
    }

    @Override
    public void setMenuItemId(long menuItemId) {
        this.menuItemId = menuItemId;
    }

    /** The spring bean mapping */
    public String getMenuItemType() {
        return this.menuItemType;
    }

    public void setMenuItemType(String menuItemType) {
        this.menuItemType = menuItemType;
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

    public double getPrice() {
        return price;
    }

    @Override
    public void generateId() {
        this.setMenuItemId(MenuItemIdGenerator.getInstance().generateId());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("MenuItem: ")
                .append("[").append(this.getMenuItemId()).append("]")
                .append("[").append(this.getMenuItemType()).append("]")
                .append("[").append(this.getName()).append("]")
                .append("[").append(this.getPrice()).append("]");
        return builder.toString();
    }

}
