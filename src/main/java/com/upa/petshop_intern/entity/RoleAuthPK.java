package com.upa.petshop_intern.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Li.Hou on 2017/10/11.
 */
public class RoleAuthPK implements Serializable {
    private long roleId;
    private long authId;

    @Column(name = "role_id", nullable = false)
    @Id
    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    @Column(name = "auth_id", nullable = false)
    @Id
    public long getAuthId() {
        return authId;
    }

    public void setAuthId(long authId) {
        this.authId = authId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleAuthPK that = (RoleAuthPK) o;

        if (roleId != that.roleId) return false;
        if (authId != that.authId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (roleId ^ (roleId >>> 32));
        result = 31 * result + (int) (authId ^ (authId >>> 32));
        return result;
    }
}
