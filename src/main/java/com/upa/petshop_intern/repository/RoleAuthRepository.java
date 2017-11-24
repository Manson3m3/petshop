package com.upa.petshop_intern.repository;

import com.upa.petshop_intern.entity.RoleAuth;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


//Created by Li.Hou on 2017/10/11.

@Repository
public interface RoleAuthRepository extends PagingAndSortingRepository<RoleAuth,Long>{
    void deleteByRoleIdAndAuthId(Long roleId,Long authId);
    List<RoleAuth> findAllByRoleId(Long roleId);
    List<RoleAuth> findAllByAuthId(Long authId);
    List<RoleAuth> findByRoleId(Long roleId);
    void deleteAllByRoleId(Long roleId);
    void deleteByAuthId(long authId);

}
