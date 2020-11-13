package com.xbm.order.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    NEW_ORDER(0,"新建订单"),
    DONE_ORDER(1,"已完成订单")
    ;

    private Integer code;

    private String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
