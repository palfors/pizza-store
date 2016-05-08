package com.alforsconsulting;

/**
 * Created by palfors on 5/8/16.
 */
public class PizzaStoreTest {

    public static void main(String[] args) {

        PizzaStore<String> pizzaStore = new PizzaStore<String>("myTestStore");

        Order order = pizzaStore.createOrder();
        order.addItem(new Pizza<String>("Sausage"));
        order.addItem(new Pizza<String>("Sausage,Onion"));
        pizzaStore.placeOrder(order);

        Order order2 = pizzaStore.createOrder();
        order2.addItem(new Pizza<String>("Pepperoni"));
        order2.addItem(new BreadSticks<Integer>(new Integer(3)));

        pizzaStore.showOrders();
    }

}
