package com.upa.petshop_intern.common.exception;


import com.upa.petshop_intern.common.response.BaseResponse;

/**
 * 异常时返回
 *
 * @author liang.zhou
 */
public class BaseExceptionResponse extends BaseResponse {
    private String errorInfo;

    public BaseExceptionResponse(WebBackendException e) {
        this.setErrorCode(e.getErrorCode());
        this.errorInfo = e.getErrorInfo();
    }

    public BaseExceptionResponse(ErrorCode e) {
        this.setErrorCode(e.getErrorCode());
        this.errorInfo = e.getErrorInfo();
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }
}
