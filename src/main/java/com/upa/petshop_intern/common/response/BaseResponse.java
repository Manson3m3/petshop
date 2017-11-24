package com.upa.petshop_intern.common.response;


import com.upa.petshop_intern.common.exception.ErrorCode;

/**
 * 基本返回类，包含错误码
 *
 * @author liang.zhou
 */
public class BaseResponse<T> {
    protected T data;
    private int errorCode = 0;
    private String errorInfo = "";

    public BaseResponse() {
    }

    public BaseResponse(int errorCode, String errorInfo) {
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
    }

    public BaseResponse(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public void setError(ErrorCode errorCode) {
        this.errorCode = errorCode.getErrorCode();
        this.errorInfo = errorCode.getErrorInfo();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
