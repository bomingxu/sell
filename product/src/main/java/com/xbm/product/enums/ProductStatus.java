package com.xbm.product.enums;

import lombok.Getter;

/**
 * 商品上下架状态
 */
@Getter
public enum ProductStatus {
    UP(0,"在架"),
    DOWN(1,"下架")
    ;


    private int code;

    private String msg;

    ProductStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
