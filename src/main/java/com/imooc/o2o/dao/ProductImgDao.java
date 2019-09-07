package com.imooc.o2o.dao;

import com.imooc.o2o.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {
    int insertProductImg(List<ProductImg> productImgList);

    List<ProductImg> queryProductImg(long productId);

    int deleteProductImgByProductId(long productId);
}
