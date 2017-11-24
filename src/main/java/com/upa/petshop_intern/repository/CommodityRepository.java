package com.upa.petshop_intern.repository;

import com.upa.petshop_intern.entity.Commodity;
import com.upa.petshop_intern.entity.LoginRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Yunhao.Cao on 2017/10/11.
 */
@Repository
public interface CommodityRepository extends PagingAndSortingRepository<Commodity, Long> {
    Page<Commodity> findAll(Pageable pageable);

    Page<Commodity> findAllByCommodityNameLike(String name, Pageable pageable);

    List<Commodity> findAll();

    Commodity findById(Long id);

    void deleteById(Long id);
}
