package com.alforsconsulting.pizzastore;

import com.alforsconsulting.pizzastore.order.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by palfors on 5/8/16.
 */
@Component
@Scope("prototype")
@Entity
@Table( name = "STORE" )
public class PizzaStore {
    private static final Logger logger = LogManager.getLogger();

    private long storeId = -1;
    private List<Order> orders = new ArrayList<Order>();
    protected String name;
    private Timestamp createDate;
    private Timestamp lastModifiedDate;

    public PizzaStore() {
    }

    public PizzaStore(long storeId) {
        this.storeId = storeId;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
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

    @Transient
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        order.placeOrder();
        orders.add(order);
    }

    public void listOrders() {
        logger.debug("Listing PizzaStore orders");
        for (Order order : orders) {
            logger.debug("[{}]", order);
        }
    }

    public void generateId() {
        this.storeId = StoreIdGenerator.getInstance().generateId();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("PizzaStore: ")
            .append("[id: ").append(this.getStoreId()).append("]")
            .append("[name: ").append(this.getName()).append("]")
            .append("[createDate: ").append(this.getCreateDate()).append("]")
            .append("[lastModifedDate: ").append(this.getLastModifiedDate()).append("]");
        return builder.toString();
    }

}
