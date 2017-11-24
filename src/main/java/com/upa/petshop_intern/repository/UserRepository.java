package com.upa.petshop_intern.repository;

import com.upa.petshop_intern.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Yunhao.Cao on 2017/10/10.
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByUserName(String userName);

    User findById(Long id);

    List<User> findAll();

    User findByUserNameAndPassword(String userName, String password);

    User findByUserNameEncryptedAndPassword(String userNameEncrypted, String password);

    Page<User> findAll(Pageable pageable);

    int countByUserName(String userName);

    void deleteById(Long id);

    Page<User> findByUserNameLike(String userName,Pageable pageable);
}
