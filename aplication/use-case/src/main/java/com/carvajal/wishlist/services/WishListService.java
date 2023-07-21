package com.carvajal.wishlist.services;

import com.carvajal.product.dto.ProductDto;
import com.carvajal.wishlist.gatewey.in.WishListUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListService implements WishListUseCase {
    private final WishListUseCase wishListUseCase;

    @Override
    public Mono<Boolean> addProduct(Long userId, Long productId) {
        return wishListUseCase.addProduct(userId, productId);
    }

    @Override
    public Mono<Tuple3<List<ProductDto>, List<ProductDto>, List<ProductDto>>> listProducts(Long userId) {
        return wishListUseCase.listProducts(userId);
    }

    @Override
    public Mono<Boolean> deleteProduct(Long userId, Long productId) {
        return wishListUseCase.deleteProduct(userId, productId);
    }
}
