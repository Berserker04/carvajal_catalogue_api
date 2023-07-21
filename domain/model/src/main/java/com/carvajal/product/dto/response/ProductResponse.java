package com.carvajal.product.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ProductResponse {
    private Long id;
    private String name;
    private String slug;
    private Double price;
    private String image;
    private Integer stock;
    private String state;
    private Boolean isLike;
}
