package com.alforsconsulting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by palfors on 5/7/16.
 */
public class Pizza<T> extends AbstractOrderItem {

    List<Topping> toppings = new ArrayList<Topping>();

    public Pizza(T id) {
        this.setId(id);
    }

    public String getItemType() {
        return "Pizza";
    }

}
