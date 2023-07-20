package com.carvajal.wishlist.services;

import com.carvajal.product.Product;
import com.carvajal.product.dto.ProductDto;
import com.carvajal.product.gatewey.in.ProductUseCase;
import com.carvajal.wishlist.WishList;
import com.carvajal.wishlist.dto.WishListDto;
import com.carvajal.wishlist.gatewey.in.WishListUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

@Service
@RequiredArgsConstructor
public class WishListService implements WishListUseCase {
    private final WishListUseCase wishListUseCase;
    @Override
    public Mono<Boolean> addProduct(Long userId, Long productId) {
        return wishListUseCase.addProduct(userId, productId);
    }

    @Override
    public Mono<WishListDto> listProducts() {
        return wishListUseCase.listProducts();
    }

    @Override
    public Mono<Boolean> deleteProduct(Long productId) {
        return wishListUseCase.deleteProduct(productId);
    }
}
