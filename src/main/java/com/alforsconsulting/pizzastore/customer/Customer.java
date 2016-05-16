package com.alforsconsulting.pizzastore.customer;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by palfors on 5/12/16.
 */
@Component
@Scope("prototype")
public class Customer {
    private long customerId;
    private String name = "<unknown>";

    public Customer() {
        this.customerId = CustomerIdGenerator.getInstance().generateId();
    }

    public Customer(long customerId) {
        this.customerId = customerId;
    }

    public Customer (String name) {
        this.name = name;
        this.customerId = CustomerIdGenerator.getInstance().generateId();
    }

    public long getCustomerId() {
        return customerId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("Customer: ").append(getName());
        return builder.toString();
    }

}
