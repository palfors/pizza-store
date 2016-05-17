package com.alforsconsulting.pizzastore.order.line.detail;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by palfors on 5/12/16.
 */
@Component
@Scope("prototype")
public class OrderLineDetail {

    private long orderLineDetailId;
    private long orderLineId;
    private long menuItemDetailId;
    private String placement;

    public OrderLineDetail() {
        orderLineDetailId = OrderLineDetailIdGenerator.getInstance().generateId();
    }

    public OrderLineDetail(long orderLineDetailId) {
        this.orderLineDetailId = orderLineDetailId;
    }

    public long getOrderLineDetailId() {
        return orderLineDetailId;
    }

    public void setOrderLineDetailId(long orderLineDetailId) {
        this.orderLineDetailId = orderLineDetailId;
    }

    public long getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(long orderLineId) {
        this.orderLineId = orderLineId;
    }

    public long getMenuItemDetailId() {
        return menuItemDetailId;
    }

    public void setMenuItemDetailId(long menuItemDetailId) {
        this.menuItemDetailId = menuItemDetailId;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }
}
