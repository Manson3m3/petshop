package com.upa.petshop_intern.repository;

import com.upa.petshop_intern.entity.LoginRecord;
import com.upa.petshop_intern.entity.UserNonce;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Yunhao.Cao on 2017/10/11.
 */
public interface UserNonceRepository extends PagingAndSortingRepository<UserNonce, Long> {
    Page<UserNonce> findAll(Pageable pageable);

    List<UserNonce> findAll();

    UserNonce findByUserId(Long id);

    void deleteByUserId(Long id);
}
