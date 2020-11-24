package com.xbm.product.service.impl;

import com.xbm.sell.common.model.DescreaseStockInput;
import com.xbm.sell.common.model.ProductInfoOutput;
import com.xbm.sell.common.utils.JsonUtil;
import com.xbm.product.dao.ProductInfoRepository;
import com.xbm.product.enums.ProductStatus;
import com.xbm.product.enums.ResultEnum;
import com.xbm.product.exception.ProductException;
import com.xbm.product.model.ProductInfo;
import com.xbm.product.service.ProductInfoService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private AmqpTemplate amqpTemplate;

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
    public void decreaseStock(List<DescreaseStockInput> cartDTOList) {
        List<ProductInfo> productInfoList = decreaseStockProcess(cartDTOList);

        List<ProductInfoOutput> productInfoOutputList = productInfoList.stream().map(e -> {
            ProductInfoOutput productInfoOutput = new ProductInfoOutput();
            BeanUtils.copyProperties(e,productInfoOutput);
            return productInfoOutput;
        }).collect(Collectors.toList());

        //发送消息队列
        amqpTemplate.convertAndSend("productInfo", JsonUtil.toJson(productInfoOutputList));
    }

    @Transactional
    public List<ProductInfo> decreaseStockProcess(List<DescreaseStockInput> cartDTOList) {
        List<ProductInfo> productInfoList = new ArrayList<>();
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

            productInfoList.add(productInfo);
        }
        return productInfoList;
    }
}
