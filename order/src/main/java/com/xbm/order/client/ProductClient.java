package com.xbm.order.client;

import com.xbm.order.dto.CartDTO;
import com.xbm.order.model.ProductInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("product")
public interface ProductClient {

    @PostMapping("/productController/listForOrder")
    List<ProductInfo> listForOrder(List<String> productIdList);

    @PostMapping("/productController/descreaseStock")
    void descreaseStock(@RequestBody List<CartDTO> cartDTOList);

}