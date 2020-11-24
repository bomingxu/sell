package com.xbm.order.convert;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xbm.order.dto.OrderDTO;
import com.xbm.order.form.OrderForm;
import com.xbm.order.model.OrderDetail;
import com.xbm.sell.common.enums.ResultEnum;
import com.xbm.sell.common.exception.SellException;

import java.util.ArrayList;
import java.util.List;

public class OrderForm2OrderDTO {

    public static OrderDTO convert(OrderForm orderForm){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        orderDTO.setBuyerPhone(orderForm.getPhone());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        Gson gson = new Gson();
        try{
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e){
            System.out.println("OrderForm2OrderDTO---->convert--->参数转换异常"+orderForm.getItems());
            throw new SellException(ResultEnum.PARAMS_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

}
