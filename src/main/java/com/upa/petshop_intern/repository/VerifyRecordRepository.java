package com.upa.petshop_intern.repository;

import com.upa.petshop_intern.entity.VerifyRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Yunhao.Cao on 2017/10/10.
 */
@Repository
public interface VerifyRecordRepository extends PagingAndSortingRepository<VerifyRecord, Long> {
    Page<VerifyRecord> findAll(Pageable pageable);

    List<VerifyRecord> findAll();

    VerifyRecord findById(Long id);

    VerifyRecord findByVerifyCodeAndVerifyToken(String verifyCode, String verifyToken);

    void deleteById(Long id);
}
