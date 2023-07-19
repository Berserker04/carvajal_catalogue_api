package com.carvajal.product.properties;

import com.carvajal.commons.ValidateData;

public class Image {
    private static String FIELD_NAME = "image";
    private String value;

    public Image(String value) {
        if(ValidateData.string(value, FIELD_NAME)){
            this.value = value;
        }
    }

    public String getValue(){ return value; }
}
