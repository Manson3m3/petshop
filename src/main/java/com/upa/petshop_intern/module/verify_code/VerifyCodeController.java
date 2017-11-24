package com.upa.petshop_intern.module.verify_code;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.upa.petshop_intern.common.BaseRequest;
import com.upa.petshop_intern.common.exception.ErrorCode;
import com.upa.petshop_intern.common.exception.WebBackendException;
import com.upa.petshop_intern.common.util.VerifyCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * Created by Yunhao.Cao on 2017/10/10.
 */
@RequestMapping(value = "/verifycode")
@RestController
public class VerifyCodeController {

    @Autowired
    VerifyCodeService verifyCodeService;

    @RequestMapping(method = RequestMethod.GET)
    public void getVerifyCode(@RequestParam String verifyToken, HttpServletResponse response) throws Exception {
        if (verifyToken == null) {
            throw new WebBackendException(ErrorCode.VERIFY_CODE_NEED_TOKEN);
        }

        String createVerifyCode = verifyCodeService.setVerifyCodeImage(response);

        verifyCodeService.addVerifyRecord(createVerifyCode, verifyToken);
    }
}
