package com.carvajal.product.services;

import com.carvajal.product.Product;
import com.carvajal.product.dto.ProductDto;
import com.carvajal.product.gatewey.in.ProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {
    private final ProductUseCase productUseCase;

    @Override
    public Mono<Product> createProduct(Product product) {
        return productUseCase.createProduct(product);
    }

    @Override
    public Flux<ProductDto> getProductAll(Long userId) {
        return productUseCase.getProductAll(userId);
    }

    @Override
    public Mono<ProductDto> getProductBySlug(Long userId, String slug) {
        return productUseCase.getProductBySlug(userId, slug);
    }

    @Override
    public Mono<Product> updateProduct(Product product) {
        return productUseCase.updateProduct(product);
    }

    @Override
    public Mono<Boolean> deleteProduct(Long id) {
        return productUseCase.deleteProduct(id);
    }
}
