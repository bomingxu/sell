package com.xbm.order.service.iml;

import com.xbm.order.dao.OrderDetailRepository;
import com.xbm.order.dao.OrderMasterRepository;
import com.xbm.order.dto.OrderDTO;
import com.xbm.order.enums.OrderStatusEnum;
import com.xbm.order.enums.PayStatusEnum;
import com.xbm.order.model.OrderDetail;
import com.xbm.order.model.OrderMaster;
import com.xbm.order.service.OrderService;
import com.xbm.product.client.ProductClient;
import com.xbm.sell.common.enums.ResultEnum;
import com.xbm.sell.common.exception.SellException;
import com.xbm.sell.common.model.DescreaseStockInput;
import com.xbm.sell.common.model.ProductInfoOutput;
import com.xbm.sell.common.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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

    @Transactional
    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        String orderId = KeyUtil.getUniqueKey();
        //1.查询商品信息
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        List<String> productIdList = orderDetailList.stream()
                .map(orderDetail->orderDetail.getProductId())
                .collect(Collectors.toList());
        List<ProductInfoOutput> productInfoList = productClient.listForOrder(productIdList);
        //2.计算总价
        BigDecimal totalPrice = new BigDecimal(0);

        for(OrderDetail orderDetail : orderDetailList){
            for(ProductInfoOutput productInfo : productInfoList){
                if(orderDetail.getProductId().equals(productInfo.getProductId())){
                    Integer productQuantity = orderDetail.getProductQuantity();
                    totalPrice = totalPrice.add(productInfo.getProductPrice().multiply(new BigDecimal(productQuantity)));
                    //订单详情入库
                    BeanUtils.copyProperties(productInfo,orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.getUniqueKey());
                    orderDetail.setProductQuantity(productQuantity);
                    orderDetail.setCreateTime(new Date());
                    orderDetail.setUpdateTime(new Date());
                    orderDetailRepository.save(orderDetail);
                }
            }
        }
        //3.扣库存
        List<DescreaseStockInput> cartDTOList = orderDetailList.stream()
                .map(orderDetail -> new DescreaseStockInput(orderDetail.getProductId(),orderDetail.getProductQuantity()))
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

    @Transactional
    @Override
    public OrderDTO finishOrder(String orderId) {
        //1.查询订单，判断订单是否存在
        Optional<OrderMaster> orderMasterOptional = orderMasterRepository.findById(orderId);
        if(!orderMasterOptional.isPresent()){
            throw new SellException(ResultEnum.ORDER_NOT_EXIT);
        }
        //2.判断订单状态是否为新建订单
        OrderMaster orderMaster = orderMasterOptional.get();
        if(OrderStatusEnum.NEW_ORDER.getCode() != orderMaster.getOrderStatus()){
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //3.修改订单状态，改为已完成
        orderMaster.setOrderStatus(OrderStatusEnum.DONE_ORDER.getCode());
        orderMasterRepository.save(orderMaster);

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        //查询订单详情
        List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIT);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
