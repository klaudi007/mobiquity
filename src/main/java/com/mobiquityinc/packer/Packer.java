package com.mobiquityinc.packer;

import com.mobiquityinc.domain.Item;
import com.mobiquityinc.domain.Pack;
import com.mobiquityinc.domain.enumeration.PackerType;
import com.mobiquityinc.domain.enumeration.Parameter;
import com.mobiquityinc.exception.constraint.Validator;
import com.mobiquityinc.service.PackerAPI;
import com.mobiquityinc.service.util.Parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Packer {

    private static PackerFactory factory = new PackerFactory();

    public static List<String> pack(String path){

        List<String> result = null;

        try {
            List<Pack> packs = Parser.getPackages(path);
            result = Packer.packer(PackerType.KNAPSACK, packs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *  Bonus ;)) with additional feature
     *
     *  depending on constraint (packerType) it will switch
     *  different ways of solving problem
     *  full information is provided on Test Cases.
     *
     * */
    public static List<String> packer(PackerType packerType, List<Pack> payload){

        PackerAPI packerAPI = factory.getPacker(packerType);

        List<String> result = new ArrayList<>();

        for(Pack pack: payload){
            /**
             *  Check params based on Mobitquity Constraints.
             * */
            Validator.validate(Parameter.PACKAGE_WEIGHT, pack.getCapacity());
            Validator.validate(Parameter.ITEMS_COUNT, pack.getItems().size());
            pack.getItems().forEach(e -> {
                Validator.validate(Parameter.ITEM_WEIGHT, e.getWeight());
                Validator.validate(Parameter.ITEM_COST, e.getPrice());
            });


            /**
             *  Or we can simple get filtered items without validating
             *  item weight and cost.
             * */
//            item.stream().filter(e -> e.getWeight() <= 100 && e.getPrice() <=100 ).collect(Collectors.toList());

            List<Item> selectedItems = packerAPI.pack(pack.getCapacity(), pack.getItems());
            String str = "-";
            if(selectedItems != null && !selectedItems.isEmpty()){
                str = selectedItems.stream().map(item -> String.valueOf(item.getIndex())).collect(Collectors.joining(", "));
            }
            result.add(str);
        }
        return result;
    }
}
