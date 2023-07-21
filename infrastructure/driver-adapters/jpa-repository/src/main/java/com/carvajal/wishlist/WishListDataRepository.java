package com.carvajal.wishlist;

import com.carvajal.product.Product;
import com.carvajal.product.ProductData;
import com.carvajal.product.dto.ProductDto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface WishListDataRepository  extends ReactiveCrudRepository<WishListData, Long> {

    @Query("UPDATE wish_lists SET state = 'removed' WHERE state = 'active' AND \"userId\" = $1 AND \"productId\" = $2")
    Mono<Integer> deleteProduct(Long userId, Long productId);

    @Query("SELECT DISTINCT ON (p.id)\n" +
            "    p.*,\n" +
            "    COALESCE(wl.\"productId\" = p.id, false) AS isLike\n" +
            "FROM products p\n" +
            "INNER JOIN wish_lists wl ON p.id = wl.\"productId\" AND wl.\"userId\" = $1 AND wl.state = 'active'\n" +
            "WHERE p.id = $2\n" +
            "ORDER BY p.id ASC")
    Flux<ProductData> findProductsByIdAndIsActive(Long userId, Long productId);

    @Query("SELECT DISTINCT ON (p.id)\n" +
            "    p.*,\n" +
            "    COALESCE(wl.\"productId\" = p.id, false) AS isLike\n" +
            "FROM products p\n" +
            "INNER JOIN wish_lists wl ON p.id = wl.\"productId\" AND wl.\"userId\" = $1 AND wl.state = 'active'\n" +
            "WHERE p.stock > 0\n" +
            "ORDER BY p.id ASC;\n")
    Flux<ProductData> findProductsByState(Long userId);

    @Query("SELECT DISTINCT ON (p.id)\n" +
            "    p.*,\n" +
            "    COALESCE(wl.\"productId\" = p.id, false) AS isLike\n" +
            "FROM products p\n" +
            "INNER JOIN wish_lists wl ON p.id = wl.\"productId\" AND wl.\"userId\" = $1 AND wl.state = 'removed'\n" +
            "ORDER BY p.id ASC;\n")
    Flux<ProductData> findProductsByRemoved(Long userId);

    @Query("SELECT DISTINCT ON (p.id)\n" +
            "    p.*,\n" +
            "    COALESCE(wl.\"productId\" = p.id, false) AS isLike\n" +
            "FROM products p\n" +
            "INNER JOIN wish_lists wl ON p.id = wl.\"productId\" AND wl.\"userId\" = $1 AND wl.state = 'active'\n" +
            "WHERE p.stock = 0\n" +
            "ORDER BY p.id ASC;\n")
    Flux<ProductData> findProductsWithEmptyStock(Long userId);

}
