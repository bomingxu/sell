package com.xbm.product.client;

import com.xbm.sell.common.model.DescreaseStockInput;
import com.xbm.sell.common.model.ProductInfoOutput;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductClientFallBack implements ProductClient{
    @Override
    public List<ProductInfoOutput> listForOrder(List<String> productIdList) {
        return null;
    }

    @Override
    public void descreaseStock(List<DescreaseStockInput> descreaseStockInputList) {
    }
}
