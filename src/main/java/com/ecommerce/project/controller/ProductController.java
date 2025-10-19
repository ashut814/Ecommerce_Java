package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService productService;
    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO, @PathVariable long categoryId) {
        ProductDTO savedProduct = productService.addProduct(productDTO, categoryId);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name =  "pageSize",  defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy",  defaultValue = AppConstants.SORT_PRODUCT_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder",  defaultValue = AppConstants.SORT_BY_ORDER, required = false) String sortOrder
            ) {
        return new ResponseEntity<>(productService.getAllProducts(pageNumber,pageSize,sortBy,sortOrder), HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable  Long categoryId){
        return new ResponseEntity<>(productService.getProductsByCategory(categoryId),  HttpStatus.OK);
    }

    @GetMapping("/public/products/search/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyWord(@PathVariable String keyword){
        return new ResponseEntity<>(productService.getProductsByKeyWord(keyword), HttpStatus.OK);
    }

    @PutMapping("admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody Product product, @PathVariable Long productId){
        return new ResponseEntity<>(productService.updateProduct(product, productId), HttpStatus.OK);
    }

    @DeleteMapping("admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        return new ResponseEntity<ProductDTO>(productService.deleteProduct(productId),HttpStatus.OK);
    }

    @PutMapping("/product/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId, @RequestParam("image")MultipartFile image) throws IOException {
        return new ResponseEntity<ProductDTO>(productService.updateProductImage(productId,image),HttpStatus.OK);
    }
}
