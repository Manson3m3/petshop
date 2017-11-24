package com.upa.petshop_intern.common.security;


import com.upa.petshop_intern.common.util.HttpUtil;
import com.upa.petshop_intern.common.util.SignUtil;
import com.upa.petshop_intern.entity.User;
import com.upa.petshop_intern.entity.UserNonce;
import com.upa.petshop_intern.entity.UserToken;
import com.upa.petshop_intern.module.login.LoginService;
import com.upa.petshop_intern.module.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 随机数拦截器
 */
@Component
public class DigestInterceptor extends HandlerInterceptorAdapter {

    final Logger API_LOGGER = LoggerFactory.getLogger("digest");

    @Autowired
    UserService userService;
    @Autowired
    LoginService loginService;

    /**
     * 在访问接口之前的拦截操作：验证用户的随机数
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        // 预检请求不触发业务逻辑
        if (request.getMethod().equals(RequestMethod.OPTIONS.toString())) {
            return false;
        }

        try {
            // 获取请求中的验证信息
            RequestAuthorization requestAuthorization = new RequestAuthorization(request);
            // 获取数据库中的随机数
            UserNonce userNonce = loginService.findUserNonceByUserId(requestAuthorization.getUserId());

            // 过期则写入新随机数
            if(userNonce.getOverdueTime().before(new Date())) {
                userNonce = loginService.updateUserNonce(requestAuthorization.getUserId(), request.getRequestURI());
            }
            // 校验随机数和签名
            if (checkNonceAndSignature(userNonce, requestAuthorization)){
                if(userNonce.getNonce().equals(requestAuthorization.getNonce())){
                    request.setAttribute("nonce", requestAuthorization.getNonce());
                    SignUtil.writeAuthorization(response, userNonce.getNonce());
                    return true;
                } else {
                    SignUtil.write401StaleResponse(response, userNonce.getNonce());
                    return false;
                }
            }

        } catch (Exception e) {
            // 校验错误则记录访问者访问的信息
            API_LOGGER.error("authorization error ip:{} target:{} authorization:{}", HttpUtil.getRequestIp(request), request.getServletPath(), request.getHeader("Authorization"));
        }

        // 校验不通过时写入401返回头
        SignUtil.write401Response(response);
        return false;
    }


    /**
     * 在访问接口之后的拦截操作：
     */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    /**
     * 整个请求结束后的操作
     *
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
    throws Exception {

    }

    /**
     * 检验发送的随机数和签名
     */
    private boolean checkNonceAndSignature(UserNonce userNonce, RequestAuthorization requestAuthorization) throws Exception {
        String sign = requestAuthorization.getSign();
        Long userId = requestAuthorization.getUserId();

        if (userNonce == null) {
            return false;
        }
        // 判断token
        User user = userService.findUserById(userId);
        if (user == null) {
            return false;
        }
        UserToken userToken = loginService.findUserTokenByUserId(userId);
        // token过期？
        if (userToken.getOverdueTime().before(new Date())) {
            return false;
        }
        // 判断sign
        if (!sign.equals(SignUtil.generateSign(userId.toString(), requestAuthorization.getNonce(), userToken.getUserToken()))) {
            return false;
        }
        return true;
    }
}
