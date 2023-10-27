package com.example.usermanager.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-25
 * Time: 11:57(李明浦)
 */
//统一异常拦截处理类
@ControllerAdvice
@ResponseBody
public class MyExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public Object ExceptionAdvice(Exception e){
        HashMap<String,Object> result  = new HashMap<>();
        result.put("state",-1);
        result.put("msg","程序出现异常" + e.getMessage());
        return result;
    }
}
