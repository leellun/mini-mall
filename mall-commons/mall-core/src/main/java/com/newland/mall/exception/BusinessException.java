

package com.newland.mall.exception;

import com.newland.mall.enumeration.ResultCode;

public class BusinessException extends RuntimeException {
    private Integer code = ResultCode.ERROR.getCode();

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
