package com.mobiquityinc.domain;

import java.util.List;

/**
 *  Composition
 * */
public class Pack {

    private Double capacity;
    private List<Item> items;

    public Pack(){}

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
