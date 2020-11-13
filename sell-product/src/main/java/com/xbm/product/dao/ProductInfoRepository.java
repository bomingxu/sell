package com.xbm.product.dao;

import com.xbm.product.model.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {

    List<ProductInfo> findByProductStatus(int productStatus );

    List<ProductInfo> findByProductIdIn(List<String> productIdList);

}
