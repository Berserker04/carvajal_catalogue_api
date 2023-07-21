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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/wishlists")
public class WishListController {
    private final ProductMapperShared productMapperShared;
    private final WishListService wishListService;
    private static final Logger logger = LoggerFactory.getLogger(WishListController.class);

    @PostMapping("/{productId}")
    public ResponseEntity<?> addProduct(HttpSession session, @PathVariable Long productId) {
        logger.info("product to wish list: add new product");
        try {
            SecurityContextHolder.getContext().getAuthentication();
            Client client = (Client) session.getAttribute(Constant.KEY_USER_SESSION);

            boolean result = wishListService.addProduct(client.getId().getValue(), productId).block();

            if (!result) return ResponseHandler.success("Can't add product");
            return ResponseHandler.success(HttpStatus.CREATED, "Success");
        } catch (IllegalArgumentException e) {
            logger.info(e.getMessage());
            return ResponseHandler.success(e.getMessage());
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseHandler.error("Internal server error");
        }
    }

    @GetMapping
    public Mono<ResponseEntity<?>> listProductAll(HttpSession session) {
        logger.info("Product: get all");
        try {
            SecurityContextHolder.getContext().getAuthentication();
            Client client = (Client) session.getAttribute(Constant.KEY_USER_SESSION);

            return wishListService.listProducts(client.getId().getValue())
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
                    });
        } catch (IllegalArgumentException e) {
            logger.info(e.getMessage());
            return Mono.just(ResponseHandler.success(e.getMessage()));
        } catch (Exception e) {
            logger.info(e.getMessage());
            return Mono.just(ResponseHandler.error("Internal server error"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(HttpSession session, @PathVariable Long id) {
        logger.info("Product: deleting client {}", id);
        try {
            SecurityContextHolder.getContext().getAuthentication();
            Client client = (Client) session.getAttribute(Constant.KEY_USER_SESSION);

            Boolean result = wishListService.deleteProduct(client.getId().getValue(), id).block();

            if (!result) return ResponseHandler.success("Can't delete Product");
            return ResponseHandler.success("Success");
        } catch (IllegalArgumentException e) {
            logger.info(e.getMessage());
            return ResponseHandler.success(e.getMessage());
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseHandler.error("Internal server error");
        }
    }
}