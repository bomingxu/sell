package com.xbm.sell.common.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PARAMS_ERROR(1,"参数异常"),
    CART_EMPTY(2,"购物车为空"),

    USER_NOT_EXIT(4001,"用户不存在"),
    USER_ROLE_ERROR(4002,"用户类型错误"),

    ORDER_NOT_EXIT(4003,"订单不存在"),
    ORDER_STATUS_ERROR(4004,"订单状态错误"),
    ORDER_DETAIL_NOT_EXIT(4005,"订单详情不存在")
    ;


    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
