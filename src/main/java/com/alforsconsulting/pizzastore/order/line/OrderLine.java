package com.alforsconsulting.pizzastore.order.line;

import com.alforsconsulting.pizzastore.menu.MenuItem;
import com.alforsconsulting.pizzastore.order.line.detail.OrderLineDetail;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by palfors on 5/12/16.
 */
@Component
@Scope("prototype")
@Entity
@Table( name = "ORDER_LINE" )
public class OrderLine {
    private long orderLineId = -1;
    private long orderId = -1;
    private long menuItemId = -1;
    private int quantity;
    private double price;
    private double subtotal;
    private Timestamp createDate;
    private Timestamp lastModifiedDate;

    private List<OrderLineDetail> orderLineDetails = new ArrayList<>();

    public OrderLine() {
    }

    public OrderLine(long orderLineId) {
        this.orderLineId = orderLineId;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public long getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(long orderLineId) {
        this.orderLineId = orderLineId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Transient
    public double getSubtotal() {
        return subtotal;
    }

    @Transient
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
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

    public void addOrderLineDetail(OrderLineDetail orderLineDetail) {

        orderLineDetails.add(orderLineDetail);
        setSubtotal(calculateSubtotal());
    }

    public void removeOrderLineDetail(OrderLineDetail orderLineDetail) {

        orderLineDetails.remove(orderLineDetail);
        setSubtotal(calculateSubtotal());
    }

    public void addOrderLineDetails(List<OrderLineDetail> details) {
        if (details != null) {
            for (OrderLineDetail detail : details) {
                orderLineDetails.add(detail);
            }
            setSubtotal(calculateSubtotal());
        }
    }

    @Transient
    public List<OrderLineDetail> getOrderLineDetails() {
        return orderLineDetails;
    }

    public double calculateSubtotal() {
        double subtotal = getPrice();
        for (OrderLineDetail detail : getOrderLineDetails()) {
            subtotal = subtotal + detail.getPrice();
        }

        return subtotal * getQuantity();
    }

    public void generateId() {
        this.orderLineId = OrderLineIdGenerator.getInstance().generateId();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder().append("OrderLine: ")
            .append("[orderLineId: ").append(this.getOrderLineId()).append("]")
            .append("[orderId: ").append(this.getOrderId()).append("]")
            .append("[menuItemId: ").append(this.getMenuItemId()).append("]")
            .append("[quantity: ").append(this.getQuantity()).append("]")
            .append("[price: ").append(this.getPrice()).append("]")
            .append("[subtotal: ").append(this.getSubtotal()).append("]")
            .append("[createDate: ").append(this.getCreateDate()).append("]")
            .append("[lastModifedDate: ").append(this.getLastModifiedDate()).append("]");

        for (OrderLineDetail detail : orderLineDetails) {
            builder.append("\n - ").append(detail);
        }

        return builder.toString();
    }
}
