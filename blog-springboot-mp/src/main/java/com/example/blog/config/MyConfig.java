package com.example.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Value("${imagepath}")
    private String imagepath;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns("/user/reg") // 排除注册接口（注册接口不拦截）
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/getuser")
                .excludePathPatterns("/getcaptcha")
                .excludePathPatterns("/art/list")
                .excludePathPatterns("/art/detail")
                .excludePathPatterns("/user/getsess")
                .excludePathPatterns("/user/isartbyme")
                .excludePathPatterns("/comment/list")
                .excludePathPatterns("/art/update_rcount")
                .excludePathPatterns("/**/*.html")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/editor.md/**")
                .excludePathPatterns("/img/**")
                .excludePathPatterns("/image/**")
                .excludePathPatterns("/js/**");

    }

    /**
     * 映射图片路径
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations("file:" + imagepath);

    }
}















