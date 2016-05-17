package com.alforsconsulting.pizzastore.order;

import com.alforsconsulting.pizzastore.AppContext;
import com.alforsconsulting.pizzastore.customer.Customer;
import com.alforsconsulting.pizzastore.menu.MenuItem;
import com.alforsconsulting.pizzastore.order.line.OrderLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by palfors on 5/7/16.
 */
@Component
@Scope("prototype")
public class Order {
    private long storeId;
    private long orderId;
    private OrderStatus status = OrderStatus.NEW;
    private List<OrderLine> orderLines = new ArrayList<OrderLine>();
    private Customer customer = null;
    private double price;

    public Order(long orderId) {
        this.orderId = orderId;
    }

    public Order() {
        this.orderId = OrderIdGenerator.getInstance().generateId();
    }

    public void addLine(OrderLine line) {
        orderLines.add(line);
    }

    public void addItem(MenuItem item, int quantity) {

        OrderLine orderLine = (OrderLine) AppContext.getInstance().getContext().getBean("orderLine");
        orderLine.setOrderId(orderId);
        orderLine.setMenuItemId(item.getMenuItemId());
        orderLine.setQuantity(quantity);
        // TODO: handle the price

        orderLines.add(orderLine);
    }

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

    protected void updateStatus(OrderStatus orderStatus) {
        status = orderStatus;
    }

    public long getOrderId() {
        return this.orderId;
    }

    public long getStoreId() {
        return this.storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public String toString() {
        // loop through the order and list the items
        StringBuilder builder = new StringBuilder("Order: \n");
        builder.append("- orderId: ").append(orderId).append("\n");
        builder.append("- store: ").append(storeId).append("\n").append(orderId).append(" (").append(status).append(")\n");
        builder.append("- price: ").append(getPrice()).append("\n");
        builder.append("- ").append(customer).append("\n");
        for (OrderLine line : orderLines) {
            builder.append("- ").append(line).append("\n");
        }

        return builder.toString();
    }

    public Customer getCustomer() {
        return this.customer;
    }

    @Autowired
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
