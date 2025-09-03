package com.ecommerce.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long productId;
    private String productName;
    private String productDescription;
    private Double productPrice;
    private String productImage;
    private Integer productQuantity;
    private double specialPrice;
    private double discountPercentage;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private  Category category;
}
