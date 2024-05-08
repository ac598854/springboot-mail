package com.liu.springbootmail.dao;

import com.liu.springbootmail.dto.ProductRequest;
import com.liu.springbootmail.model.Product;

public interface ProductDao {

  Product getProductId(Integer productId);

  Integer createProduct(ProductRequest productRequest);

  void updateProduct(Integer productId,ProductRequest productRequest);
}
