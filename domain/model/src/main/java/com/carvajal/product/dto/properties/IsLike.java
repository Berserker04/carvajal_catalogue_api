package com.carvajal.product.dto.properties;

import com.carvajal.commons.ValidateData;

public class IsLike {
    private boolean value;

    public IsLike(boolean value) {
        this.value = value;
    }

    public boolean getValue(){ return value; }
}
