package com.alforsconsulting.pizzastore.menu;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

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
    protected Timestamp createDate;
    protected Timestamp lastModifiedDate;

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

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public void generateId() {
        this.setMenuItemId(MenuItemIdGenerator.getInstance().generateId());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("MenuItem: ")
                .append("[menuItemId: ").append(this.getMenuItemId()).append("]")
                .append("[menuItemType: ").append(this.getMenuItemType()).append("]")
                .append("[name: ").append(this.getName()).append("]")
                .append("[price: ").append(this.getPrice()).append("]")
                .append("[createDate: ").append(this.getCreateDate()).append("]")
                .append("[lastModifedDate: ").append(this.getLastModifiedDate()).append("]");
        return builder.toString();
    }

}
