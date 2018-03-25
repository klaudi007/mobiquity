package com.mobiquityinc.packer;


import com.mobiquityinc.domain.enumeration.PackerType;
import com.mobiquityinc.service.PackerAPI;
import com.mobiquityinc.service.impl.Bitmask;
import com.mobiquityinc.service.impl.Knapsack;
import com.mobiquityinc.service.impl.KnapsackRecursive;

/**
 *  Creational Design Pattern - Factory method.
 * */
public class PackerFactory {

    public PackerAPI getPacker(PackerType packerType){
        switch (packerType){
            case BITMASK: return new Bitmask();
            case KNAPSACK_RECURSIVE: return new KnapsackRecursive();
            case KNAPSACK: return new Knapsack();
            default: return null;
        }
    }

}
