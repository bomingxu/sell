package com.xbm.order.vo;

import lombok.Data;

@Data
public class ResultVo<Object> {

    private int code;

    private String msg;

    private Object data;
}
