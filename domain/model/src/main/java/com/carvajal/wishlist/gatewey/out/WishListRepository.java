package com.carvajal.wishlist.gatewey.out;

import com.carvajal.product.Product;
import com.carvajal.product.dto.ProductDto;
import com.carvajal.wishlist.WishList;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WishListRepository {
    Mono<WishList> save(WishList wishList);
    Flux<Product> listProducts(String stateFilter);
    Flux<Product> listProductsWithEmptyStock();
    Mono<Boolean> deleteById(Long id);
    Mono<Boolean> deleteAllWithEmptyStock();
}
