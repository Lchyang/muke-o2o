package com.imooc.o2o.service;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;

import java.util.List;


public interface ProductService {
    ProductExecution addProduct(Product product, ImageHolder image, List<ImageHolder> imageHolderList);

    ProductExecution updateProduct(Product product, ImageHolder image, List<ImageHolder> imageHolderList);

    Product getProductById(long productId);

    ProductExecution getProductList(Product product, int pageIndex, int pageSize);
}
