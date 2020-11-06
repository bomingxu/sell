package com.xbm.product.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PRODUCT_NOT_EXIT(1,"商品不存在"),
    STOCT_NOT_ENOUGHT(2,"商品库存不足")
    ;


    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
