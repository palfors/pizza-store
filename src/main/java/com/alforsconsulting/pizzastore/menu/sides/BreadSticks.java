package com.alforsconsulting.pizzastore.menu.sides;

import com.alforsconsulting.pizzastore.menu.MenuItem;
import com.alforsconsulting.pizzastore.menu.MenuItemType;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

/**
 * Created by palfors on 5/8/16.
 */
@Component
@Scope("prototype")
public class BreadSticks implements MenuItem {

    public BreadSticks() {
    }

    public String getItemType() {
        return MenuItemType.BREADSTICKS.name();
    }

    public double getPrice() {
        return 2.00;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder().append("BreadSticks (").append(getPrice()).append(")");
        return builder.toString();
    }

}
