package com.carvajal.product;

import com.carvajal.commons.properties.Id;
import com.carvajal.commons.properties.State;
import com.carvajal.product.properties.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private Id id;
    private Name name;
    private Slug slug;
    private Price price;
    private Image image;
    private Stock stock;
    private State state;
    private IsLike isLike;

    public Product(Id id, Name name, Slug slug, Price price, Image image, Stock stock, State state, IsLike isLike) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.price = price;
        this.image = image;
        this.stock = stock;
        this.state = state;
        this.isLike = isLike;
    }
}
