package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ProductImg;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductImgDaoTest extends BaseTest {
    @Autowired
    ProductImgDao productImgDao;

    @Test
    public void testProductImgDao() {
        ProductImg productImg = new ProductImg();
        productImg.setImgAddr("test");
        productImg.setImgDesc("test");
        productImg.setCreateTime(new Date());
        productImg.setProductId(37L);
        List<ProductImg> productImgList = new ArrayList<>();
        productImgList.add(productImg);
        int effectNum = productImgDao.insertProductImg(productImgList);
        assertEquals(1, effectNum);
    }
}
