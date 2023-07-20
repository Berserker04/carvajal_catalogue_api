package com.carvajal.wishlist.dto;

import com.carvajal.product.Product;

import java.util.List;

public class WishListDto {
    private List<Product> productsActiveList;
    private List<Product> productsRemoveLastList;
    private List<Product> productsRemoveList;

    public WishListDto(List<Product> productsActiveList, List<Product> productsRemoveLastList, List<Product> productsRemoveList) {
        this.productsActiveList = productsActiveList;
        this.productsRemoveLastList = productsRemoveLastList;
        this.productsRemoveList = productsRemoveList;
    }
}
