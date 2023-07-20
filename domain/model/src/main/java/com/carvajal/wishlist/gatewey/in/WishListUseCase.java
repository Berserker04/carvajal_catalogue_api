package com.carvajal.wishlist.gatewey.in;

import com.carvajal.product.Product;
import com.carvajal.product.dto.ProductDto;
import com.carvajal.wishlist.WishList;
import com.carvajal.wishlist.dto.WishListDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

import java.util.List;

public interface WishListUseCase {
    Mono<Boolean> addProduct(Long userId, Long productId);
    Mono<Tuple3<List<ProductDto>, List<ProductDto>, List<ProductDto>>> listProducts(Long userId);
//    Mono<Product> listProductsWithEmptyStock(String filter);
    Mono<Boolean> deleteProduct(Long userId, Long productId);
}
