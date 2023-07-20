package com.carvajal.wishlist;

import com.carvajal.commons.properties.Id;
import com.carvajal.commons.properties.State;
import com.carvajal.product.Product;
import com.carvajal.product.ProductData;
import com.carvajal.product.dto.ProductDto;
import com.carvajal.product.dto.properties.IsLike;
import com.carvajal.product.properties.*;
import reactor.core.publisher.Mono;

public class WishListMapper {
    public final Mono<WishListData> toEntityData(WishList wishList) {
        return Mono.just(WishListData.builder()
                .id(wishList.getId().getValue())
                .userId(wishList.getUserId().getValue())
                .productId(wishList.getProductId().getValue())
                .state(wishList.getState().getValue())
                .build());
    }

    public final Mono<WishListData> toNewEntityData(WishList wishList) {
        return Mono.just(WishListData.builder()
                .userId(wishList.getUserId().getValue())
                .productId(wishList.getProductId().getValue())
                .state(wishList.getState().getValue())
                .build());
    }

    public final WishList toDomainDtoModel(WishListData wishListData) {
        return new WishList(
                new Id(wishListData.getId()),
                new Id(wishListData.getUserId()),
                new Id(wishListData.getProductId()),
                new State(wishListData.getState())
        );
    }
}
