package com.xbm.product.dao;

import com.xbm.product.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

    List<ProductCategory> findAllByCategoryTypeIn(List<Integer> categoryTypeList);

}
