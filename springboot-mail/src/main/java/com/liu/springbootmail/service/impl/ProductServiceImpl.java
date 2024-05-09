package com.liu.springbootmail.service.impl;

import com.liu.springbootmail.constant.ProductCategory;
import com.liu.springbootmail.dao.ProductDao;
import com.liu.springbootmail.dto.ProductParams;
import com.liu.springbootmail.dto.ProductRequest;
import com.liu.springbootmail.model.Product;
import com.liu.springbootmail.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductId(Integer productId) {
        return productDao.getProductId(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        productDao.updateProduct(productId,productRequest);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productDao.deleteProduct(productId);
    }

    @Override
    public List<Product> getProducts(ProductParams productParams) {
        return productDao.getProducts(productParams);
    }

    public Integer countProduct(ProductParams productParams){
        return productDao.countProduct(productParams);
    };


}
