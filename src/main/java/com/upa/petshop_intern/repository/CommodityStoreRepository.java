package com.upa.petshop_intern.repository;

import com.upa.petshop_intern.entity.CommodityCategory;
import com.upa.petshop_intern.entity.CommodityStore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Yunhao.Cao on 2017/10/11.
 */
@Repository
public interface CommodityStoreRepository extends PagingAndSortingRepository<CommodityStore, Long> {
    Page<CommodityStore> findAll(Pageable pageable);

    List<CommodityStore> findAll();

    CommodityStore findByCommodityId(Long commodityId);

    void deleteByCommodityId(Long commodityId);
}
