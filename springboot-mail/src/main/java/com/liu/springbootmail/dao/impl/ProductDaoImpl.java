package com.liu.springbootmail.dao.impl;

import com.liu.springbootmail.dao.ProductDao;
import com.liu.springbootmail.dto.ProductParams;
import com.liu.springbootmail.dto.ProductRequest;
import com.liu.springbootmail.model.Product;
import com.liu.springbootmail.rowmapper.ProuctRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
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

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product(product_name, category, image_url, price, stock, " +
                "description, created_date, last_modified_date) " +
                "VALUES (:productName,:category,:imageUrl,:price,:stock,:description," +
                ":createdDate,:lastModifiedDate)";

        Map<String,Object> map = new HashMap<>();
        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());
        Date now = new Date();
        map.put("createdDate",now);
        map.put("lastModifiedDate",now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map),keyHolder);

        int productId = keyHolder.getKey().intValue();

        return productId;


    }

    public void updateProduct(Integer productId,ProductRequest productRequest){
        String sql = "UPDATE product SET product_name = :productName, category = :category, image_url = :imageUrl," +
                "price = :price, stock = :stock, description = :description, last_modified_date = :lastModifiedDate" +
                " WHERE product_id = :productId";

        Map<String,Object> map = new HashMap<>();
        map.put("productId",productId);

        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        map.put("lastModifiedDate",new Date());

        namedParameterJdbcTemplate.update(sql,map);
    }

    public void deleteProduct(Integer productId){
        String sql = "DELETE FROM product WHERE product_id = :productId";

        Map<String,Object> map = new HashMap<>();
        map.put("productId",productId);

        namedParameterJdbcTemplate.update(sql,map);

    }

    @Override
    public List<Product> getProducts(ProductParams productParams) {
        String sql = "SELECT product_id,product_name, category, image_url, price, stock, " +
                "description, created_date, last_modified_date " +
                "FROM product WHERE 1=1";

        Map<String,Object> map = new HashMap<>();
        //查詢條件
        if(productParams.getCategory() != null){
            sql = sql + " AND category = :category";
            map.put("category",productParams.getCategory().name());
        }

        if(productParams.getSearch() != null){
            sql = sql+" AND product_name LIKE :search";
            map.put("search", "%" + productParams.getSearch() + "%");
        }
        //排序
        sql = sql + " ORDER BY " + productParams.getOrderBy() + " " + productParams.getSort();

        //分頁
        sql = sql + " LIMIT :limit OFFSET :offset";
        map.put("limit",productParams.getLimit());
        map.put("offset",productParams.getOffset());

        List<Product> productList = namedParameterJdbcTemplate.query(sql,map,new ProuctRowMapper());

        return productList;
    }


    public Integer countProduct(ProductParams productParams){
        String sql = "SELECT count(*) FROM product WHERE 1=1";

        Map<String,Object> map = new HashMap<>();

        if(productParams.getCategory() != null){
            sql = sql + " AND category = :category";
            map.put("category",productParams.getCategory().name());
        }

        if(productParams.getSearch() != null){
            sql = sql+" AND product_name LIKE :search";
            map.put("search", "%" + productParams.getSearch() + "%");
        }

        Integer total = namedParameterJdbcTemplate.queryForObject(sql,map,Integer.class);
        return total;
    }


}
