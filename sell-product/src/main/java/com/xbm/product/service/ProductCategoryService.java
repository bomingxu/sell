package com.xbm.product.service;

import com.xbm.product.model.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    List<ProductCategory> findListByCategoryTypeIn(List<Integer> categoryTypeList);

}
