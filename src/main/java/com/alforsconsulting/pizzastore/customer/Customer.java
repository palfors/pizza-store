package com.alforsconsulting.pizzastore.customer;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by palfors on 5/12/16.
 */
@Component
@Scope("prototype")
@Entity
@Table( name = "CUSTOMER" )
public class Customer {

    private long customerId = -1;
    private String name;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp lastModifiedDate;

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
        this.customerId = CustomerIdGenerator.getInstance().generateId();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("Customer: ")
            .append("[customerId: ").append(this.getCustomerId()).append("]")
            .append("[name: ").append(this.getName()).append("]")
            .append("[createDate: ").append(this.getCreateDate()).append("]")
            .append("[lastModifedDate: ").append(this.getLastModifiedDate()).append("]");
        return builder.toString();
    }

}
