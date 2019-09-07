package com.imooc.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exceptions.ProductCategoryOperationException;
import com.imooc.o2o.service.ProductCategoryService;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/shopadmin")
public class ProductController {
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
    private static final int IMAGEMAXCOUNT = 6;

    @Autowired
    public ProductController(ProductService productService, ProductCategoryService productCategoryService) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
    }

    @RequestMapping(value = "/addproduct", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //TODO短信验证码
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误");
            return modelMap;
        }
        // 接受前端数据参数初始化
        Product product = new Product();
        ImageHolder imageHolder = new ImageHolder();
        List<ImageHolder> imageHolderList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        // 获取商品数据
        try {
            String productStr = HttpServletRequestUtil.getString(request, "productStr");
            product = mapper.readValue(productStr, product.getClass());
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        // 获取图片数据
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getServletContext());
        try {
            if (commonsMultipartResolver.isMultipart(request)) {
                imageHolder = handleImage(request, imageHolder, imageHolderList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        //传入当前店铺数据
        Shop shop = (Shop) request.getSession().getAttribute("currentShop");
        product.setShop(shop);

        //添加商品
        if (imageHolder != null && imageHolderList.size() > 0) {
            try {
                ProductExecution pe = productService.addProduct(product, imageHolder, imageHolderList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
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
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    /**
     * 处理商品缩略图和详情图片
     */
    private ImageHolder handleImage(HttpServletRequest request, ImageHolder
            imageHolder, List<ImageHolder> imageHolderList) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        CommonsMultipartFile imageFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
        if (imageFile != null) {
            try {
                imageHolder = new ImageHolder(imageFile.getOriginalFilename(), imageFile.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //循环处理多个图片
        for (int i = 0; i < IMAGEMAXCOUNT; i++) {
            CommonsMultipartFile productImageFile = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
            if (productImageFile != null) {
                ImageHolder productImage = null;
                try {
                    productImage = new ImageHolder(productImageFile.getOriginalFilename(), productImageFile.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageHolderList.add(productImage);
            } else {
                break;
            }
        }
        return imageHolder;
    }

    @RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
    public Map<String, Object> modifyProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
        //短信验证码
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误");
            return modelMap;
        }

        // 接受前端数据参数初始化
        Product product = new Product();
        ImageHolder imageHolder = new ImageHolder();
        List<ImageHolder> imageHolderList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        // 获取商品数据
        try {
            String productStr = HttpServletRequestUtil.getString(request, "productStr");
            product = mapper.readValue(productStr, product.getClass());
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        // 获取图片数据
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getServletContext());
        try {
            if (commonsMultipartResolver.isMultipart(request)) {
                imageHolder = handleImage(request, imageHolder, imageHolderList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        //添加商品
        if (product != null) {
            try {
                //传入当前店铺数据
                Shop shop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(shop);
                ProductExecution pe = productService.updateProduct(product, imageHolder, imageHolderList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
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
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    @RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProductById(@RequestParam Long productId) {
        Map<String, Object> modelMap = new HashMap<>();
        if (productId > -1) {
            Product product = productService.getProductById(productId);
            ProductCategory productCategory = new ProductCategory();
            productCategory.setShopId(product.getShop().getShopId());
            List<ProductCategory> productCategoryList = productCategoryService.queryProductCategory(productCategory);
            modelMap.put("product", product);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty productId");
        }
        return modelMap;
    }
}
