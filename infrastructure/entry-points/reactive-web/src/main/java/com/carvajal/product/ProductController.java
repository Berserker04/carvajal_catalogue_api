package com.carvajal.product;

import com.carvajal.client.Client;
import com.carvajal.commons.Constant;
import com.carvajal.http.ResponseHandler;
import com.carvajal.product.services.ProductService;
import com.carvajal.shared.mappers.ProductMapperShared;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductMapperShared productMapperShared;
    private final ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping()
    public Mono<ResponseEntity<Map<String, Object>>> createProduct(@RequestBody Product product) {
        logger.info("Product: creating new product");
        return productService.createProduct(product)
                .flatMap(productMapperShared::toDomainResponseModel)
                .map(entityData -> ResponseHandler.success("Success", entityData, HttpStatus.CREATED))
                .switchIfEmpty(Mono.just(ResponseHandler.success("Can't register product")))
                .onErrorResume(IllegalArgumentException.class, e -> {
                    logger.info(e.getMessage());
                    return Mono.just(ResponseHandler.success(e.getMessage()));
                })
                .onErrorResume(Exception.class, e -> {
                    logger.info(e.getMessage());
                    return Mono.just(ResponseHandler.error("Internal server error"));
                });
    }

    @GetMapping()
    public Mono<ResponseEntity<Map<String, Object>>> getProductAll(HttpSession session) {
        logger.info("Product: get all");
        return Mono.just(session.getAttribute(Constant.KEY_USER_SESSION))
                .ofType(Client.class)
                .flatMap(client -> productService.getProductAll(client.getId().getValue())
                        .flatMap(productMapperShared::toDomainResponseModel)
                        .collectList()
                        .flatMap(result -> Mono.just(ResponseHandler.success("Success", result))))
                .switchIfEmpty(Mono.just(ResponseHandler.success("Product not found")))
                .onErrorResume(IllegalArgumentException.class, e -> {
                    logger.info(e.getMessage());
                    return Mono.just(ResponseHandler.success(e.getMessage()));
                })
                .onErrorResume(Exception.class, e -> {
                    logger.info(e.getMessage());
                    return Mono.just(ResponseHandler.error("Internal server error"));
                });
    }


    @GetMapping("/{slug}")
    public Mono<ResponseEntity<Map<String, Object>>> getProductBySlug(HttpSession session, @PathVariable String slug) {
        return Mono.just(session.getAttribute(Constant.KEY_USER_SESSION))
                .map(client -> (Client) client)
                .flatMap(client -> productService.getProductBySlug(client.getId().getValue(), slug))
                .flatMap(productMapperShared::toDomainResponseModel)
                .flatMap(reponseData -> Mono.just(ResponseHandler.success("Success", reponseData)))
                .switchIfEmpty(Mono.just(ResponseHandler.success("Product not found")))
                .onErrorResume(IllegalArgumentException.class, e -> {
                    logger.info(e.getMessage());
                    return Mono.just(ResponseHandler.success(e.getMessage()));
                })
                .onErrorResume(Exception.class, e -> {
                    logger.info(e.getMessage());
                    return Mono.just(ResponseHandler.error("Internal server error"));
                });
    }

    @PutMapping()
    public Mono<ResponseEntity<Map<String, Object>>> updateProduct(@RequestBody Product product) {
        logger.info("Product: updating product");
        return productService.updateProduct(product)
                .flatMap(productMapperShared::toDomainResponseModel)
                .map(entityData -> ResponseHandler.success("Success", entityData))
                .switchIfEmpty(Mono.just(ResponseHandler.success("Could not update product")))
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
    public Mono<ResponseEntity<Map<String, Object>>> deleteProduct(@PathVariable Long id) {
        logger.info("Product: deleting product {}", id);
        return productService.deleteProduct(id)
                .flatMap(result -> Mono.just(ResponseHandler.success("Success")))
                .switchIfEmpty(Mono.just(ResponseHandler.success("Can't delete product")))
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
