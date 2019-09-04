package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductCategoryDaoTest extends BaseTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void TestProductCategroy() {
        ProductCategory productCategory = new ProductCategory();
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategory(productCategory);
        for (ProductCategory productCategory1 : productCategoryList) {
            System.out.println(productCategory1.getProductCategoryName());
        }
//        assertEquals(2, productCategoryList.size());
    }

    @Test
    public void TestBatchInsertProductCategoryDao(){
        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setShopId(36L);
        productCategory1.setProductCategoryName("测试");
        productCategory1.setCreateTime(new Date());
        productCategory1.setPriority(2);
        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setShopId(36L);
        productCategory2.setProductCategoryName("测试2");
        productCategory2.setCreateTime(new Date());
        productCategory2.setPriority(3);
        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(productCategory1);
        productCategoryList.add(productCategory2);
        int effectNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
        assertEquals(2, effectNum);
    }

    @Test
    public void TestBatchDeleteProductCategoryDao(){
        ProductCategory pc = new ProductCategory();
        pc.setShopId(36L);
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategory(pc);
        for(ProductCategory ps : productCategoryList){
            if (ps.getProductCategoryName().equals("新增1")) {
                int effectNUm = productCategoryDao.batchDeleteProductCategory(ps.getShopId(), ps.getProductCategoryId());
                assertEquals(1, effectNUm);
            }
        }

    }
}
