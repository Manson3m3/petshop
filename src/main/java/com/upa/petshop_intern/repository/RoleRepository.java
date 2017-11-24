package com.upa.petshop_intern.repository;

import com.upa.petshop_intern.entity.Role;
import com.upa.petshop_intern.module.role.RoleForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
/**
 * Created by Tao.Jiang on 2017/9/27.
 */
@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role,Long> {

    Role findById(Long roleId);

    void deleteById(Long roleId);

    Role findByRoleName(String roleName);

    Page<Role> findByRoleNameLike(String roleName,Pageable pageable);

    int countByRoleName(String roleName);
}
