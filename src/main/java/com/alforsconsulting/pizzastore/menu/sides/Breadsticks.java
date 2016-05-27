package com.alforsconsulting.pizzastore.menu.sides;

import com.alforsconsulting.pizzastore.menu.GenericMenuItem;
import com.alforsconsulting.pizzastore.menu.MenuItemType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;

/**
 * Created by palfors on 5/27/16.
 */
@Component
@Scope("prototype")
@Entity
public class Breadsticks extends GenericMenuItem {
    public Breadsticks() {
        // default the menuItemType
        setMenuItemType(MenuItemType.BREADSTICKS.getBeanName());
    }

    public Breadsticks(long menuItemId) {
        this.setMenuItemId(menuItemId);
        // default the menuItemType
        setMenuItemType(MenuItemType.BREADSTICKS.getBeanName());
    }

}
