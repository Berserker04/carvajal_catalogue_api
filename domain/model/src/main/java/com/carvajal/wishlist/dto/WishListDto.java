package com.carvajal.wishlist.dto;

import com.carvajal.product.Product;
import com.carvajal.product.dto.ProductDto;
import com.carvajal.product.dto.response.ProductResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WishListDto {
    private List<ProductResponse> productsActiveList;
    private List<ProductResponse> productsRemoveLastList;
    private List<ProductResponse> productsRemoveList;

    public WishListDto(List<ProductResponse> productsActiveList, List<ProductResponse> productsRemoveLastList, List<ProductResponse> productsRemoveList) {
        this.productsActiveList = productsActiveList;
        this.productsRemoveLastList = productsRemoveLastList;
        this.productsRemoveList = productsRemoveList;
    }
}
