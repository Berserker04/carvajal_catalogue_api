package com.carvajal.product.properties;

import com.carvajal.commons.ValidateData;

public class Name {
    private static String FIELD_NAME = "name";
    private String value;

    public Name(String value) {
        if(ValidateData.string(value, FIELD_NAME)){
            this.value = value;
        }
    }

    public String getValue(){ return value; }
}
