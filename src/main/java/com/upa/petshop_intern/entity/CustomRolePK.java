package com.upa.petshop_intern.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Yunhao.Cao on 2017/10/17.
 */
public class CustomRolePK implements Serializable {
    private long customId;
    private long roleId;

    @Column(name = "custom_id", nullable = false)
    @Id
    public long getCustomId() {
        return customId;
    }

    public void setCustomId(long customId) {
        this.customId = customId;
    }

    @Column(name = "role_id", nullable = false)
    @Id
    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        CustomRolePK that = (CustomRolePK) o;

        if (customId != that.customId)
            return false;
        if (roleId != that.roleId)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (customId ^ (customId >>> 32));
        result = 31 * result + (int) (roleId ^ (roleId >>> 32));
        return result;
    }
}
