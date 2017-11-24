package com.upa.petshop_intern.repository;

import com.upa.petshop_intern.entity.LoginRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Yunhao.Cao on 2017/10/10.
 */
@Repository
public interface LoginRecordRepository extends PagingAndSortingRepository<LoginRecord, Long> {
    Page<LoginRecord> findAll(Pageable pageable);

    List<LoginRecord> findAll();

    LoginRecord findById(Long id);

    void deleteById(Long id);
}
