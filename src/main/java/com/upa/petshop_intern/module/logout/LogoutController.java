package com.upa.petshop_intern.module.logout;

import com.upa.petshop_intern.common.response.BaseResponse;
import com.upa.petshop_intern.entity.User;
import com.upa.petshop_intern.module.login.LoginData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Yunhao.Cao on 2017/10/19.
 */
@RequestMapping("/logout")
@RestController
public class LogoutController {

    @Autowired
    LogoutService logoutService;

    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse logout(@RequestAttribute("user") User user) throws Exception {
        logoutService.logout(user.getId());
        return new BaseResponse<>();
    }
}
