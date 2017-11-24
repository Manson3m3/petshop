package com.upa.petshop_intern.module.role;

import com.upa.petshop_intern.common.exception.ErrorCode;
import com.upa.petshop_intern.common.exception.WebBackendException;
import com.upa.petshop_intern.common.response.BaseResponse;
import com.upa.petshop_intern.common.util.ConvertUtil;
import com.upa.petshop_intern.common.util.RowConverter;
import com.upa.petshop_intern.entity.Auth;
import com.upa.petshop_intern.entity.Role;
import com.upa.petshop_intern.entity.RoleAuth;
import com.upa.petshop_intern.repository.AuthRepository;
import com.upa.petshop_intern.repository.RoleAuthRepository;
import com.upa.petshop_intern.repository.RoleRepository;
import com.upa.petshop_intern.repository.UserRoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tao.jiang on 2017/9/26.
 */
@Service
@Transactional
public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    RoleAuthRepository roleAuthRepository;
    @Autowired
    AuthRepository authRepository;

    /**
     * 获取角色列表
     */
    public Page<RoleForm> findRoles(Pageable pageable)throws Exception{
        Page<Role> rolePage=roleRepository.findAll(pageable);
        Page<RoleForm> roleFormPage= ConvertUtil.convertPage(rolePage, pageable, rolePage.getTotalElements(), new RowConverter<Role, RoleForm>() {
            @Override
            public RoleForm convertRow(Role role) throws Exception {
                RoleForm roleForm=new RoleForm();
                BeanUtils.copyProperties(role,roleForm);
                roleForm.setAuthList(getAuthList(roleForm.getId()));
                return roleForm;
            }
        });
        return roleFormPage;
    }

    /**
     * 根据角色名搜索
     */
    public Page<RoleForm> findByRoleName(String roleName, Pageable pageable)throws Exception{
        Page<Role> rolePage=roleRepository.findByRoleNameLike("%"+roleName+"%",pageable);
        //找不到用户
        if(rolePage == null)
            throw new WebBackendException(ErrorCode.ROLE_NOT_FOUND);
        Page<RoleForm> roleFormPage = ConvertUtil.convertPage(rolePage, pageable, rolePage.getTotalElements(), new RowConverter<Role, RoleForm>() {
            @Override
            public RoleForm convertRow(Role role) throws Exception {
                RoleForm roleForm = new RoleForm();
                BeanUtils.copyProperties(role,roleForm);
                roleForm.setAuthList(getAuthList(roleForm.getId()));
                return roleForm;
            }
        });
        return roleFormPage;
    }

    /**
     *获取角色
     */
    public RoleForm findRoleById(Long roleId)throws  Exception{
        Role role=roleRepository.findById(roleId);
        //找不到角色
        if(role == null)
            throw new WebBackendException(ErrorCode.ROLE_NOT_FOUND);
        RoleForm roleForm=new RoleForm();
        BeanUtils.copyProperties(role,roleForm);
        roleForm.setAuthList(getAuthList(roleId));
        return roleForm;
    }

    /**
     *添加角色
     */
    public Role addRole(RoleForm roleForm)throws Exception{
        Role role=new Role();
        BeanUtils.copyProperties(roleForm,role,"id");
        //角色名冲突
        if(roleRepository.countByRoleName(roleForm.getRoleName())>0)
            throw new WebBackendException(ErrorCode.ROLE_EXISTS);
        roleRepository.save(role);
        roleAuthRepository.save(generateRoleAuthList(roleForm.getAuthList(),role.getId()));
        return role;
    }

    /**
     *修改角色
     */
    public Role updateRole(RoleForm roleForm,Long roleId)throws Exception{
        Role role=roleRepository.findById(roleId);
        //角色不存在
        if(role==null)
            throw new WebBackendException(ErrorCode.ROLE_NOT_FOUND);
        //修改后的角色名冲突
        if(!role.getRoleName().equals(roleForm.getRoleName()))
            if(roleRepository.countByRoleName(roleForm.getRoleName())>0)
                throw new WebBackendException(ErrorCode.ROLE_EXISTS);
        BeanUtils.copyProperties(roleForm,role);
        roleRepository.save(role);
        roleAuthRepository.deleteAllByRoleId(roleId);
        roleAuthRepository.save(generateRoleAuthList(roleForm.getAuthList(),roleId));
        return role;
    }

    /**
     *删除角色
     */
    public void deleteRole(Long roleId)throws Exception{
        Role role=roleRepository.findById(roleId);
        //角色不存在
        if (role==null)
            throw new WebBackendException(ErrorCode.ROLE_NOT_FOUND);
        userRoleRepository.deleteByRoleId(roleId);
        roleRepository.deleteById(roleId);
        roleAuthRepository.deleteAllByRoleId(roleId);
    }

    /**
     * 根据用户角色获取权限列表
     */
    public List<Auth> getAuthList(Long roleId)throws Exception{
        List<RoleAuth> roleAuthList=roleAuthRepository.findByRoleId(roleId);
        List<Auth> authList=new ArrayList<Auth>();
        if (roleAuthList==null)
            return null;
        for (RoleAuth roleAuth:roleAuthList){
            authList.add(authRepository.findById(roleAuth.getAuthId()));
        }
        return authList;
    }

    /**
     * 根据角色和权限列表插入关联
     */
    private List<RoleAuth> generateRoleAuthList(List<Auth> authList,Long roleId)throws Exception{
        if(authList==null||authList.size()==0)
        return null;
        List<RoleAuth> roleAuthList=new ArrayList<RoleAuth>();
        for(Auth auth:authList){
            RoleAuth roleAuth=new RoleAuth();
            roleAuth.setRoleId(roleId);
            roleAuth.setAuthId(auth.getId());
            roleAuthList.add(roleAuth);
        }
        return roleAuthList;
    }
}
