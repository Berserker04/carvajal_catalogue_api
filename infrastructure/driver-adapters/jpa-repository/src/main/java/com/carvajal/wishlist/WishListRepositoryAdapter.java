package com.carvajal.wishlist;

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
        return repository.findProductsByState(userId, stateFilter)
                .map(mapperShared::toDomainDtoModel);
    }

    @Override
    public Flux<ProductDto> listProductsWithEmptyStock(Long userId) {
        return repository.findProductsWithEmptyStock(userId)
                .map(mapperShared::toDomainDtoModel);
    }

    @Override
    public Mono<Boolean> deleteById(Long id) {
        return repository.findById(id)
                .flatMap(product -> {
                    if (product != null) {
                        repository.deleteProduct(id).subscribe();
                        return Mono.just(true);
                    }
                    return Mono.just(false);
                });
    }

    @Override
    public Mono<Boolean> deleteAllWithEmptyStock(Long userId) {
        repository.deleteAllWithEmptyStock(userId).subscribe();
        return Mono.just(true);
    }
}
