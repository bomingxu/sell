package com.xbm.product.controller;

import com.xbm.product.model.ProductCategory;
import com.xbm.product.model.ProductInfo;
import com.xbm.product.service.ProductCategoryService;
import com.xbm.product.service.ProductInfoService;
import com.xbm.product.utils.ResultVoUtil;
import com.xbm.product.vo.ProductInfoVo;
import com.xbm.product.vo.ProductVo;
import com.xbm.product.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
@Api(tags = "商品相关接口")
public class ProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 1.查询商品列表
     * 2.获取类目type列表
     * 3.查询类目列表
     * 4.构造数据
     */
    @ApiOperation("查询在架商品列表")
    @GetMapping("/list")
    public ResultVo<ProductVo> list(){
        //1.查询商品列表
        List<ProductInfo> productInfoList = productInfoService.findUpAll();
        //2.获取类目type列表
        List<Integer> categoryTypeList = productInfoList.stream().map(ProductInfo::getCategoryType).collect(Collectors.toList());
        //3.查询类目列表
        List<ProductCategory> categoryList = productCategoryService.findListByCategoryTypeIn(categoryTypeList);
        //4.构造数据
        List<ProductVo> productVoList = new ArrayList<>();
        for(ProductCategory productCategory : categoryList){
            ProductVo productVo = new ProductVo();
            productVo.setCategoryName(productCategory.getCategoryName());
            productVo.setCategoryType(productCategory.getCategoryType());
            List<ProductInfoVo> productInfoVoList = new ArrayList<>();
            for(ProductInfo productInfo : productInfoList){
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo,productInfoVo);
                    productInfoVoList.add(productInfoVo);
                }
            }
            productVo.setProductInfoVoList(productInfoVoList);
            productVoList.add(productVo);
        }
        return ResultVoUtil.success(productVoList);

    }
}
