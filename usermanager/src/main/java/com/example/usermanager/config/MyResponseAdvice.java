package com.example.usermanager.config;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-25
 * Time: 18:31(李明浦)
 */

//返回统一的数据格式
@ControllerAdvice
public class MyResponseAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        HashMap<String,Object> result = new HashMap<>();
        result.put("state",1);
        result.put("msg","");
        result.put("data",body);
        return result;
    }
}
