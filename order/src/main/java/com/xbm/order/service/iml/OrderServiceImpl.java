package com.xbm.order.service.iml;

import com.xbm.order.client.ProductClient;
import com.xbm.order.dao.OrderDetailRepository;
import com.xbm.order.dao.OrderMasterRepository;
import com.xbm.order.dto.CartDTO;
import com.xbm.order.dto.OrderDTO;
import com.xbm.order.enums.OrderStatusEnum;
import com.xbm.order.enums.PayStatusEnum;
import com.xbm.order.model.OrderDetail;
import com.xbm.order.model.OrderMaster;
import com.xbm.order.model.ProductInfo;
import com.xbm.order.service.OrderService;
import com.xbm.order.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    RestTemplate RestTemplate;

    @Autowired
    private ProductClient productClient;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        String orderId = KeyUtil.getUniqueKey();
        //1.查询商品信息
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        List<String> productIdList = orderDetailList.stream()
                .map(orderDetail->orderDetail.getProductId())
                .collect(Collectors.toList());
        List<ProductInfo> productInfoList = productClient.listForOrder(productIdList);
        //2.计算总价
        BigDecimal totalPrice = new BigDecimal(0);

        for(OrderDetail orderDetail : orderDetailList){
            for(ProductInfo productInfo : productInfoList){
                if(orderDetail.getProductId().equals(productInfo.getProductId())){
                    Integer productQuantity = orderDetail.getProductQuantity();
                    totalPrice = totalPrice.add(productInfo.getProductPrice().multiply(new BigDecimal(productQuantity)));
                    //订单详情入库
                    BeanUtils.copyProperties(productInfo,orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.getUniqueKey());
                    orderDetail.setProductQuantity(productQuantity);
                    orderDetail.setCreateTime(new Date());
                    orderDetailRepository.save(orderDetail);
                }
            }
        }
        //3.扣库存
        List<CartDTO> cartDTOList = orderDetailList.stream()
                .map(orderDetail -> new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.descreaseStock(cartDTOList);
        //4.订单信息入库
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(totalPrice);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW_ORDER.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT_PAY.getCode());
        orderMaster.setCreateTime(new Date());
        orderMaster.setUpdateTime(new Date());
        orderMasterRepository.save(orderMaster);

        orderDTO.setOrderId(orderMaster.getOrderId());
        return orderDTO;
    }
}
