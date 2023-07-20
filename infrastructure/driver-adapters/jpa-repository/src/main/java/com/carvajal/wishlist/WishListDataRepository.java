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


    @Query("UPDATE WishListData SET state = 'removed' WHERE id = :?")
    Mono<Integer> deleteProduct(Long id);

    @Query("SELECT \n" +
            "    p.*,\n" +
            "    CASE \n" +
            "        WHEN wl.\"productId\" = p.id  THEN True\n" +
            "        ELSE False\n" +
            "    END AS isLike\n" +
            "FROM products p\n" +
            "LEFT JOIN wish_lists wl\n" +
            "ON p.id = wl.\"productId\"\n" +
            "WHERE wl.\"userId\" = :? \n" +
            "AND wl.state = :? \n" +
            "ORDER BY p.id ASC")
    Flux<ProductData> findProductsByState(Long userId, String stateFilter);

    @Query("SELECT \n" +
            "    p.*,\n" +
            "    CASE \n" +
            "        WHEN wl.\"productId\" = p.id THEN True\n" +
            "        ELSE False\n" +
            "    END AS isLike\n" +
            "FROM products p\n" +
            "LEFT JOIN wish_lists wl\n" +
            "ON p.id = wl.\"productId\"\n" +
            "WHERE wl.\"userId\" = 2 \n" +
            "AND wl.state = 'active'\n" +
            "AND p.stock = 0\n" +
            "ORDER BY wl.id ASC")
    Flux<ProductData> findProductsWithEmptyStock(Long userId);

    @Query("UPDATE wish_lists wl\n" +
            "SET state = 'removed'\n" +
            "FROM products p\n" +
            "WHERE p.stock = 0\n" +
            "AND wl.state = 'active'\n" +
            "AND wl.\"userId\" = :? ")
    Mono<Integer> deleteAllWithEmptyStock(Long userId);

}
