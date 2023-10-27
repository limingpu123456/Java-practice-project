package com.example.gerenblog.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-22
 * Time: 16:54(李明浦)
 */
@ControllerAdvice
@ResponseBody
public class ExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public Object ExceptionAdvice(Exception e){
        return AjaxResult.fail(-1,e.getMessage());
    }
}
