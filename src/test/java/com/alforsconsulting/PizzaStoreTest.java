package com.alforsconsulting;

import com.alforsconsulting.pizzastore.menu.sides.BreadSticks;
import com.alforsconsulting.pizzastore.order.Order;
import com.alforsconsulting.pizzastore.menu.pizza.Pizza;
import com.alforsconsulting.pizzastore.PizzaStore;

/**
 * Created by palfors on 5/8/16.
 */
public class PizzaStoreTest {

    public static void main(String[] args) {
        PizzaStore pizzaStore = new PizzaStore();

        mainImpl(pizzaStore);
    }

    protected static void mainImpl(PizzaStore pizzaStore) {
        Order order = new Order();
        order.addItem(new Pizza("Sausage"), 2);
        order.addItem(new Pizza("Sausage,Onion"), 1);
        pizzaStore.addOrder(order);

        Order order2 = new Order();
        order2.addItem(new Pizza("Pepperoni"), 2);
        order2.addItem(new BreadSticks(), 2);
        pizzaStore.addOrder(order2);

        pizzaStore.showOrders();
    }

}
