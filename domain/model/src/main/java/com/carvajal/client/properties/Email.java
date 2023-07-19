package com.carvajal.client.properties;

import com.carvajal.commons.ValidateData;

public class Email {
    private static String FIELD_NAME = "email";
    private Long value;

    public Email(Long value) {
        if(ValidateData.number(value, FIELD_NAME)){
            this.value = value;
        }
    }

    public Email(String value) {
        if(ValidateData.string(value, FIELD_NAME)){
            this.value = Long.valueOf(value);
        }
    }

    public Long getValue(){ return value; }
}
