package com.xbm.order.form;

import lombok.Getter;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
public class OrderForm {

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空")
    private String name;

    /**
     * 手机号
     */
    @NotEmpty(message = "用户手机号不能为空")
    private String phone;

    /**
     * 地址
     */
    @NotEmpty(message = "用户收货地址不能为空")
    private String address;

    /**
     * 用户的openId
     */
    @NotEmpty(message = "用户openId不能为空")
    private String openid;

    /**
     * 购物车
     */
    @NotEmpty(message = "购物车不能为空")
    private String items;

}
