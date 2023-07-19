package com.carvajal.product.properties;

import com.carvajal.commons.ValidateData;

public class Stock {
    private static String FIELD_NAME = "stock";
    private Integer value;

    public Stock(Integer value) {
        if(ValidateData.number(value, FIELD_NAME)){
            this.value = value;
        }
    }

    public Integer getValue(){ return value; }
}
