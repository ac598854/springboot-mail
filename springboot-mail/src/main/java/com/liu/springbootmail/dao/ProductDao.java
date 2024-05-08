package com.liu.springbootmail.dao;

import com.liu.springbootmail.model.Product;

public interface ProductDao {

  abstract  Product getProductId(Integer productId);
}
