package com.carvajal.wishlist;

import com.carvajal.client.Client;
import com.carvajal.commons.Constant;
import com.carvajal.http.ResponseHandler;
import com.carvajal.product.dto.response.ProductResponse;
import com.carvajal.shared.mappers.ProductMapperShared;
import com.carvajal.wishlist.dto.WishListDto;
import com.carvajal.wishlist.services.WishListService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/wishlists")
public class WishListController {
    private final ProductMapperShared productMapperShared;
    private final WishListService wishListService;
    private static final Logger logger = LoggerFactory.getLogger(WishListController.class);

    @PostMapping("/{productId}")
    public Mono<ResponseEntity<Map<String, Object>>> addProduct(HttpSession session, @PathVariable Long productId) {
        logger.info("product to wish list: add new product");
        return Mono.just(session.getAttribute(Constant.KEY_USER_SESSION))
                .ofType(Client.class)
                .flatMap(client -> wishListService.addProduct(client.getId().getValue(), productId))
                .flatMap(result -> Mono.just(ResponseHandler.success(HttpStatus.CREATED, "Success")))
                .switchIfEmpty(Mono.just(ResponseHandler.success("Can't add product")))
                .onErrorResume(IllegalArgumentException.class, e -> {
                    logger.info(e.getMessage());
                    return Mono.just(ResponseHandler.success(e.getMessage()));
                })
                .onErrorResume(Exception.class, e -> {
                    logger.info(e.getMessage());
                    return Mono.just(ResponseHandler.error("Internal server error"));
                });
    }

    @GetMapping
    public Mono<ResponseEntity<Map<String, Object>>> listProductAll(HttpSession session) {
        logger.info("Product: get all");
        return Mono.just(session.getAttribute(Constant.KEY_USER_SESSION))
                .ofType(Client.class)
                .flatMap(client -> wishListService.listProducts(client.getId().getValue())
                        .flatMap(wishlistT -> {
                            Flux<ProductResponse> result1 = Flux.fromIterable(wishlistT.getT1())
                                    .flatMap(productDto -> productMapperShared.toDomainResponseModel(productDto));
                            Flux<ProductResponse> result2 = Flux.fromIterable(wishlistT.getT2())
                                    .flatMap(productDto -> productMapperShared.toDomainResponseModel(productDto));
                            Flux<ProductResponse> result3 = Flux.fromIterable(wishlistT.getT3())
                                    .flatMap(productDto -> productMapperShared.toDomainResponseModel(productDto));

                            return Mono.zip(result1.collectList(), result2.collectList(), result3.collectList())
                                    .map(WishListT -> {
                                        WishListDto wishListDto = new WishListDto(WishListT.getT1(), WishListT.getT2(), WishListT.getT3());
                                        return ResponseHandler.success("Success", wishListDto);
                                    });
                        }))
                .switchIfEmpty(Mono.just(ResponseHandler.success("Products not found")))
                .onErrorResume(IllegalArgumentException.class, e -> {
                    logger.info(e.getMessage());
                    return Mono.just(ResponseHandler.success(e.getMessage()));
                })
                .onErrorResume(Exception.class, e -> {
                    logger.info(e.getMessage());
                    return Mono.just(ResponseHandler.error("Internal server error"));
                });
    }


    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Map<String, Object>>> deleteProduct(HttpSession session, @PathVariable Long id) {
        logger.info("Product: deleting client {}", id);
        return Mono.just(session.getAttribute(Constant.KEY_USER_SESSION))
                .ofType(Client.class)
                .flatMap(client -> wishListService.deleteProduct(client.getId().getValue(), id))
                .flatMap(result -> Mono.just(ResponseHandler.success("Success")))
                .switchIfEmpty(Mono.just(ResponseHandler.success("Can't delete Product")))
                .onErrorResume(IllegalArgumentException.class, e -> {
                    logger.info(e.getMessage());
                    return Mono.just(ResponseHandler.success(e.getMessage()));
                })
                .onErrorResume(Exception.class, e -> {
                    logger.info(e.getMessage());
                    return Mono.just(ResponseHandler.error("Internal server error"));
                });
    }
}
