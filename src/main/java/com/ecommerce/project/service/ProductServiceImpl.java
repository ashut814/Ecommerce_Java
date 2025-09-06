package com.ecommerce.project.service;

import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repositiories.CategoryRepository;
import com.ecommerce.project.repositiories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements  ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ProductDTO addProduct(Product product, long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",categoryId));
        product.setCategory(category);
        double specialPrice = product.getDiscountPercentage();
        double discountAmount = (specialPrice / 100) * product.getProductPrice();
        double finalPrice = product.getProductPrice() - discountAmount;
        product.setSpecialPrice(finalPrice);
        product.setProductImage("default image");
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> products =  productRepository.findAll();
        List<ProductDTO> productDTOs = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProducts(productDTOs);

        return productResponse;
    }

    @Override
    public ProductResponse getProductsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",categoryId));

        List<Product> products = productRepository.findByCategory(category);

        List<ProductDTO> productDTOs = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProducts(productDTOs);

        return productResponse;
    }

    @Override
    public ProductResponse getProductsByKeyWord(String keyword) {
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase("%" + keyword + "%");


        List<ProductDTO> productDTOs = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProducts(productDTOs);

        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Product product, Long productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", productId));

        existingProduct.setProductName(product.getProductName());
        existingProduct.setProductDescription(product.getProductDescription());
        existingProduct.setProductPrice(product.getProductPrice());
        existingProduct.setDiscountPercentage(product.getDiscountPercentage());

        double discountAmount = (existingProduct.getDiscountPercentage() / 100.0) * existingProduct.getProductPrice();
        double finalPrice = existingProduct.getProductPrice() - discountAmount;
        existingProduct.setSpecialPrice(finalPrice);

        Product updatedProduct = productRepository.save(existingProduct);

        return modelMapper.map(updatedProduct, ProductDTO.class);
    }
}
