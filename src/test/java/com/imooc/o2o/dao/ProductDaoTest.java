package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.Shop;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductDaoTest extends BaseTest {
    @Autowired
    ProductDao productDao;

    @Test
    public void testInsertProduct() {
        Shop shop = new Shop();
        shop.setShopId(36L);
        Product product = new Product();
        product.setProductName("test");
        product.setEnableStatus(0);
        product.setLastEditTime(new Date());
        product.setPriority(2);
        product.setShop(shop);
        int effectNum = productDao.insertProduct(product);
        assertEquals(1, effectNum);
    }

    @Test
    public void testBQueryProductList() throws Exception {
        Product productCondition = new Product();
        // 分页查询，预期返回三条结果
        List<Product> productList = productDao.queryProductList(productCondition, 0, 3);
        assertEquals(2, productList.size());
        // 查询名称为测试的商品总数
        int count = productDao.queryProductCount(productCondition);
        assertEquals(2, count);
        // 使用商品名称模糊查询，预期返回两条结果
        productCondition.setProductName("测试");
        productList = productDao.queryProductList(productCondition, 0, 3);
        assertEquals(1, productList.size());
        count = productDao.queryProductCount(productCondition);
        assertEquals(1, count);
    }
}
