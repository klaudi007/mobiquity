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

        List<String> result;

        List<Pack> packs = Parser.getPackages(path);

        /**
         *  Check params based on Mobitquity Constraints. (semantic check)
         * */
        packs.forEach(pack -> {

            Validator.validate(Parameter.PACKAGE_WEIGHT, pack.getCapacity());
            Validator.validate(Parameter.ITEMS_COUNT, pack.getItems().size());

            pack.getItems().forEach(item -> {
                Validator.validate(Parameter.ITEM_WEIGHT, item.getWeight());
                Validator.validate(Parameter.ITEM_COST, item.getPrice());
            });
        });

        result = Packer.packer(PackerType.KNAPSACK, packs);

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
