package com.carvajal.product;

import com.carvajal.client.gatewey.out.ClientRepository;
import com.carvajal.client.properties.Password;
import com.carvajal.commons.properties.State;
import com.carvajal.helpers.ConvertString;
import com.carvajal.product.dto.ProductDto;
import com.carvajal.product.gatewey.in.ProductUseCase;
import com.carvajal.product.gatewey.out.ProductRepository;
import com.carvajal.product.properties.Slug;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class ProductUseCaseImp implements ProductUseCase {

    private final ProductRepository productRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

    @Override
    public Mono<Product> createProduct(Product product) {
        product.setSlug(new Slug(ConvertString.slug(product.getName().getValue())));
        product.setState(new State("active"));
        return productRepository.save(product);
    }

    @Override
    public Flux<ProductDto> getProductAll() { return productRepository.getProductAll(); }

    @Override
    public Mono<ProductDto> getProductBySlug(Long userId, String slug) { return productRepository.findBySlug(userId, slug); }

    @Override
    public Mono<Product> updateProduct(Product product) {
        product.setSlug(new Slug(ConvertString.slug(product.getName().getValue())));
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
