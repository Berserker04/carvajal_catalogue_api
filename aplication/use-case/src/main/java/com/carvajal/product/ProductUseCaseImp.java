package com.carvajal.product;

import com.carvajal.client.gatewey.out.ClientRepository;
import com.carvajal.client.properties.Password;
import com.carvajal.product.gatewey.in.ProductUseCase;
import com.carvajal.product.gatewey.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCaseImp implements ProductUseCase {

    private final ProductRepository productRepository;

    @Override
    public Mono<Product> createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Mono<Product> getProductBySlug(String slug) {
        return productRepository.findBySlug(slug);
    }

    @Override
    public Mono<Product> updateProduct(Product product) {;
        return productRepository.update(product)
                .flatMap(result-> {
                    if(result >= 1){
                        return productRepository.findById(product.getId().getValue());
                    }
                    return null;
                });
    }

    @Override
    public Mono<Boolean> deleteProduct(Long id) {
        return productRepository.deleteById(id);
    }
}
