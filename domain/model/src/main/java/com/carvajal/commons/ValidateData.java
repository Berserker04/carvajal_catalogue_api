package com.carvajal.commons;

import java.time.LocalDate;

public class ValidateData {
    public static boolean string(String value, String fieldName) {
        if (value == null || value.isEmpty()) {
            createException(fieldName);
        }
        return true;
    }

    public static boolean number(Integer value, String fieldName) {
        if (value == null) {
            createException(fieldName);
        }
        return true;
    }

    public static boolean number(Long value, String fieldName) {
        if (value == null) {
            createException(fieldName);
        }
        return true;
    }

    public static boolean number(Double value, String fieldName) {
        if (value == null) {
            createException(fieldName);
        }
        return true;
    }

    public static boolean date(LocalDate value, String fieldName) {
        if (value == null || value.equals("")) {
            createException(fieldName);
        }
        if(value.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("La fecha " + value + " no puede ser mayor a la fecha actual");
        }
        return true;
    }

    private static String createException(String fieldName){
        throw new IllegalArgumentException("El campo " + fieldName + " no puede estar vacío");
    }
}
