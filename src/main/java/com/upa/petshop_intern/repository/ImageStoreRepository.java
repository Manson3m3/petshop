package com.upa.petshop_intern.repository;

import com.upa.petshop_intern.entity.ImageStore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Yunhao.Cao on 2017/10/11.
 */
public interface ImageStoreRepository extends PagingAndSortingRepository<ImageStore, Long> {
    Page<ImageStore> findAll(Pageable pageable);

    List<ImageStore> findAll();

    ImageStore findById(Long imageStoreId);

    ImageStore findByHash(String hash);

    void deleteById(Long imageStoreId);
}
