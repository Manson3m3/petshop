package com.upa.petshop_intern.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 安全拦截器定义
 *
 * @author liang.zhou
 */
@Configuration
public class SecurityConfig extends WebMvcConfigurerAdapter {

    @Autowired
    DigestInterceptor digestInterceptor;
    @Autowired
    SecurityInterceptor securityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加摘要验证拦截器
        String[] digestExcludePatterns = {"/", "/login/**", "/verifycode/**"};
        registry.addInterceptor(digestInterceptor).excludePathPatterns(digestExcludePatterns);//拦截指定url外的全部地址

        // 添加检查权限的拦截器
        String[] excludePatterns = {"/", "/login/**", "/verifycode/**"};
        registry.addInterceptor(securityInterceptor).excludePathPatterns(excludePatterns);//拦截指定url外的全部地址

        super.addInterceptors(registry);
    }
}
