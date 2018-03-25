package com.mobiquityinc.service.impl;

import com.mobiquityinc.domain.Item;
import com.mobiquityinc.service.PackerAPI;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *  if we will take into account the second restriction (item count up to 15)
 *  then we can use subset enumeration. In this algorithm will 2^15 subset
 *  e.i. our time complexity is exponential.
 *  But in our example max count is 15 ;))
 *
 * */
public class Bitmask implements PackerAPI {

    @Override
    public List<Item> pack(double capacity, List<Item> items) {

        int N = items.size();

        double answer = 0.0;
        double answer_weight = 0.0;
        int answer_mask = 0;
        

        for (int mask=0; mask<(1<<N); ++mask) { //subset of items (0, 0, 0) (1, 0, 1)
            double current = 0;
            double current_price = 0;
            for (int idx=0; idx<N; ++idx) { //item index
                if ((mask&(1<<idx)) > 0) {
                    current += items.get(idx).getWeight();
                    current_price += items.get(idx).getPrice();
                }
            }
            if (current <= capacity) {
                if (current_price > answer || (current_price == answer && current <= answer_weight)) {
                    answer_mask = mask;
                    answer = current_price;
                    answer_weight = current;
                }
            }
        }

        List<Item> resultItems = new ArrayList<>();
        for (int idx=0; idx<N; ++idx) { //item index
            if ((answer_mask&(1<<idx)) > 0) {
                resultItems.add(items.get(idx));
            }
        }
        return resultItems;
    }
}
