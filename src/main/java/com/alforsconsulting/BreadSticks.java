package com.alforsconsulting;

/**
 * Created by palfors on 5/8/16.
 */
public class BreadSticks<T> extends AbstractOrderItem {

    public BreadSticks(T id) {
        this.setId(id);
    }

    public String getItemType() {
        return "BreadSticks";
    }
}
