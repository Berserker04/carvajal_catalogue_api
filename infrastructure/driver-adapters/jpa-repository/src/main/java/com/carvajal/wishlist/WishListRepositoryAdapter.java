package com.carvajal.wishlist;

import com.carvajal.commons.Constant;
import com.carvajal.product.Product;
import com.carvajal.product.ProductDataRepository;
import com.carvajal.product.ProductMapper;
import com.carvajal.product.dto.ProductDto;
import com.carvajal.shared.mappers.ProductMapperShared;
import com.carvajal.wishlist.gatewey.out.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WishListRepositoryAdapter implements WishListRepository {
    private final WishListMapper mapper;
    private final ProductMapperShared mapperShared;
    private final WishListDataRepository repository;
    @Override
    public Mono<WishList> save(WishList wishList) {
        return Mono.just(wishList)
                .flatMap(mapper::toNewEntityData)
                .flatMap(repository::save)
                .map(mapper::toDomainDtoModel);
    }

    @Override
    public Flux<ProductDto> listProducts(Long userId, String stateFilter) {
        if(stateFilter.equalsIgnoreCase(Constant.STATE_ACTIVE)){
            return repository.findProductsByState(userId)
                    .map(mapperShared::toDomainDtoModel);
        }
        return repository.findProductsByRemoved(userId)
                .map(mapperShared::toDomainDtoModel);
    }

    @Override
    public Flux<ProductDto> listProductsWithEmptyStock(Long userId) {
        return repository.findProductsWithEmptyStock(userId)
                .map(mapperShared::toDomainDtoModel);
    }

    @Override
    public Mono<Boolean> deleteById(Long userId, Long productId) {
        return repository.deleteProduct(userId, productId)
                .then(Mono.just(true))
                .onErrorResume(throwable -> Mono.just(false));
    }
}
