package com.liu.springbootmail.dto;

import com.liu.springbootmail.constant.ProductCategory;

public class ProductParams {
    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    ProductCategory category;
    String search;
}
