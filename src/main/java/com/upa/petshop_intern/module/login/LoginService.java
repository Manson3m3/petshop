package com.upa.petshop_intern.module.login;

import com.upa.petshop_intern.common.exception.ErrorCode;
import com.upa.petshop_intern.common.exception.WebBackendException;
import com.upa.petshop_intern.common.response.BaseResponse;
import com.upa.petshop_intern.common.util.CommonUtil;
import com.upa.petshop_intern.common.util.DatetimeUtil;
import com.upa.petshop_intern.common.util.EncryptionUtil;
import com.upa.petshop_intern.common.util.SignUtil;
import com.upa.petshop_intern.entity.*;
import com.upa.petshop_intern.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

/**
 * Created by Yunhao.Cao on 2017/10/10.
 */
@Service
public class LoginService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoginRecordRepository loginRecordRepository;

    @Autowired
    VerifyRecordRepository verifyRecordRepository;

    @Autowired
    UserTokenRepository userTokenRepository;

    @Autowired
    UserNonceRepository userNonceRepository;

    @Value("${token-overdue}")
    private int USER_TOKEN_TIME_DUE;

    public LoginData login(LoginForm loginForm, HttpSession httpSession, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws Exception {
        String verifyCode = loginForm.getVerifyCode();
        String verifyToken = loginForm.getVerifyToken();

        if (!validVerifyCode(verifyCode, verifyToken)) {
            throw new WebBackendException(ErrorCode.VERIFY_CODE_NOT_VALID);
        }

        System.out.println("username = " + loginForm.getUserName());
        System.out.println("password = " + loginForm.getPassword());

        User user = validUserNameAndPassword(loginForm.getUserName(), loginForm.getPassword());

        if (null == user) {
            throw new WebBackendException(ErrorCode.USER_PASSWORD_NOT_VALID);
        }

        UserToken userToken = updateUserToken(user.getId(), httpSession);

        addLoginRecord(user, httpServletRequest);

        UserNonce userNonce = updateUserNonce(user.getId(), httpServletRequest.getRequestURI());

        String newNonce = userNonce.getNonce();

        SignUtil.writeAuthorization(httpServletResponse, newNonce);

        LoginData loginData = new LoginData();
        loginData.setUserName(EncryptionUtil.encryptAesBase64(user.getUserName(), newNonce));
        loginData.setUserId(EncryptionUtil.encryptAesBase64(String.valueOf(user.getId()), newNonce));
        loginData.setUserToken(EncryptionUtil.encryptAesBase64(userToken.getUserToken(), newNonce));

        return loginData;
    }

    /**
     * 验证用户名密码
     */
    public User validUserNameAndPassword(String userName, String password) {
        return userRepository.findByUserNameEncryptedAndPassword(userName, EncryptionUtil.encryptPassword(password));
    }

    /**
     * 验证验证码,包括验证过期时间
     */
    public boolean validVerifyCode(String verifyCode, String verifyToken) {
        VerifyRecord verifyRecord = verifyRecordRepository.findByVerifyCodeAndVerifyToken(verifyCode, verifyToken);
        if (null == verifyCode) {
            return false;
        }

        if (DatetimeUtil.getCurrentTimestamp().before(verifyRecord.getOverdueTime())) {
            return true;
        }
        return false;
    }

    /**
     * 写入登录日志
     */
    public void addLoginRecord(User user, HttpServletRequest httpServletRequest) {
        LoginRecord loginRecord = new LoginRecord();

        loginRecord.setId(0);
        loginRecord.setUserId(user.getId());
        loginRecord.setIp(CommonUtil.getRemoteIP(httpServletRequest));
        loginRecord.setLoginTime(DatetimeUtil.getCurrentTimestamp());

        loginRecordRepository.save(loginRecord);
    }

    /**
     * 更新用户Token, 包括时间
     */
    public UserToken updateUserToken(Long userId, HttpSession session) {
        UserToken userToken = new UserToken();

        userToken.setUserToken(getNewUserToken(session));
        userToken.setUserId(userId);
        userToken.setOverdueTime(new Timestamp(System.currentTimeMillis() + USER_TOKEN_TIME_DUE));

        return userTokenRepository.save(userToken);
    }

    /**
     * 更新用户Nonce, 包括时间
     */
    public UserNonce updateUserNonce(Long userId, String requestPath) {
        UserNonce userNonce = new UserNonce();

        userNonce.setUserId(userId);
        userNonce.setNonce(SignUtil.generateNonce(requestPath, userId));
        userNonce.setOverdueTime(new Timestamp(System.currentTimeMillis() + USER_TOKEN_TIME_DUE));

        return userNonceRepository.save(userNonce);
    }

    /**
     * 更新token过期时间
     */
    public void updateTokenOverdue(UserToken userToken) {
        userToken.setOverdueTime(new Timestamp(System.currentTimeMillis() + USER_TOKEN_TIME_DUE));
        userTokenRepository.save(userToken);
    }

    /**
     * 获取新的Token
     */
    public String getNewUserToken(HttpSession session) {
        return EncryptionUtil.getMd5(session.getId() + DatetimeUtil.getCurrentTimestamp().toString());
    }

    public UserToken findUserTokenByUserId(Long userId) {
        return userTokenRepository.findByUserId(userId);
    }

    public UserNonce findUserNonceByUserId(Long userId) {
        return userNonceRepository.findByUserId(userId);
    }
}
