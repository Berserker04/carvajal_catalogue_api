package com.carvajal.product.gatewey.in;

import com.carvajal.product.Product;
import com.carvajal.product.dto.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductUseCase {
    Mono<ProductDto> createProduct(Product product);
    Flux<ProductDto> getProductAll(Long userId);
    Mono<ProductDto> getProductBySlug(Long userId, String slug);
    Mono<ProductDto> updateProduct(Product product);
    Mono<Boolean> deleteProduct(Long id);
}
