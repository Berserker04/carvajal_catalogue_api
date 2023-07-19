package com.carvajal.product;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductDataRepository extends ReactiveCrudRepository<ProductData, Long> {
    Mono<ProductData> findBySlug(String slug);
    @Query("UPDATE producs SET name = :?, slug = :?, price = :?, image = :?, stock = :?, state = :? WHERE id = :?")
    Mono<Integer> updateFieldsById(String name, String slug, Double price, String image, Integer stock, String state, Long id);
//
    @Query("UPDATE producs SET state = 'False' WHERE id = :?")
    Mono<Integer> deleteProduct(Long id);
}
