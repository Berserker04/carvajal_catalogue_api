package com.carvajal.product;

import com.carvajal.auth.service.UserDetailsServiceImpl;
import com.carvajal.client.Client;
import com.carvajal.client.ClientController;
import com.carvajal.client.ClientData;
import com.carvajal.http.ResponseHandler;
import com.carvajal.product.dto.ProductDto;
import com.carvajal.product.services.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductMapper mapper;
    private final ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping()
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        logger.info("Product: creating new product");
        try {
            SecurityContextHolder.getContext().getAuthentication();

            Product result = productService.createProduct(product).block();

            if(result == null) return ResponseHandler.success( "Can't register product");
            return ResponseHandler.success("Success", mapper.toEntityData(result).block(), HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            logger.info(e.getMessage());
            return ResponseHandler.success(e.getMessage());
        }catch (Exception e){
            logger.info(e.getMessage());
            return ResponseHandler.error("Internal server error");
        }
    }

    @GetMapping()
    public ResponseEntity<?> getProductAll(HttpSession session) {
        logger.info("Product: get all");
        try {
            SecurityContextHolder.getContext().getAuthentication();
            Client client = (Client) session.getAttribute("userSession");

            List<ProductData> result = productService.getProductAll()
                    .flatMap(product -> mapper.toEntityData(product))
                    .collect(Collectors.toList()).block();

            if(result.size() == 0) return ResponseHandler.success("Products not found");
            return ResponseHandler.success("Success", result);
        }catch (IllegalArgumentException e){
            logger.info(e.getMessage());
            return ResponseHandler.success(e.getMessage());
        }catch (Exception e){
            logger.info(e.getMessage());
            return ResponseHandler.error("Internal server error");
        }
    }

    @GetMapping("/{slug}")
    public ResponseEntity<?> getProductBySlug(HttpSession session, @PathVariable String slug) {
        try {
            SecurityContextHolder.getContext().getAuthentication();
            Client client = (Client) session.getAttribute("userSession");

            ProductDto result = productService.getProductBySlug(client.getId().getValue(), slug).block();

            if(result == null) return ResponseHandler.success( "Product not found");
            return ResponseHandler.success("Success", mapper.toEntityData(result).block());
        }catch (IllegalArgumentException e){
            logger.info(e.getMessage());
            return ResponseHandler.success(e.getMessage());
        }catch (Exception e){
            logger.info(e.getMessage());
            return ResponseHandler.error("Internal server error");
        }
    }

    @PutMapping()
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        try {
            SecurityContextHolder.getContext().getAuthentication();

            Product result = productService.updateProduct(product).block();

            if(result == null) return ResponseHandler.success( "Could not update product");
            return ResponseHandler.success("Success", mapper.toEntityData(result).block());
        }catch (IllegalArgumentException e){
            logger.info(e.getMessage());
            return ResponseHandler.success(e.getMessage());
        }catch (Exception e){
            logger.info(e.getMessage());
            return ResponseHandler.error("Internal server error");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        logger.info("Product: deleting client {}", id);
        try {
            SecurityContextHolder.getContext().getAuthentication();

            Boolean result = productService.deleteProduct(id).block();

            if(!result) return ResponseHandler.success( "Can't delete Product");
            return ResponseHandler.success("Success");
        }catch (IllegalArgumentException e){
            logger.info(e.getMessage());
            return ResponseHandler.success(e.getMessage());
        }catch (Exception e){
            logger.info(e.getMessage());
            return ResponseHandler.error("Internal server error");
        }
    }
}
