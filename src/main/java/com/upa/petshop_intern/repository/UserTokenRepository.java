package com.upa.petshop_intern.repository;

import com.upa.petshop_intern.entity.LoginRecord;
import com.upa.petshop_intern.entity.UserToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Yunhao.Cao on 2017/10/11.
 */
@Repository
public interface UserTokenRepository extends PagingAndSortingRepository<UserToken, Long> {
    Page<UserToken> findAll(Pageable pageable);

    List<UserToken> findAll();

    UserToken findByUserId(Long id);

    void deleteByUserId(Long id);
}
