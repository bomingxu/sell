package com.xbm.order.controller;

import com.xbm.order.convert.OrderForm2OrderDTO;
import com.xbm.order.dto.OrderDTO;
import com.xbm.order.enums.ResultEnum;
import com.xbm.order.exception.OrderException;
import com.xbm.order.form.OrderForm;
import com.xbm.order.service.OrderService;
import com.xbm.order.utils.ResultVoUtil;
import com.xbm.order.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResultVo createOrder(@Valid @RequestBody OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确,orderForm={}",orderForm);
            throw new OrderException(ResultEnum.PARAMS_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        //参数转换
        OrderDTO orderDTO = OrderForm2OrderDTO.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】购物车为空");
            throw new OrderException(ResultEnum.CART_EMPTY);
        }
        //创建订单
        OrderDTO result = orderService.createOrder(orderDTO);
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("orderId",result.getOrderId());
        return ResultVoUtil.success(resultMap);
    }

}
