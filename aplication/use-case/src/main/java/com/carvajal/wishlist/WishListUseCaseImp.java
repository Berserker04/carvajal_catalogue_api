package com.carvajal.wishlist;

import com.carvajal.commons.properties.Id;
import com.carvajal.commons.properties.State;
import com.carvajal.product.Product;
import com.carvajal.product.dto.ProductDto;
import com.carvajal.wishlist.dto.WishListDto;
import com.carvajal.wishlist.gatewey.in.WishListUseCase;
import com.carvajal.wishlist.gatewey.out.WishListRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

;import java.util.List;

@RequiredArgsConstructor
public class WishListUseCaseImp implements WishListUseCase {
    private final WishListRepository wishListRepository;

    private static final String stateActive = "active";
    private static final String stateRemove = "removed";

    @Override
    public Mono<Boolean> addProduct(Long userId, Long productId) {
        WishList wishList = new WishList(null, new Id(userId), new Id(productId), new State(stateActive));
        return wishListRepository.save(wishList)
                .flatMap(wl -> {
                    if (wl == null) return Mono.just(false);
                    return Mono.just(true);
                });
    }

    @Override
    public Mono<WishListDto> listProducts(Long userId) {
        Mono<List<ProductDto>> productsActiveMono = wishListRepository.listProducts(userId, stateActive)
                .collectList();
        Mono<List<ProductDto>> productsRemovedMono = wishListRepository.listProducts(userId, stateRemove)
                .collectList();
        Mono<List<ProductDto>> productsWithEmptyStockMono = wishListRepository.listProductsWithEmptyStock(userId)
                .collectList();

        return Mono.zip(productsActiveMono, productsRemovedMono, productsWithEmptyStockMono)
                .map(tuple -> {
                    List<ProductDto> productsActiveList = tuple.getT1();
                    List<ProductDto> productsRemovedList = tuple.getT2();
                    List<ProductDto> productsWithEmptyStockList = tuple.getT3();
                    if (!productsWithEmptyStockList.isEmpty()) {
                        wishListRepository.deleteAllWithEmptyStock(userId).subscribe();
                    }
                    return new WishListDto(productsActiveList, productsRemovedList, productsWithEmptyStockList);
                });
    }

    @Override
    public Mono<Boolean> deleteProduct(Long productId) {
        return wishListRepository.deleteById(productId);
    }
}
