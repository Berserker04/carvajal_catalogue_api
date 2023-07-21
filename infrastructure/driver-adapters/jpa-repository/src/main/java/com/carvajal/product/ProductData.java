package com.carvajal.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Optional;

@Data
@Builder(toBuilder = true)
@Table(name = "products")
public class ProductData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column("id")
    private Long id;
    private String name;
    private String slug;
    private Double price;
    private String image;
    private Integer stock;
    private String state;
    @Transient
    @Column("isLike")
    private Boolean isLike;

    public Boolean getIsLike() {
        return Optional.ofNullable(isLike).orElse(false);
    }
}