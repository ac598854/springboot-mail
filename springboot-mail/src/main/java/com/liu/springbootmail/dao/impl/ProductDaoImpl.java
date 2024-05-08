package com.liu.springbootmail.dao.impl;

import com.liu.springbootmail.dao.ProductDao;
import com.liu.springbootmail.model.Product;
import com.liu.springbootmail.rowmapper.ProuctRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public Product getProductId(Integer productId) {
        String sql = "SELECT product_id,product_name, category, image_url, price, stock, description, " +
                "created_date, last_modified_date " +
                "FROM product WHERE product_id = :productId";

        Map<String,Object> map = new HashMap<>();
        map.put("productId",productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProuctRowMapper());

        if(!productList.isEmpty()){
            return productList.get(0);
        }else{
            return null;
        }
    }
}
