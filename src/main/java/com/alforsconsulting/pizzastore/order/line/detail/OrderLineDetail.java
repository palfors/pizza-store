package com.alforsconsulting.pizzastore.order.line.detail;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by palfors on 5/12/16.
 */
@Component
@Scope("prototype")
@Entity
@Table( name = "ORDER_LINE_DETAIL" )
public class OrderLineDetail {

    private long orderLineDetailId;
    private long orderLineId;
    private long menuItemDetailId;
    private String placement;

    public OrderLineDetail() {

    }

    public OrderLineDetail(long orderLineDetailId) {
        this.orderLineDetailId = orderLineDetailId;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
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

    public void generateId() {
        orderLineDetailId = OrderLineDetailIdGenerator.getInstance().generateId();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder().append("OrderLineDetail: ");
        builder.append("[orderLineDetailId: ").append(this.getOrderLineDetailId()).append("]");
        builder.append("[orderLineId: ").append(this.getOrderLineId()).append("]");
        builder.append("[menuItemDetailId: ").append(this.getMenuItemDetailId()).append("]");
        builder.append("[placement: ").append(this.getPlacement()).append("]");

        return builder.toString();
    }
}
