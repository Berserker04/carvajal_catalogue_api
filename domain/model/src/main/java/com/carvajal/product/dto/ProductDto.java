package com.carvajal.product.dto;

import com.carvajal.commons.properties.Id;
import com.carvajal.commons.properties.State;
import com.carvajal.product.Product;
import com.carvajal.product.dto.properties.IsLike;
import com.carvajal.product.properties.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto extends Product {
    private IsLike isLike;
    public ProductDto(Id id, Name name, Slug slug, Price price, Image image, Stock stock, State state, IsLike isLike) {
        super(id, name, slug, price, image, stock, state);
        this.isLike = isLike;
    }
}
