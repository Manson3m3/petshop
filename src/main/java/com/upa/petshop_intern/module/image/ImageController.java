package com.upa.petshop_intern.module.image;

import com.upa.petshop_intern.common.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Yunhao.Cao on 2017/10/11.
 */
@RequestMapping(value = "/images")
@RestController
public class ImageController {

    @Autowired
    ImageService imageService;

    /**
     * 获取商品图片
     */
    @RequestMapping(value = "/commodities/{id}", method = RequestMethod.GET)
    public void findCommodityImage(@PathVariable Long id, HttpServletResponse httpServletResponse) throws Exception {
        imageService.findCommodityImage(id, httpServletResponse);
    }

    /**
     * 上传商品图片
     */
    @RequestMapping(value = "/commodities/{id}", method = RequestMethod.POST)
    public BaseResponse addCommodityImage(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws Exception {
        imageService.addCommodityImage(file, id);
        return new BaseResponse();
    }

    /**
     * 获取用户头像
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public BaseResponse findUserImage(@PathVariable Long id, HttpServletResponse httpServletResponse) throws Exception {
        imageService.findUserImage(id ,httpServletResponse);
        return new BaseResponse();
    }

    /**
     * 上传用户头像
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
    public BaseResponse addUserImage(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws Exception {
        imageService.addUserImage(file, id);
        return new BaseResponse();
    }
}
