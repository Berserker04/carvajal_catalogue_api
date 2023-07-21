package com.carvajal.product.properties;

import com.carvajal.commons.ValidateData;

public class Slug {
    private static String FIELD_NAME = "slug";
    private String value;

    public Slug(String value) {
        if(ValidateData.string(value, FIELD_NAME)){
            this.value = value;
        }
    }

    public String getValue(){ return value; }
}
