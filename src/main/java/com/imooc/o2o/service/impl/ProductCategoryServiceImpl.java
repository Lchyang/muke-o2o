package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ProductCategoryDao;
import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.enums.ProdcutCategoryStateEnum;
import com.imooc.o2o.exceptions.ProductCategoryOperationException;
import com.imooc.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryDao productCategoryDao;

    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryDao productCategoryDao) {
        this.productCategoryDao = productCategoryDao;
    }

    @Override
    public List<ProductCategory> queryProductCategory(ProductCategory productCategoryCondition) {
        return productCategoryDao.queryProductCategory(productCategoryCondition);
    }

    @Override
    @Transactional
    public ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> productCategoryList)
            throws ProductCategoryOperationException {
        if (productCategoryList != null && productCategoryList.size() > 0) {
            try {
                int effectNUm = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if (effectNUm < 0) {
                    throw new ProductCategoryOperationException("插入商品失败");
                }
                return new ProductCategoryExecution(ProdcutCategoryStateEnum.SUCCESS, productCategoryList);
            } catch (Exception e) {
                throw new ProductCategoryOperationException("errMsg" + e.getMessage());
            }
        } else {
            return new ProductCategoryExecution(ProdcutCategoryStateEnum.EMPTY_LIST);
        }
    }

    @Override
    @Transactional
    public ProductCategoryExecution batchDeleteProductCategory(ProductCategory productCategory)
            throws ProductCategoryOperationException {
        //TODO 将此商品类别下面的商品的类别id至为空
        if (productCategory != null) {
            long shopId = productCategory.getShopId();
            long productCategoryId = productCategory.getProductCategoryId();
            try {
                int effectNum = productCategoryDao.batchDeleteProductCategory(shopId, productCategoryId);
                if (effectNum < 0) {
                    throw new ProductCategoryOperationException("删除失败");
                } else {
                    return new ProductCategoryExecution(ProdcutCategoryStateEnum.SUCCESS);
                }
            } catch (Exception e) {
                throw new ProductCategoryOperationException("删除失败" + e.getMessage());
            }
        } else {
            throw new ProductCategoryOperationException("商品类别为空");
        }
    }
}
