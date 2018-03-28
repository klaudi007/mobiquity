package com.mobiquityinc.packer;

import com.mobiquityinc.domain.Pack;
import com.mobiquityinc.domain.enumeration.PackerType;
import com.mobiquityinc.domain.enumeration.Parameter;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.exception.constraint.Validator;
import com.mobiquityinc.service.util.Parser;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PackerTest {

    private String path = null;
    private List<Pack> packs = null;
    private List<String> expectedResult = null;

    @Before
    public void init(){
        // getting access to ClassLoader one of the main part of JVM (ClassLoader & ClassVerificator)
        ClassLoader loader = getClass().getClassLoader();
        path = new File(loader.getResource("input").getFile()).getAbsolutePath();
        packs = Parser.getPackages(path);
        expectedResult = Arrays.asList(new String[]{"4","-","2, 7","8, 9"});
    }

    /**
     *
     *
     *  Default solution with a help of Dynamic Programming
     *
     * */
    @Test
    public void pack(){
        System.out.println("----< Default(Knapsack) way Start >----");
        List<String> result = Packer.pack(path);
        result.forEach(r ->{
            System.out.println(r);
        });
        System.out.println("----<  End  >----");

        assertEquals(expectedResult, result);
    }

    /**
     *
     *  In task exist 3 type of solution
     *  All cases described in their beginning
     *
     * */
    @Test
    public void bitmaskPacker() {

        System.out.println("----< Bitmask way Start >----");
        List<String> result = Packer.packer(PackerType.BITMASK, packs);
        result.forEach(r ->{
            System.out.println(r);
        });
        System.out.println("----<  End  >----");
        assertEquals(expectedResult, result);
    }

    @Test
    public void knapsackRecursivePacker() {
        System.out.println("----< Recursive way Start >----");
        List<String> result = Packer.packer(PackerType.KNAPSACK_RECURSIVE, packs);
        result.forEach(r ->{
            System.out.println(r);
        });
        System.out.println("----<  End  >----");
        assertEquals(expectedResult, result);
    }

    /**
     *  In case of this conditions method will throw expected exception
     *
     *  1. Max weight that a package can take is ≤ 100
     *  2. There might be up to 15 items you need to choose from
     *  3. Max weight and cost of an item is ≤ 100
     *
     * */
    @Test(expected = APIException.class)
    public void packerExceptionTester(){
        packs.forEach( pack -> {
            pack.setCapacity(200.00);
        });

        packs.forEach(pack -> {
            Validator.validate(Parameter.PACKAGE_WEIGHT, pack.getCapacity());
            Validator.validate(Parameter.ITEMS_COUNT, pack.getItems().size());
            pack.getItems().forEach(item -> {
                Validator.validate(Parameter.ITEM_WEIGHT, item.getWeight());
                Validator.validate(Parameter.ITEM_COST, item.getPrice());
            });
        });

    }


}