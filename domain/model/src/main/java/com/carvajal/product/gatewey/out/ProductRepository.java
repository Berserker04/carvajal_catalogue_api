package com.carvajal.product.gatewey.out;

import com.carvajal.product.Product;
import com.carvajal.product.dto.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> save(Product client);
    Flux<ProductDto> getProductAll();
    Mono<Product> findById(Long id);
    Mono<ProductDto> findBySlug(String slug);
    Mono<Integer> update(Product product);
    Mono<Boolean> deleteById(Long id);
}
