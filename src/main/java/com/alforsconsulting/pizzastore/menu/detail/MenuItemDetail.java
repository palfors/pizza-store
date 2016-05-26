package com.alforsconsulting.pizzastore.menu.detail;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by palfors on 5/12/16.
 */
@Component
@Entity
@Table (name = "MENUITEM_DETAIL")
public class MenuItemDetail {

    private long menuItemDetailId;
    private long menuItemId;
    private String detailType;
    private String name;
    private double price;
    private Timestamp createDate;
    private Timestamp lastModifiedDate;

    public MenuItemDetail() {
    }

    public MenuItemDetail(long menuItemDetailId) {
        this.menuItemDetailId = menuItemDetailId;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public long getMenuItemDetailId() {
        return menuItemDetailId;
    }

    public void setMenuItemDetailId(long menuItemDetailId) {
        this.menuItemDetailId = menuItemDetailId;
    }

    public long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
    public String toString() {
        StringBuilder builder = new StringBuilder("MenuItemDetail ")
            .append("[").append(this.getMenuItemDetailId()).append("]")
            .append("[").append(this.getMenuItemId()).append("]")
            .append("[").append(this.getDetailType()).append("]")
            .append("[").append(this.getName()).append("]")
            .append("[").append(this.getPrice()).append("]");
        return builder.toString();
    }


}
