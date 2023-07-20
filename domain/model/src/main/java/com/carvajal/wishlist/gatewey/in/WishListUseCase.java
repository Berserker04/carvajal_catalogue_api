package com.carvajal.wishlist.gatewey.in;

import com.carvajal.product.Product;
import com.carvajal.product.dto.ProductDto;
import com.carvajal.wishlist.WishList;
import com.carvajal.wishlist.dto.WishListDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

public interface WishListUseCase {
    Mono<Boolean> addProduct(Long userId, Long productId);
    Mono<WishListDto> listProducts();
//    Mono<Product> listProductsWithEmptyStock(String filter);
    Mono<Boolean> deleteProduct(Long productId);
}
