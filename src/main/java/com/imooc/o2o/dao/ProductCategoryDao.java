package com.imooc.o2o.dao;

import com.imooc.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryDao {
    //查询商品类别
    List<ProductCategory> queryProductCategory(@Param("productCategoryCondition") ProductCategory productCategory);

    //批量插入商品
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    int batchDeleteProductCategory(@Param("shopId") long shopId, @Param("productCategoryId") long productCategoryId);
}
