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

    @Query("SELECT \n" +
            "    p.*,\n" +
            "    CASE \n" +
            "        WHEN wl.\"productId\" = p.id  THEN True\n" +
            "        ELSE False\n" +
            "    END AS isLike\n" +
            "FROM products p\n" +
            "LEFT JOIN wish_lists wl\n" +
            "ON p.id = wl.\"productId\"\n" +
            "WHERE wl.\"userId\" = $1 \n" +
            "AND wl.\"productId\" = $2 \n" +
            "AND wl.state = 'active' \n" +
            "ORDER BY p.id ASC")
    Flux<ProductData> findProductsByIdAndIsActive(Long userId, Long productId);

    @Query("SELECT \n" +
            "    p.*,\n" +
            "    CASE \n" +
            "        WHEN wl.\"productId\" = p.id  THEN True\n" +
            "        ELSE False\n" +
            "    END AS isLike\n" +
            "FROM products p\n" +
            "LEFT JOIN wish_lists wl\n" +
            "ON p.id = wl.\"productId\"\n" +
            "WHERE wl.\"userId\" = $1 \n" +
            "AND wl.state = 'active' \n" +
            "AND p.stock > 0\n" +
            "ORDER BY p.id ASC")
    Flux<ProductData> findProductsByState(Long userId);

    @Query("SELECT \n" +
            "    p.*,\n" +
            "    CASE \n" +
            "        WHEN wl.\"productId\" = p.id  THEN True\n" +
            "        ELSE False\n" +
            "    END AS isLike\n" +
            "FROM products p\n" +
            "LEFT JOIN wish_lists wl\n" +
            "ON p.id = wl.\"productId\"\n" +
            "WHERE wl.\"userId\" = $1 \n" +
            "AND wl.state = 'removed' \n" +
            "ORDER BY p.id ASC")
    Flux<ProductData> findProductsByRemoved(Long userId);

    @Query("SELECT \n" +
            "    p.*,\n" +
            "    CASE \n" +
            "        WHEN wl.\"productId\" = p.id THEN True\n" +
            "        ELSE False\n" +
            "    END AS isLike\n" +
            "FROM products p\n" +
            "LEFT JOIN wish_lists wl\n" +
            "ON p.id = wl.\"productId\"\n" +
            "WHERE wl.\"userId\" = $1 \n" +
            "AND wl.state = 'active'\n" +
            "AND p.stock = 0\n" +
            "ORDER BY wl.id ASC")
    Flux<ProductData> findProductsWithEmptyStock(Long userId);

}
