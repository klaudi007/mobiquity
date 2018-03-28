package com.mobiquityinc.exception.constraint;

import com.mobiquityinc.domain.enumeration.Parameter;
import com.mobiquityinc.exception.APIException;

/**
 *
 *  Because of Java SE nature I can't use
 *  @Constraint (JEE) annotation to create
 *  custom Annotation for validation action.
 *  That's why i am using simple solution
 *  for this kind of action
 *
 * */
public class Validator {

    private static final String  PACKAGE_WEIGHT_MSG = "Max weight that a package can take is =< 100";

    private static final String  ITEM_WEIGHT_AND_COST_MSG = "Max weight and cost of Item is =< 100";

    private static final String  ITEMS_COUNT_MSG = "There might be up to 15 items you need to choose from";

    public static void validate(Parameter parameter, double payload){

        switch(parameter){
            case PACKAGE_WEIGHT: if(payload > 100.00) throw new APIException(PACKAGE_WEIGHT_MSG); break;
            case ITEM_WEIGHT:
            case ITEM_COST: if(payload > 100) throw new APIException(ITEM_WEIGHT_AND_COST_MSG); break;
            case ITEMS_COUNT: if(payload > 15) throw new APIException(ITEMS_COUNT_MSG); break;
            default: break;
        }

    }


}
