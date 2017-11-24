package com.upa.petshop_intern.repository;

import com.upa.petshop_intern.entity.CommodityCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Li.Hou on 2017/10/16.
 */
public interface CommodityCategoryRepository extends PagingAndSortingRepository<CommodityCategory, Long> {
    /**
     * 分页显示商品品类列表
     * @param pageable
     * @return
     */
    Page<CommodityCategory> findAll(Pageable pageable);

    /**
     * 根据商品id查询商品品类列表
     * @param commodityId
     * @return
     */
    List<CommodityCategory> findAllByCommodityId(Long commodityId);

    /**
     * 根据品类id查询商品品类列表
     * @param categoryId
     * @return
     */
    List<CommodityCategory> findAllByCategoryId(Long categoryId);

    /**
     * 删除单个商品品类关联
     * @param commodityId
     * @param categoryId
     */
    void deleteByCommodityIdAndCategoryId(Long commodityId,Long categoryId);

    /**
     * 删除商品及其所有品类关联
     * @param commodityId
     */
    void deleteByCommodityId(Long commodityId);

    /**
     * 删除品类及其所有商品关联
     * @param categoryId
     */
    void deleteByCategoryId(Long categoryId);
}
