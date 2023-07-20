package com.carvajal.product.properties;

import com.carvajal.commons.ValidateData;

public class Price {
    private static String FIELD_NAME = "price";
    private Double value;

    public Price(Double value) {
        if(ValidateData.number(value, FIELD_NAME)){
            this.value = value;
        }
    }

    public Double getValue(){ return value; }
}
