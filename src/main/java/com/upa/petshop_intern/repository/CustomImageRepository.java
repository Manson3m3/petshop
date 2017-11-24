package com.upa.petshop_intern.repository;

import com.upa.petshop_intern.entity.CustomImage;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Yunhao.Cao on 2017/10/16.
 */
@Repository
public interface CustomImageRepository extends PagingAndSortingRepository<CustomImage,Long> {
    void deleteAllByCustomId(Long userId);
}
