package com.carvajal.product.gatewey.out;

import com.carvajal.product.Product;
import com.carvajal.product.dto.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<ProductDto> save(Product product);
    Flux<ProductDto> getProductAll(Long userId);
    Mono<ProductDto> findById(Long id);
    Mono<ProductDto> findBySlug(Long userId, String slug);
    Mono<Integer> update(Product product);
    Mono<Boolean> deleteById(Long id);
}
