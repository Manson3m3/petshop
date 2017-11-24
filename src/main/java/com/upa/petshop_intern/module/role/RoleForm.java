package com.upa.petshop_intern.module.role;

import java.util.List;

import com.upa.petshop_intern.entity.Auth;
import com.upa.petshop_intern.module.user.UserForm;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.GeneratedValue;

/**
 * Created by Tao.Jiang on 2017/9/28.
 */

public class RoleForm {
    private Long id;
    @NotBlank(message = "{rolename.blank}")
    private String roleName;
    private String roleDescription;
    private List<Auth> authList;

    public List<Auth> getAuthList() {
        return authList;
    }

    public void setAuthList(List<Auth> authList) {
        this.authList = authList;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public interface Create {
    }

    public interface Update {
    }
}
