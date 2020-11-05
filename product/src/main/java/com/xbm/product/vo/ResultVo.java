package com.xbm.product.vo;

import lombok.Data;

@Data
public class ResultVo<Object> {

    private int code;

    private String msg;

    private Object data;
}
