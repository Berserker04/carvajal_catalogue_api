package com.carvajal.wishlist.dto;

import com.carvajal.product.Product;
import com.carvajal.product.dto.ProductDto;

import java.util.List;

public class WishListDto {
    private List<ProductDto> productsActiveList;
    private List<ProductDto> productsRemoveLastList;
    private List<ProductDto> productsRemoveList;

    public WishListDto(List<ProductDto> productsActiveList, List<ProductDto> productsRemoveLastList, List<ProductDto> productsRemoveList) {
        this.productsActiveList = productsActiveList;
        this.productsRemoveLastList = productsRemoveLastList;
        this.productsRemoveList = productsRemoveList;
    }
}
