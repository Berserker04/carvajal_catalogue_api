package com.carvajal.product;

import com.carvajal.client.Client;
import com.carvajal.client.gatewey.out.ClientRepository;
import com.carvajal.product.gatewey.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {

    private final ProductMapper mapper;
    private final ProductDataRepository repository;
    @Override
    public Mono<Product> save(Product product) {
        return Mono.just(product)
                .flatMap(mapper::toNewEntityData)
                .flatMap(repository::save)
                .map(mapper::toDomainModel);
    }

    @Override
    public Flux<Product> getProductAll() {
        return repository.findAll()
                .map(mapper::toDomainModel);
    }

    @Override
    public Mono<Product> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomainModel);
    }

    @Override
    public Mono<Product> findBySlug(String slug) {
        return repository.findBySlug(slug)
                .map(mapper::toDomainModel);
    }

    @Override
    public Mono<Integer> update(Product product) {
        return repository.existsById(product.getId().getValue())
                .flatMap(exists -> {
                    if(exists){
                        return Mono.just(product)
                                .flatMap(p->{
                                    repository.updateFields(
                                            p.getName().getValue(),
                                            p.getSlug().getValue(),
                                            p.getPrice().getValue(),
                                            p.getImage().getValue(),
                                            p.getStock().getValue(),
                                            p.getState().getValue(),
                                            p.getId().getValue()
                                    )
                                            .subscribe(integer -> {
                                                System.out.println(integer);
                                            });

                                    return Mono.just(1);
                                });
                    }
                    return Mono.empty();
                });
    }

    @Override
    public Mono<Boolean> deleteById(Long id) {
        return repository.findById(id)
                .flatMap(client -> {
                    if(client != null){
                        repository.deleteProduct(id).subscribe();
                        return Mono.just(true);
                    }
                    return Mono.just(false);
                });
    }
}
