package com.upa.petshop_intern.repository;

import com.upa.petshop_intern.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Li.Hou on 2017/10/11.
 */
@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category,Long>{
    void deleteById(Long id);
    Category findById(Long id);
    Category findByCategoryName(String categoryName);
    Page<Category> findByCategoryNameLike(String categoryName,Pageable pageable);
    List<Category> findAll();
    long countByCategoryName(String cateName);
}
