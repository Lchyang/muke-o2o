package com.imooc.o2o.service;

import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;

import java.io.InputStream;

public interface ShopService {
    ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName);

    Shop getByShopId(long shopId);

    ShopExecution modifyShop(Shop shop, InputStream  shopImgInputStream, String fileName);

    ShopExecution queryShopList(Shop shopCondition, int pageIndex, int pageSize);
}
