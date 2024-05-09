package com.liu.springbootmail.controller;

import com.liu.springbootmail.constant.ProductCategory;
import com.liu.springbootmail.dto.ProductParams;
import com.liu.springbootmail.dto.ProductRequest;
import com.liu.springbootmail.model.Product;
import com.liu.springbootmail.service.ProductService;
import com.liu.springbootmail.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product")
    public ResponseEntity<Page<Product>> getProducts(
            //查詢條件
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,

            //排序
            @RequestParam(defaultValue = "created_date")String orderBy,
            @RequestParam(defaultValue = "desc") String sort,

            //分頁
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0")  @Min(0) Integer offset

    ){
        ProductParams productParams = new ProductParams();
        productParams.setCategory(category);
        productParams.setSearch(search);
        productParams.setOrderBy(orderBy);
        productParams.setSort(sort);
        productParams.setLimit(limit);
        productParams.setOffset(offset);

        //取得商品列表
        List<Product> productList = productService.getProducts(productParams);

        //取得product總數
        Integer total = productService.countProduct(productParams);

        //分頁
        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResult(productList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
        Product product = productService.getProductId(productId);
        if(product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/product")
    public  ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
       Integer productId = productService.createProduct(productRequest);

       Product product = productService.getProductId(productId);

       return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<Product> update(@PathVariable Integer productId,
                                          @RequestBody @Valid ProductRequest productRequest){

        Product product = productService.getProductId(productId);

        if(product == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }


        productService.updateProduct(productId,productRequest);

        Product updateProduct = productService.getProductId(productId);

        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);


    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Integer productId){
        productService.deleteProduct(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }



}
