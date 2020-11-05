package com.xbm.product.service.impl;

import com.xbm.product.dao.ProductInfoRepository;
import com.xbm.product.enums.ProductStatus;
import com.xbm.product.model.ProductInfo;
import com.xbm.product.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findAllByProductStatus(ProductStatus.UP.getCode());
    }
}
