package com.carvajal.wishlist.gatewey.in;

import com.carvajal.product.dto.ProductDto;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

import java.util.List;

public interface WishListUseCase {
    Mono<Boolean> addProduct(Long userId, Long productId);
    Mono<Tuple3<List<ProductDto>, List<ProductDto>, List<ProductDto>>> listProducts(Long userId);
    Mono<Boolean> deleteProduct(Long userId, Long productId);
}
