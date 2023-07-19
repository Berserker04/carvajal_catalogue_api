package com.carvajal.product.gatewey.out;

import com.carvajal.product.Product;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> save(Product client);
    Mono<Product> findById(Long id);
    Mono<Product> findBySlug(String slug);
    Mono<Integer> update(Product product);
    Mono<Boolean> deleteById(Long id);
}
