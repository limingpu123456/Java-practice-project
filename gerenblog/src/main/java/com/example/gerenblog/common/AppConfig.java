package com.example.gerenblog.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-23
 * Time: 15:04(李明浦)
 */
@Configuration
public class AppConfig implements WebMvcConfigurer {

    List<String> excludes = Arrays.asList(
            new String[]{
            "/**/*.html",
            "/js/**",
            "/editor.md/**",
            "/css/**",
            "/img/**",
            "/user/login",
            "/user/reg",
            "/art/detail",
            "/art/list",
            "/art/totalpage"
            });

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //不拦截的url集合

        InterceptorRegistration registration =  registry.addInterceptor(loginInterceptor);
        registration.addPathPatterns("/**");
        registration.excludePathPatterns(excludes);
    }
}
