package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {
    int insertProduct(Product product);

    int updateProduct(Product product);

    List<Product> queryProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int rowIndex,
                                   @Param("pageSize") int pageSize);

    Product queryProductById(long productId);

    int deleteProudct(@Param("productId") long productId, @Param("shopId") long shopId);
}
