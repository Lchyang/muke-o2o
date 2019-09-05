package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

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
}
