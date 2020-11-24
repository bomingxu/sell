package com.xbm.sell.common.vo;

import lombok.Data;

@Data
public class ResultVo<Object> {

    private int code;

    private String msg;

    private Object data;
}
