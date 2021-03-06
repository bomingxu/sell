package com.xbm.product.service;

import com.xbm.product.common.DescreaseStockInput;
import com.xbm.product.model.ProductInfo;

import java.util.List;

public interface ProductInfoService {

    List<ProductInfo> findUpAll();

    List<ProductInfo> findListByProductIdIn(List<String> productIdList);

    void decreaseStock(List<DescreaseStockInput> cartDTOList);
}
