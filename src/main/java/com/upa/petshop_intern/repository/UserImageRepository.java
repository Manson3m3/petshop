package com.upa.petshop_intern.repository;

import com.upa.petshop_intern.entity.UserImage;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Tao.Jiang on 2017/10/13.
 */
@Repository
public interface UserImageRepository extends PagingAndSortingRepository<UserImage,Long> {
    void deleteAllByUserId(Long userId);
    UserImage findByUserId(Long userId);
}
