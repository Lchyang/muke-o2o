package com.imooc.o2o.web.shopadmin;

import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.dto.Result;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProdcutCategoryStateEnum;
import com.imooc.o2o.exceptions.ProductCategoryOperationException;
import com.imooc.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/shopadmin")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @RequestMapping(value = "/getproductcategorylist", method = {RequestMethod.GET})
    @ResponseBody
    private Result<List<ProductCategory>> queryProductCategoryList(HttpServletRequest request) {
        Shop currentshop = (Shop) request.getSession().getAttribute("currentShop");
        if (currentshop != null && currentshop.getShopId() > 0) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setShopId(currentshop.getShopId());
            List<ProductCategory> productCategoryList = productCategoryService.queryProductCategory(productCategory);
            return new Result<>(true, productCategoryList);
        } else {
            ProdcutCategoryStateEnum ps = ProdcutCategoryStateEnum.INNER_ERROR;
            return new Result<>(false, ps.getStateInfo(), ps.getState());
        }
    }


    @RequestMapping(value = "addproductcategorys", method = {RequestMethod.POST})
    @ResponseBody
    private Map<String, Object> addProductCategory(@RequestBody List<ProductCategory> productCategoryList,
                                                   HttpServletRequest request) {
        //1.获取当前店铺shopid
        //2.循环插入到批量添加店铺中
        //3.插入店铺
        Map<String, Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        for (ProductCategory pc : productCategoryList) {
            pc.setShopId(currentShop.getShopId());
        }
        if (productCategoryList.size() > 0) {
            try {
                ProductCategoryExecution pe = productCategoryService.batchInsertProductCategory(productCategoryList);
                if (pe.getState() == ProdcutCategoryStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (Exception e) {
                throw new ProductCategoryOperationException("添加店铺失败");
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "商品类别列表为空");
        }
        return modelMap;
    }

    @RequestMapping(value = "removeproductcategory", method = {RequestMethod.POST})
    @ResponseBody
    private Map<String, Object> addProductCategory(Long productCategoryId,
                                                   HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        ProductCategory productCategory = new ProductCategory();
        if (productCategoryId != null && productCategoryId > 0) {
            productCategory.setShopId(currentShop.getShopId());
            productCategory.setProductCategoryId(productCategoryId);
            try {
                ProductCategoryExecution pe = productCategoryService.batchDeleteProductCategory(productCategory);
                if (pe.getState() == ProdcutCategoryStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductCategoryOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "商品类别列表为空");
        }
        return modelMap;
    }
}
