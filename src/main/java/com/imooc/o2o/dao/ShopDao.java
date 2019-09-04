package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {

    /**
     * 通过shopid查询店铺
     * @param shopId 店铺id
     */
    Shop queryByShopId(long shopId);

    /**
     * 新增店铺
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺
     */
    int updateShop(Shop shop);

    /**
     * @param shopCondition 分页查询店铺列表（店铺名称，店铺类别，区域id, owner, 店铺状态）
     * @param rowIndex 从第几行开始取
     * @param pageSize 每页条目
     * @return 店铺列表
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex,
    @Param("pageSize") int pageSize);

    /**
     * 查询店铺数量
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);

}
