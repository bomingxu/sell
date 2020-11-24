package com.xbm.order.service;

import com.xbm.order.dto.OrderDTO;

public interface OrderService {

    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    OrderDTO createOrder(OrderDTO orderDTO);

    /**
     * 结束订单
     * @param orderId
     * @return
     */
    OrderDTO finishOrder(String orderId);

}
