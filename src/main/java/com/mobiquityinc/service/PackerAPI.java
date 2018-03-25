package com.mobiquityinc.service;

import com.mobiquityinc.domain.Item;

import java.util.List;

public interface PackerAPI {
    List<Item> pack(double capacity, List<Item> items);
}
