package com.alforsconsulting.pizzastore.order;

import com.alforsconsulting.pizzastore.customer.Customer;
import com.alforsconsulting.pizzastore.menu.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
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
    private long storeId = 0;
    private long orderId = 0;
    private OrderStatus status = OrderStatus.NEW;
    private List<OrderLine> orderLines = new ArrayList<OrderLine>();

    private Customer customer = new Customer("joe");

    public Order() {
        this.orderId = OrderIdGenerator.getInstance().generateId();
    }

    public Order(long storeId) {
        this.storeId = storeId;
        this.orderId = OrderIdGenerator.getInstance().generateId();
    }

    public void addItem(MenuItem item, int quantity) {
        orderLines.add(new OrderLine(item, quantity));
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

    public long getStoreId() {
        return this.storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public String toString() {
        // loop through the order and list the items
        StringBuilder builder = new StringBuilder("Order: ").append(
                storeId).append(".").append(orderId).append(" (").append(status).append(")\n");
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
    @Qualifier("moe-customer")
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
