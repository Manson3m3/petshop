package com.upa.petshop_intern.repository;

import com.upa.petshop_intern.entity.Custom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Yunhao.Cao on 2017/10/16.
 */
public interface CustomRepository extends PagingAndSortingRepository<Custom, Long> {
    Custom findByCustomName(String customName);

    Custom findById(Long id);

    List<Custom> findAll();

    Custom findByCustomNameAndPassword(String customName, String password);
    Custom findByCustomNameEncryptedAndPassword(String customNameEncrypted, String password);

    Page<Custom> findAll(Pageable pageable);

    int countByCustomName(String customName);

    void deleteById(Long id);

    //    @Query("select username from user where user.username like ?1")
    Page<Custom> findAllByCustomNameLike(String userName, Pageable pageable);
}
