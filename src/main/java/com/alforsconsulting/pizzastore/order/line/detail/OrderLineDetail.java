package com.alforsconsulting.pizzastore.order.line.detail;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;
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
@Scope("prototype")
@Entity
@Table( name = "ORDER_LINE_DETAIL" )
public class OrderLineDetail {

    private long orderLineDetailId = -1;
    private long orderLineId = -1;
    private long menuItemDetailId = -1;
    private String placement;
    private double price;
    private Timestamp createDate;
    private Timestamp lastModifiedDate;

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

    public void generateId() {
        orderLineDetailId = OrderLineDetailIdGenerator.getInstance().generateId();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder().append("OrderLineDetail: ")
            .append("[orderLineDetailId: ").append(this.getOrderLineDetailId()).append("]")
            .append("[orderLineId: ").append(this.getOrderLineId()).append("]")
            .append("[menuItemDetailId: ").append(this.getMenuItemDetailId()).append("]")
            .append("[placement: ").append(this.getPlacement()).append("]")
            .append("[price: ").append(this.getPrice()).append("]")
            .append("[createDate: ").append(this.getCreateDate()).append("]")
            .append("[lastModifedDate: ").append(this.getLastModifiedDate()).append("]");

        return builder.toString();
    }
}
