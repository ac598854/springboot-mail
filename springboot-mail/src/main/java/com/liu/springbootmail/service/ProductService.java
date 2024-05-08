package com.liu.springbootmail.service;

import com.liu.springbootmail.dto.ProductRequest;
import com.liu.springbootmail.model.Product;

import java.util.List;

public interface ProductService {
    Product getProductId(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId,ProductRequest productRequest);
    void deleteProduct(Integer productId);
    List<Product> getProducts();
}
