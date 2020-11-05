package com.xbm.order.service.iml;

import com.xbm.order.dao.OrderDetailRepository;
import com.xbm.order.dao.OrderMasterRepository;
import com.xbm.order.dto.OrderDTO;
import com.xbm.order.enums.OrderStatusEnum;
import com.xbm.order.enums.PayStatusEnum;
import com.xbm.order.model.OrderDetail;
import com.xbm.order.model.OrderMaster;
import com.xbm.order.service.OrderService;
import com.xbm.order.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    RestTemplate RestTemplate;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        //TODO 1.查询商品信息

        //TODO 2.计算总价

        //TODO 3.扣库存

        //4.订单信息入库
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderId(KeyUtil.getUniqueKey());
        orderMaster.setOrderAmount(new BigDecimal(5));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW_ORDER.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT_PAY.getCode());
        orderMaster.setCreateTime(new Date());
        orderMaster.setUpdateTime(new Date());
        orderMasterRepository.save(orderMaster);
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        for(OrderDetail orderDetail : orderDetailList){
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderMaster.getOrderId());
        }

        orderDTO.setOrderId(orderMaster.getOrderId());
        return orderDTO;
    }
}
