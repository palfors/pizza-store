package com.alforsconsulting.pizzastore.order;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.customer.Customer;
import com.alforsconsulting.pizzastore.menu.MenuItem;
import com.alforsconsulting.pizzastore.order.line.OrderLine;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by palfors on 5/7/16.
 */
@Component
@Scope("prototype")
@Entity
@Table( name = "STORE_ORDER" )
public class Order {
    private long storeId = -1;

    private long orderId = -1;
    private String status = OrderStatus.NEW.name();
    private List<OrderLine> orderLines = new ArrayList<OrderLine>();
    private long customerId = -1;
    private double price;
    private Timestamp createDate;
    private Timestamp lastModifiedDate;

    public Order() {
    }

    public Order(long orderId) {
        this.orderId = orderId;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getStoreId() {
        return this.storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        this.orderId = OrderIdGenerator.getInstance().generateId();
    }

    @Transient
    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void removeOrderLine(OrderLine line) {
        orderLines.remove(line);
    }

    public void placeOrder() {
        updateStatus(OrderStatus.PLACED);
    }

    public void cancelOrder() {
        updateStatus(OrderStatus.CANCELLED);
    }

    public void addLine(OrderLine line) {
        orderLines.add(line);
    }

    protected void updateStatus(OrderStatus orderStatus) {
        status = orderStatus.name();
    }

    public String toString() {
        // loop through the order and list the items
        StringBuilder builder = new StringBuilder("Order: ")
            .append("[orderId: ").append(this.getOrderId()).append("]")
            .append("[store: ").append(this.getStoreId()).append("]")
            .append("[customer: ").append(this.getCustomerId()).append("]")
            .append("[status: ").append(this.getStatus()).append("]")
            .append("[price: ").append(this.getPrice()).append("]")
            .append("[createDate: ").append(this.getCreateDate()).append("]")
            .append("[lastModifedDate: ").append(this.getLastModifiedDate()).append("]");

        for (OrderLine line : orderLines) {
            builder.append("\n - ").append(line);
        }

        return builder.toString();
    }

}
