package com.xbm.sell.common.exception;


import com.xbm.sell.common.enums.ResultEnum;

public class SellException extends RuntimeException{

    private Integer code;

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
