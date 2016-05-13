package com.alforsconsulting;

import com.alforsconsulting.pizzastore.menu.pizza.crust.CrustType;
import com.alforsconsulting.pizzastore.menu.pizza.toppings.*;
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

        Pizza pizza1 = new Pizza(CrustType.PAN);
        pizza1.addTopping("Sausage");
        Topping onion = new Onion();
        onion.setToppingPlacement(ToppingPlacement.LEFTHALF);
        pizza1.addTopping(onion);
        order.addItem(pizza1, 2);

        Pizza pizza2 = new Pizza(CrustType.THIN);
        Topping pepperoni = new Pepperoni();
        pepperoni.setToppingPlacement(ToppingPlacement.RIGHTHALF);
        pizza2.addTopping(pepperoni);
        Topping sausage = new Sausage();
        sausage.setToppingPlacement(ToppingPlacement.LEFTHALF);
        pizza2.addTopping(sausage);
        order.addItem(pizza2, 1);

        pizzaStore.addOrder(order);

        Order order2 = new Order();
        order2.addItem(new Pizza(), 2);
        order2.addItem(new BreadSticks(), 2);
        pizzaStore.addOrder(order2);

        pizzaStore.showOrders();
    }

}
