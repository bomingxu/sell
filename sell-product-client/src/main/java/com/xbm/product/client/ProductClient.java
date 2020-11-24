package com.xbm.product.client;


import com.xbm.sell.common.model.DescreaseStockInput;
import com.xbm.sell.common.model.ProductInfoOutput;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("product")
public interface ProductClient {

    @PostMapping("/productController/listForOrder")
    List<ProductInfoOutput> listForOrder(List<String> productIdList);

    @PostMapping("/productController/descreaseStock")
    void descreaseStock(@RequestBody List<DescreaseStockInput> descreaseStockInputList);

}
