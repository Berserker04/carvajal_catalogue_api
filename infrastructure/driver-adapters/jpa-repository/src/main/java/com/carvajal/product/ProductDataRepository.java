package com.carvajal.product;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductDataRepository extends ReactiveCrudRepository<ProductData, Long> {

    @Query("SELECT DISTINCT ON (p.id)\n" +
            "    p.*,\n" +
            "    COALESCE(wl.\"productId\" = p.id AND wl.\"userId\" = $1, false) AS isLike\n" +
            "FROM products p\n" +
            "LEFT JOIN wish_lists wl ON p.id = wl.\"productId\"\n" +
            "WHERE p.slug = $2\n" +
            "ORDER BY p.id ASC;\n")
    Mono<ProductData> findBySlug(Long userId, String slug);

    @Modifying
    @Query("UPDATE products SET name = :name, slug = :slug, price = :price, image = :image, stock = :stock, state = :state WHERE id = :id")
    Mono<Integer> updateFields(String name, String slug, Double price, String image, Integer stock, String state, Long id);

    @Query("UPDATE products SET state = 'removed' WHERE id = :?")
    Mono<Integer> deleteProduct(Long id);

    @Query("SELECT DISTINCT ON (p.id)\n" +
            "    p.*,\n" +
            "    COALESCE(wl.\"userId\" = $1, false) AS isLike\n" +
            "FROM products p\n" +
            "LEFT JOIN wish_lists wl ON p.id = wl.\"productId\"\n" +
            "WHERE p.state = 'active'" +
            "ORDER BY p.id ASC;")
    Flux<ProductData> findAll(Long userId);
}
