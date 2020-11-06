package com.xbm.order.controller;

import com.xbm.order.client.ProductClient;
import com.xbm.order.model.ProductInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "客户端相关接口")
@RestController
@RequestMapping("/clientController")
public class ClientController {

    @Autowired
    ProductClient productClient;

    @ApiOperation(value = "商品列表",notes = "商品列表")
    @PostMapping("/getProductInfoList")
    public List<ProductInfo> getProductInfoList(@RequestBody List<String> productIdList){
        return productClient.listForOrder(productIdList);
    }

}
