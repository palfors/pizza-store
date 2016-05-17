package com.alforsconsulting.pizzastore.order.line;

import com.alforsconsulting.pizzastore.menu.MenuItem;
import com.alforsconsulting.pizzastore.order.line.detail.OrderLineDetail;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by palfors on 5/12/16.
 */
@Component
@Scope("prototype")
public class OrderLine {
    private long orderLineId;
    private long orderId;
    private long menuItemId;
    private int quantity;
    private double price;

    private List<OrderLineDetail> orderLineDetails = new ArrayList<>();

    public OrderLine() {
        this.orderLineId = OrderLineIdGenerator.getInstance().generateId();
    }

    public OrderLine(long orderLineId) {
        this.orderLineId = orderLineId;
    }

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

    public void addOrderLineDetail(OrderLineDetail orderLineDetail) {
        orderLineDetails.add(orderLineDetail);
    }

    public void removeOrderLineDetail(OrderLineDetail orderLineDetail) {
        orderLineDetails.remove(orderLineDetail);
    }

    public List<OrderLineDetail> getOrderLineDetails() {
        return orderLineDetails;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder().append("OrderLine: \n");
        builder.append("- orderLineId: ").append(orderLineId).append("\n");
        builder.append("- orderId: ").append(orderId).append("\n");
        builder.append("- menuItemId: ").append(menuItemId).append("\n");
        builder.append("- quantity: ").append(quantity).append("\n");
        builder.append("- price: ").append(price);

        return builder.toString();
    }
}
