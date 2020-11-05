package com.xbm.order.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnum {

    HAS_PAY(1,"已支付"),
    WAIT_PAY(0,"未支付")
    ;

    private Integer code;

    private String msg;

    PayStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
