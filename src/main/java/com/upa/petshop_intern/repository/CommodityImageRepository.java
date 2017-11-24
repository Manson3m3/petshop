package com.upa.petshop_intern.repository;

import com.upa.petshop_intern.entity.CommodityImage;
import com.upa.petshop_intern.entity.CommodityStore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Yunhao.Cao on 2017/10/11.
 */
public interface CommodityImageRepository extends PagingAndSortingRepository<CommodityImage, Long> {
    Page<CommodityImage> findAll(Pageable pageable);

    List<CommodityImage> findAll();

    CommodityImage findByCommodityId(Long commodityId);

    void deleteByCommodityId(Long commodityId);

    void deleteAllByCommodityId(Long commodityId);
}
