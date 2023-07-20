package com.carvajal.product;

import com.carvajal.commons.properties.Id;
import com.carvajal.commons.properties.State;
import com.carvajal.product.dto.ProductDto;
import com.carvajal.product.dto.properties.IsLike;
import com.carvajal.product.properties.*;
import reactor.core.publisher.Mono;

public class ProductMapper {
    public final Mono<ProductData> toEntityData(Product product) {
        return Mono.just(ProductData.builder()
                .id(product.getId().getValue())
                .name(product.getName().getValue())
                .slug(product.getSlug().getValue())
                .price(product.getPrice().getValue())
                .image(product.getImage().getValue())
                .stock(product.getStock().getValue())
                .state(product.getState().getValue())
                .build());
    }

    public final Mono<ProductData> toNewEntityData(Product product) {
        return Mono.just(ProductData.builder()
                .name(product.getName().getValue())
                .slug(product.getSlug().getValue())
                .price(product.getPrice().getValue())
                .image(product.getImage().getValue())
                .stock(product.getStock().getValue())
                .state(product.getState().getValue())
                .build());
    }

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