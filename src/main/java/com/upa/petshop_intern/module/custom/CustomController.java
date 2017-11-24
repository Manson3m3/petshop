package com.upa.petshop_intern.module.custom;

import com.upa.petshop_intern.common.BaseRequest;
import com.upa.petshop_intern.common.page.PageParams;
import com.upa.petshop_intern.common.page.SearchPageParams;
import com.upa.petshop_intern.common.response.BaseResponse;
import com.upa.petshop_intern.common.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by jie.yao on 2017/7/18.
 */
@RestController
@RequestMapping("/customs")
public class CustomController {

    @Autowired
    CustomService customService;

    /**
     * 获取顾客列表
     */
    @RequestMapping(method = RequestMethod.GET)
    public PageResponse<CustomData> findCustoms(PageParams pageParams) throws Exception {
        return new PageResponse<>(customService.findCustoms(pageParams.getPageRequest()));
    }

    /**
     * 搜索顾客，返回列表
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public PageResponse<CustomData> searchCustoms(SearchPageParams searchPageParams) throws Exception {
        return new PageResponse<>(customService.searchByCustomName(searchPageParams.getQ(), searchPageParams.getPageRequest()));
    }

    /**
     * 获取顾客
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BaseResponse<CustomData> findCustom(@PathVariable Long id) throws Exception {
        return new BaseResponse<>(customService.findCustomById(id));
    }

    /**
     * 增加顾客
     */
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse<CustomData> addCustom(@RequestBody BaseRequest<CustomFormWithPassword> request) throws Exception {
        return new BaseResponse<>(customService.addCustom(request.getData()));
    }

    /**
     * 修改顾客, 不包括密码
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public BaseResponse<CustomData> updateCustom(@RequestBody BaseRequest<CustomForm> request, @PathVariable Long id) throws Exception {
        return new BaseResponse<>(customService.updateCustom(request.getData(), id));
    }

    /**
     * 删除顾客
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public BaseResponse deleteCustom(@PathVariable Long id) throws Exception {
        customService.deleteCustom(id);
        return new BaseResponse();
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public BaseResponse recharge(@RequestBody MultipartFile file, @PathVariable Long id) throws Exception {
        return new BaseResponse();
    }

    /**
     * 充值
     */
    @RequestMapping(value = "/recharge/{id}", method = RequestMethod.POST)
    public BaseResponse logout(@RequestBody BaseRequest<CustomRechargeForm> request, @PathVariable Long id) throws Exception {
        customService.recharge(id, request.getData());
        return new BaseResponse();
    }
}
