package com.upa.petshop_intern.module.logout;

import com.upa.petshop_intern.common.exception.ErrorCode;
import com.upa.petshop_intern.common.exception.WebBackendException;
import com.upa.petshop_intern.entity.UserNonce;
import com.upa.petshop_intern.repository.UserNonceRepository;
import com.upa.petshop_intern.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Yunhao.Cao on 2017/10/19.
 */
@Service
public class LogoutService {
    @Autowired
    UserTokenRepository userTokenRepository;
    @Autowired
    UserNonceRepository userNonceRepository;

    public void logout(Long id) throws WebBackendException {
        try {
            userTokenRepository.deleteByUserId(id);
            userNonceRepository.deleteByUserId(id);
        } catch (Exception e) {
            throw new WebBackendException(ErrorCode.LOGOUT_FAILED);
        }
    }
}
