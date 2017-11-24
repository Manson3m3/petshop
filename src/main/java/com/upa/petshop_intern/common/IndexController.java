package com.upa.petshop_intern.common;

import com.upa.petshop_intern.common.response.BaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liang.zhou on 2017/7/13.
 */
@RestController
public class IndexController {

    @RequestMapping("/")
    public BaseResponse index() {
        return new BaseResponse();
    }
}
