package com.liu.springbootmail.service.impl;

import com.liu.springbootmail.dao.ProductDao;
import com.liu.springbootmail.model.Product;
import com.liu.springbootmail.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductId(Integer productId) {
        return productDao.getProductId(productId);
    }
}