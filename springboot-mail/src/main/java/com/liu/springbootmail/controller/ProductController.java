package com.liu.springbootmail.controller;

import com.liu.springbootmail.dto.ProductRequest;
import com.liu.springbootmail.model.Product;
import com.liu.springbootmail.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

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
    public  ResponseEntity<Product> createProduct(@RequestBody @Valid Product productRequest){
       Integer productId = productService.createProduct(productRequest);

       Product product = productService.getProductId(productId);

       return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }



}
