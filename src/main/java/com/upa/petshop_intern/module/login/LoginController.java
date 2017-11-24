package com.upa.petshop_intern.module.login;

import com.upa.petshop_intern.common.BaseRequest;
import com.upa.petshop_intern.common.response.BaseResponse;
import com.upa.petshop_intern.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Yunhao.Cao on 2017/10/10.
 */
@RequestMapping(value = "/login")
@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse<LoginData> login(@RequestBody BaseRequest<LoginForm> request, HttpSession httpSession, HttpServletResponse httpServletResponse, HttpServletRequest httpRequest) throws Exception {
        return new BaseResponse<>(loginService.login(request.getData(), httpSession, httpServletResponse, httpRequest));
    }
}
