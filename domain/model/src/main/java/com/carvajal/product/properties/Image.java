package com.carvajal.product.properties;
public class Image {
    private static String FIELD_NAME = "image";
    private String value;

    public Image(String value) {
        // Can be null
        this.value = value;
    }

    public String getValue(){ return value; }
}
