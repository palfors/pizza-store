package com.alforsconsulting.pizzastore.menu.sides;

import com.alforsconsulting.pizzastore.menu.AbstractMenuItem;
import com.alforsconsulting.pizzastore.menu.MenuItemIdGenerator;
import com.alforsconsulting.pizzastore.menu.MenuItemType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by palfors on 5/8/16.
 */
@Component
@Scope("prototype")
public class BreadSticks extends AbstractMenuItem {
    public BreadSticks() {
        this.setMenuItemId(MenuItemIdGenerator.getInstance().generateId());
    }

    public BreadSticks(long menuItemId) {
        this.setMenuItemId(menuItemId);
    }

    public String getItemType() {
        return MenuItemType.BREADSTICKS.name();
    }

    @Override
    public MenuItemType getMenuItemType() {
        return MenuItemType.BREADSTICKS;
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
