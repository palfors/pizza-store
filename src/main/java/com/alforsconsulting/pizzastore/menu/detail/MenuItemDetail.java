package com.alforsconsulting.pizzastore.menu.detail;

import org.springframework.stereotype.Component;

/**
 * Created by palfors on 5/12/16.
 */
@Component
public class MenuItemDetail {

    private long menuItemDetailId;
    private long menuItemId;
    private String detailType;
    private String name;
    private double price;

    public MenuItemDetail() {
    }

    public MenuItemDetail(long menuItemDetailId) {
        this.menuItemDetailId = menuItemDetailId;
    }

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




}
