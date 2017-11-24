package com.upa.petshop_intern.common.exception;


import com.upa.petshop_intern.common.util.CommonUtil;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 错误控制器
 * 出错时显示错误页面
 *
 * @author liang.zhou
 */
@ControllerAdvice
public class ExceptionConfig {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseExceptionResponse defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        e.printStackTrace();
        //参数无法转换错误
        if (e instanceof HttpMessageNotReadableException) {
            return new BaseExceptionResponse(ErrorCode.NOT_VALID_PARAM);
        }
        //校验错误则返回校验错误
        if (e instanceof MethodArgumentNotValidException) {
            try {
                //调用共通处理方法
                CommonUtil.handleValidation(((MethodArgumentNotValidException) e).getBindingResult());
            } catch (WebBackendException paramException) {
                return new BaseExceptionResponse(paramException);
            }
        }
        //若有后台定义的错误码则按该错误码返回
        if (e instanceof WebBackendException) {
            return new BaseExceptionResponse((WebBackendException) e);
        } else {
            //其他
            return new BaseExceptionResponse(ErrorCode.OTHER);
        }
    }
}
