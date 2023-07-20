package com.carvajal.shared.mappers;

import com.carvajal.commons.properties.Id;
import com.carvajal.commons.properties.State;
import com.carvajal.product.ProductData;
import com.carvajal.product.dto.ProductDto;
import com.carvajal.product.dto.properties.IsLike;
import com.carvajal.product.properties.*;

public class ProductMapperShared {
    public final ProductDto toDomainDtoModel(ProductData productData) {
        return new ProductDto(
                new Id(productData.getId()),
                new Name(productData.getName()),
                new Slug(productData.getSlug()),
                new Price(productData.getPrice()),
                new Image(productData.getImage()),
                new Stock(productData.getStock()),
                new State(productData.getState()),
                new IsLike(productData.getIsLike())
        );
    }
}
