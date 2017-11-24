package com.upa.petshop_intern.repository;

import com.upa.petshop_intern.entity.Auth;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by Tao.Jiang on 2017/10/12.
 */
@Repository
public interface AuthRepository extends PagingAndSortingRepository<Auth,Long>{
    Auth findById(Long authId);
    Auth findByAuthName(String authName);
    long countByAuthName(String authName);
    void deleteById(Long authId);
    List<Auth> findAll();
}
