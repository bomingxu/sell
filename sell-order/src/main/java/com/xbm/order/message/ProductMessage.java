package com.xbm.order.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xbm.sell.common.model.ProductInfoOutput;
import com.xbm.sell.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProductMessage {

    private final static String PRODUCT_STOCK_TEMPLATE = "product_stock_%s";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message){

        List<ProductInfoOutput> productInfoOutputList = (List<ProductInfoOutput>) JsonUtil.fromJson(message, new TypeReference<List<ProductInfoOutput>>() {});

        for (ProductInfoOutput productInfoOutput : productInfoOutputList) {
            log.info("ProductMessage---->process--->productInfoOutput:{}",productInfoOutput);
            stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE,productInfoOutput.getProductId()),
                    String.valueOf(productInfoOutput.getProductStock()));
        }

    }

}
