package com.carvajal.product.gatewey.in;

import com.carvajal.product.Product;
import com.carvajal.product.dto.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductUseCase {
    Mono<Product> createProduct(Product product);
    Flux<ProductDto> getProductAll();
    Mono<ProductDto> getProductBySlug(String slug);
    Mono<Product> updateProduct(Product product);
    Mono<Boolean> deleteProduct(Long id);
}
