package com.imooc.o2o.service;

import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> queryProductCategory(ProductCategory productCategoryCondition);

    ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> productCategoryList);

    ProductCategoryExecution batchDeleteProductCategory(ProductCategory productCategory);
}
