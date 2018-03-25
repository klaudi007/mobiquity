package com.mobiquityinc.service.impl;

import com.mobiquityinc.domain.Item;
import com.mobiquityinc.service.PackerAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 *  With a help of recursion
 *  First I include current item to package and recurse for remaining items. Decreasing package capacity.
 *  If capacity < 0 break recurse and return 0
 *  Second I exclude current item from package and recurse through remaining items.
 *  Finally return maximum value of this action. e.i including, excluding
 * */
public class KnapsackRecursive implements PackerAPI {

    Map<Integer, Item> indexToItem;

    @Override
    public List<Item> pack(double capacity, List<Item> items) {

        List<Integer> solution = new ArrayList<>();
        indexToItem = new HashMap<>();
        for (Item item : items) {
            indexToItem.put(item.getIndex(), item);
        }
        recurse(items, items.size()-1, capacity, solution);

        return solution.stream().map(index -> new Item(index)).collect(Collectors.toList());
    }

    public double recurse(List<Item> item, int size, double weight, List<Integer> solution){

        if(weight < 0 ) {
            return 0;
        }

        if(weight == 0 || size < 0){
            return 0;
        }

        if(item.get(size).getWeight()>weight){
            return recurse(item, size-1, weight, solution);
        }

        int includeSolutionSize = solution.size();

        double include = item.get(size).getPrice()+recurse(item, size-1, weight-item.get(size).getWeight(), solution);
        double includeWeight = weight(solution, includeSolutionSize, solution.size());

        int excludeSolutionSize = solution.size();
        double exclude = recurse(item, size-1, weight, solution);
        double excludeWeight = weight(solution, excludeSolutionSize, solution.size());

        if(include>exclude || (include == exclude && includeWeight < excludeWeight)){
            if(solution.size()>excludeSolutionSize)
                solution.subList(excludeSolutionSize, solution.size()).clear();
            solution.add(item.get(size).getIndex());
            return include;
        }else{
            if(excludeSolutionSize>includeSolutionSize)
                solution.subList(includeSolutionSize, excludeSolutionSize).clear();
            return exclude;
        }
    }
    
    public double weight(List<Integer> solution, int begIndex, int endIndex) {
        double sum = 0;
        for (int idx=begIndex; idx<endIndex; ++idx) {
            int itemIndex = solution.get(idx);
            sum += indexToItem.get(itemIndex).getWeight();
        }
        return sum;
    }
}
