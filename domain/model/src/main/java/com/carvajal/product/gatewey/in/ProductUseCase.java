package com.carvajal.product.gatewey.in;

import com.carvajal.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductUseCase {
    Mono<Product> createProduct(Product product);
    Flux<Product> getProductAll();
    Mono<Product> getProductBySlug(String slug);
    Mono<Product> updateProduct(Product product);
    Mono<Boolean> deleteProduct(Long id);
}
