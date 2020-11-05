package com.xbm.order.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PARAMS_ERROR(1,"参数异常"),
    CART_EMPTY(2,"购物车为空"),

    ;


    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
