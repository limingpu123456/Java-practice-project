package com.example.usermanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-25
 * Time: 12:34(李明浦)
 */
//系统配置文件类
@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Autowired
    private LoginIntercept loginIntercept;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginIntercept).
                addPathPatterns("/**").
                excludePathPatterns("/login").
                excludePathPatterns("/css/**").
                excludePathPatterns("/fonts/**").
                excludePathPatterns("/images/**").
                excludePathPatterns("/js/**").
                excludePathPatterns("/login.html");
    }
}
