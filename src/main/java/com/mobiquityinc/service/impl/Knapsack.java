package com.mobiquityinc.service.impl;

import com.mobiquityinc.domain.Item;
import com.mobiquityinc.service.PackerAPI;

import java.util.ArrayList;
import java.util.List;


/**
 *
 *  Since on the task exist some optimisation challenge
 *  we can resolve it with a help of Dynamic Programming(i.e )
 *  more precisely with a help of Pack problem.
 *  This algorithm works when input params is Integer(natural number).
 *  That's why I have to convert the params if we choose this way.
 *
 * */

public class Knapsack implements PackerAPI{

    private static final int constant = 100;

    private static class Solution {
        final List<Item> items;
        final double price;

        Solution(List<Item> items, double price) {
            this.items = items;
            this.price = price;
        }
        
        public double getTotalWeight() {
            double sum = 0;
            for (final Item item : items) {
                sum += item.getWeight();
            }
            return sum;
        }
    }

    @Override
    public List<Item> pack(double capacity, List<Item> items) {

        capacity *= constant;
        int dpCap = (int)capacity;


        items.stream().forEach(item -> item.setWeight(item.getWeight()*constant));


        Solution[][] T = new Solution[items.size()+1][dpCap+1];

        for (int i=1; i<=items.size(); ++i)
            for (int j=0; j<=capacity; ++j) {
                if (items.get(i-1).getWeight() > j) {
                    T[i][j] = T[i-1][j];
                } else {
                    double include = t(T, i-1, j-(int)items.get(i-1).getWeight()) + items.get(i-1).getPrice();
                    double exclude = t(T, i-1, j);
                    double includeWeight = tw(T, i-1, j-(int)items.get(i-1).getWeight()) + items.get(i-1).getWeight();
                    double excludeWeight = tw(T, i-1, j);
                    if (include > exclude || (include == exclude && includeWeight <= excludeWeight)) {
                        Solution prev = T[i-1][j-(int)items.get(i-1).getWeight()];
                        List<Item> prevItemList = prev != null ? prev.items : new ArrayList<>();
                        List<Item> curItemList = new ArrayList<>();
                        curItemList.addAll(prevItemList);
                        curItemList.add(items.get(i-1));
                        T[i][j] = new Solution(curItemList, include);
                    } else {
                        T[i][j] = T[i-1][j];
                    }
                }
            }
        return T[items.size()][dpCap] !=null ? T[items.size()][dpCap].items : null;
    }

    double t(Solution[][] T, int item, int weight) {
        if (T[item][weight] != null) {
            return T[item][weight].price;
        } else {
            return 0;
        }
    }
    
    double tw(Solution[][] T, int item, int weight) {
        if (T[item][weight] != null) {
            return T[item][weight].getTotalWeight();
        } else {
            return 0;
        }
    }
}
