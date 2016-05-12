package com.alforsconsulting.pizzastore.customer;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by palfors on 5/12/16.
 */
@Component
@Scope("prototype")
public class Customer {
    private String name = "bob";

    public Customer (String name) {
        this.name = name;
    }

    public Customer() {

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
