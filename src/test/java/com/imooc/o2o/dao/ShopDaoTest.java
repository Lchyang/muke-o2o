package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;

    @Test
    @Ignore
    public void testInsertShopDao() {
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
        shop.setShopName("测试店铺");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        int effectedNum = shopDao.insertShop(shop);
        assertEquals(1, effectedNum);
    }

    @Test
    @Ignore
    public void testUpdateShopDao() {
        Shop shop = new Shop();
        shop.setShopId(36L);
        shop.setShopDesc("测试更新");
        shop.setShopAddr("测试更新");
        shop.setLastEditTime(new Date());
        int effectedNum = shopDao.updateShop(shop);
        assertEquals(1, effectedNum);
    }

    @Test
    public void testQueryByShopId() {
        long shopId = 36;
        Shop shopTest = shopDao.queryByShopId(shopId);
        System.out.println(shopTest.getArea().getAreaName());
        System.out.println(shopTest.getArea().getAreaId());

    }

    @Test
    public void testQueryShopListAndCount() {
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shopCondition.setOwner(owner);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 5);
        System.out.println(shopList.size());
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println(count);
        Area area = new Area();
        area.setAreaId(3);
        shopCondition.setArea(area);
        List<Shop> xinshopList = shopDao.queryShopList(shopCondition, 0, 2);
        int xincount = shopDao.queryShopCount(shopCondition);
        System.out.println("xin: " + xinshopList.size());
        System.out.println("xin: " + xincount);
    }
}
