package com.carvajal.wishlist;

import com.carvajal.commons.Constant;
import com.carvajal.commons.properties.Id;
import com.carvajal.commons.properties.State;
import com.carvajal.product.dto.ProductDto;
import com.carvajal.wishlist.gatewey.in.WishListUseCase;
import com.carvajal.wishlist.gatewey.out.WishListRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

import java.util.List;

@RequiredArgsConstructor
public class WishListUseCaseImp implements WishListUseCase {
    private final WishListRepository wishListRepository;

    @Override
    public Mono<Boolean> addProduct(Long userId, Long productId) {
        return wishListRepository.listProductsById(userId, productId)
                .collectList()
                .flatMap(list -> {
                    if (list.isEmpty()) {
                        WishList wishList = new WishList(null, new Id(userId), new Id(productId), new State(Constant.STATE_ACTIVE));
                        return wishListRepository.save(wishList)
                                .flatMap(wl -> {
                                    if (wl == null) return Mono.just(false);
                                    return Mono.just(true);
                                });
                    } else {
                        // La lista no está vacía, por lo que el producto ya está en la lista de deseos.
                        return Mono.just(false);
                    }
                });
    }

    @Override
    public Mono<Tuple3<List<ProductDto>, List<ProductDto>, List<ProductDto>>> listProducts(Long userId) {
        Mono<List<ProductDto>> productsActive = wishListRepository.listProducts(userId, Constant.STATE_ACTIVE)
                .collectList();
        Mono<List<ProductDto>> productsWithEmptyStock = wishListRepository.listProductsWithEmptyStock(userId)
                .collectList();
        Mono<List<ProductDto>> productsRemoved = wishListRepository.listProducts(userId, Constant.STATE_REMOVED)
                .collectList();
        return Mono.zip(productsActive, productsWithEmptyStock, productsRemoved)
                .flatMap(tuple -> {
                    List<ProductDto> productsActiveList = tuple.getT1();
                    List<ProductDto> productsWithEmptyStockList = tuple.getT2();
                    List<ProductDto> productsRemovedList = tuple.getT3();
                    Flux<Boolean> deleteEmptyStock = Flux.fromIterable(productsWithEmptyStockList)
                            .flatMap(product -> {
                                return wishListRepository.deleteById(userId, product.getId().getValue());
                            });
                    return deleteEmptyStock.then(Mono.zip(Mono.just(productsActiveList), Mono.just(productsWithEmptyStockList), Mono.just(productsRemovedList)));
                });
    }

    @Override
    public Mono<Boolean> deleteProduct(Long userId, Long productId) {
        return wishListRepository.deleteById(userId, productId);
    }
}
