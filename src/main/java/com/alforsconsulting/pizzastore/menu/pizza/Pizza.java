package com.alforsconsulting.pizzastore.menu.pizza;

import com.alforsconsulting.pizzastore.menu.GenericMenuItem;
import com.alforsconsulting.pizzastore.menu.MenuItemIdGenerator;
import com.alforsconsulting.pizzastore.menu.MenuItemType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;

/**
 * Created by palfors on 5/7/16.
 */
@Component
@Scope("prototype")
@Entity
public class Pizza extends GenericMenuItem {

    public Pizza() {
        // default the menuItemType
        setMenuItemType(MenuItemType.PIZZA.getBeanName());
    }

    public Pizza(long menuItemId) {
        this.setMenuItemId(menuItemId);
        // default the menuItemType
        setMenuItemType(MenuItemType.PIZZA.getBeanName());
    }

}
