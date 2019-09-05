package com.imooc.o2o.service;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Test
    @Ignore
    public void testAddShop() throws FileNotFoundException {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(1);
        shopCategory.setShopCategoryId(1L);
        shop.setArea(area);
        shop.setOwner(owner);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试店铺3");
        shop.setShopDesc("test1");
        shop.setShopAddr("test1");
        shop.setPhone("test1");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImg = new File("/Users/lichunyang/Pictures/picture.jpg");
        InputStream is = new FileInputStream(shopImg);
//        ShopExecution se = shopService.addShop(shop, is, shopImg.getName());
//        assertEquals(ShopStateEnum.CHECK.getState(), se.getState());
    }

    @Test
    public void testModifyShop() throws FileNotFoundException, ShopOperationException{
        Shop shop = new Shop();
        shop.setShopId(36L);
        shop.setShopName("新名字");
        File shopImg = new File("/Users/lichunyang/Pictures/picture.jpg");
        InputStream is = new FileInputStream(shopImg);
//        ShopExecution shopExecution = shopService.modifyShop(shop, is, "picture.jpg");
//        System.out.println("新的图片地址为：" + shopExecution.getShop().getShopImg());
    }

    @Test
    public void testQueryShopList(){
        Shop shopCondition = new Shop();
        Area area = new Area();
        area.setAreaId(3);
        shopCondition.setShopId(1L);
        ShopExecution se = shopService.queryShopList(shopCondition, 3, 10);
        System.out.println("店铺数量" + se.getShopList().size());
        for(Shop shop : se.getShopList()){
            System.out.println("店铺名称" + shop.getShopName());
        }
        System.out.println("店铺总数量" + se.getCount());
    }
}

