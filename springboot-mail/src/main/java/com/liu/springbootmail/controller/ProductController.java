package com.liu.springbootmail.controller;

import com.liu.springbootmail.constant.ProductCategory;
import com.liu.springbootmail.dto.ProductRequest;
import com.liu.springbootmail.model.Product;
import com.liu.springbootmail.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search
    ){

        List<Product> productList = productService.getProducts(category,search);
        return ResponseEntity.status(HttpStatus.OK).body(productList);
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
