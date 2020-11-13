package com.xbm.product.service.impl;

import com.xbm.product.common.DescreaseStockInput;
import com.xbm.product.dao.ProductInfoRepository;
import com.xbm.product.enums.ProductStatus;
import com.xbm.product.enums.ResultEnum;
import com.xbm.product.exception.ProductException;
import com.xbm.product.model.ProductInfo;
import com.xbm.product.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatus.UP.getCode());
    }

    @Override
    public List<ProductInfo> findListByProductIdIn(List<String> productIdList) {
        return productInfoRepository.findByProductIdIn(productIdList);
    }

    /**
     * 扣库存
     * @param cartDTOList
     */
    @Override
    @Transactional
    public void decreaseStock(List<DescreaseStockInput> cartDTOList) {
        for(DescreaseStockInput cartDTO : cartDTOList){

            Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(cartDTO.getProductId());
            //判断商品是否存在
            if(!productInfoOptional.isPresent()){
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIT);
            }
            ProductInfo productInfo = productInfoOptional.get();
            //判断库存够不够
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(result < 0){
                throw new ProductException(ResultEnum.STOCT_NOT_ENOUGHT);
            }
            //扣库存
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
        }
    }
}
