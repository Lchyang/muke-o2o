package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dao.ProductImgDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductImg;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exceptions.ProductOperationException;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;

    @Autowired
    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Autowired
    private ProductImgDao productImgDao;

    /**
     * @param product         商品
     * @param image           缩略图
     * @param imageHolderList 展示图
     */
    @Override
    public ProductExecution addProduct(Product product, ImageHolder image, List<ImageHolder> imageHolderList) {
        //空值判断
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            //0下架，1上架
            product.setEnableStatus(1);
            if (image != null) {
                addImage(product, image);
            }
            try {
                int effectNum = productDao.insertProduct(product);
                if (effectNum < 0) {
                    throw new ProductOperationException("添加商品失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("添加商品失败" + e.toString());
            }
            if (imageHolderList != null && imageHolderList.size() > 0) {
                addProductImgList(product, imageHolderList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    private void addImage(Product product, ImageHolder image) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String imagePath = ImageUtil.GenerateThumbnail(image, dest);
        product.setImgAddr(imagePath);
    }

    /**
     * 批量添加商品详情图片
     *
     * @param product         商品
     * @param imageHolderList 图片信息列表
     */
    private void addProductImgList(Product product, List<ImageHolder> imageHolderList) {
        List<ProductImg> productImgList = new ArrayList<>();
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        for (ImageHolder im : imageHolderList) {
            String imageAddr = ImageUtil.GenerateImage(im, dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imageAddr);
            productImg.setCreateTime(new Date());
            productImg.setProductId(product.getProductId());
            productImgList.add(productImg);
        }
        if (productImgList.size() > 0) {
            try {
                int effectNum = productImgDao.insertProductImg(productImgList);
                if (effectNum <= 0) {
                    throw new ProductOperationException("创建图片详情失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建图片详情失败" + e.toString());
            }
        }

    }
}
