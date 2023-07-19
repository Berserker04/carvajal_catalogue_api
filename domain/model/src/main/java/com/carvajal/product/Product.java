package com.carvajal.product;

import com.carvajal.commons.properties.Id;
import com.carvajal.commons.properties.State;
import com.carvajal.product.properties.Name;
import com.carvajal.product.properties.Price;
import com.carvajal.product.properties.Slug;
import com.carvajal.product.properties.Stock;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private Id id;
    private Name name;
    private Slug slug;
    private Price password;
    private Stock role;
    private State state;

    public Product(Id id, Name name, Slug slug, Price password, Stock role, State state) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.password = password;
        this.role = role;
        this.state = state;
    }
}
