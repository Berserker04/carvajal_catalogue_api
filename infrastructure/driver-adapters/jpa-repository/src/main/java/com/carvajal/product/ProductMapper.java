package com.carvajal.product;

import com.carvajal.client.properties.Password;
import com.carvajal.client.properties.Role;
import com.carvajal.commons.properties.Id;
import com.carvajal.commons.properties.State;
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

//    public final Mono<ProductData> toUpdateEntityData(Product product) {
//        return Mono.just(ProductData.builder()
//                .id(product.getId().getValue())
//                .password(product.getPassword().getValue())
//                .build());
//    }

    public final Product toDomainModel(ProductData productData) {
        return new Product(
                new Id(productData.getId()),
                new Name(productData.getName()),
                new Slug(productData.getSlug()),
                new Price(productData.getPrice()),
                new Image(productData.getImage()),
                new Stock(productData.getStock()),
                new State(productData.getState()));
    }
}