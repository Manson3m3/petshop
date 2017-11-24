package com.upa.petshop_intern.module.auth;

import com.upa.petshop_intern.common.BaseRequest;
import com.upa.petshop_intern.common.page.PageParams;
import com.upa.petshop_intern.common.response.BaseResponse;
import com.upa.petshop_intern.common.response.PageResponse;
import com.upa.petshop_intern.entity.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Li.Hou on 2017/10/11.
 */
@RestController
@RequestMapping("/auths")
public class AuthController {
    @Autowired
    private AuthService authService;

    /**
     * 分页展示权限列表
     * @param pageParams
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    public PageResponse<Auth> findAllAuths(PageParams pageParams) throws Exception {
        return new PageResponse<>(authService.findAllAuths(pageParams.getPageRequest()));
    }

    /**
     * 添加权限
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse addAuth(@RequestBody BaseRequest<Auth> request) throws Exception {
        authService.addAuth(request.getData());
        return new BaseResponse();
    }

    /**
     * 更新权限
     * @param request
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public BaseResponse updateAuth(@RequestBody BaseRequest<Auth> request,@PathVariable Long id) throws Exception {
        authService.updateAuth(request.getData(),id);

        return new BaseResponse();
    }

    /**
     * 删除权限
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public BaseResponse deleteAuth(@PathVariable Long id) throws Exception {
        authService.deleteAuth(id);
        return new BaseResponse();
    }

    /**
     * 根据id查找权限
     * @param id
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public BaseResponse findAuthById(@PathVariable Long id) throws Exception {
        return new BaseResponse<>(authService.findAuthById(id));
    }

}
