package com.alforsconsulting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by palfors on 5/8/16.
 */
public abstract class AbstractOrderItem<T> implements OrderItem {
    protected T itemId;

    public T getId() {
        return itemId;
    }

    public void setId(T id) {
        itemId = id;
    }

    public String toString() {
        return new StringBuilder(getItemType()).append(": ").append(itemId).toString();
    }

}
