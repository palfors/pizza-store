package com.alforsconsulting.pizzastore.customer;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Created by palfors on 5/12/16.
 */
@Component
@Scope("prototype")
@Entity
@Table( name = "CUSTOMER" )
public class Customer {

    private long customerId;
    private String name = "<unknown>";

    public Customer() {
    }

    public Customer(long customerId) {
        this.customerId = customerId;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void generateId() {
        this.customerId = CustomerIdGenerator.getInstance().generateId();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("Customer: ").append(getName());
        return builder.toString();
    }

}
