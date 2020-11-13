package com.xbm.product.service.impl;

import com.xbm.product.dao.ProductCategoryRepository;
import com.xbm.product.model.ProductCategory;
import com.xbm.product.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductCategory> findListByCategoryTypeIn(List<Integer> categoryTypeList) {
        return productCategoryRepository.findAllByCategoryTypeIn(categoryTypeList);
    }
}
