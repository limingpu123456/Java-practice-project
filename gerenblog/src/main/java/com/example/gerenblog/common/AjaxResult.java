package com.example.gerenblog.common;

import java.util.HashMap;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-22
 * Time: 16:29(李明浦)
 */
public class AjaxResult {
    public static HashMap<String,Object> success(Object data){
        HashMap<String,Object> result  = new HashMap<>();
        result.put("code",200);
        result.put("msg","");
        result.put("data",data);
        return result;
    }

    public static HashMap<String,Object> success(String msg,Object data){
        HashMap<String,Object> result  = new HashMap<>();
        result.put("code",200);
        result.put("msg",msg);
        result.put("data",data);
        return result;
    }

    public static HashMap<String,Object> fail(int code,String msg){
        HashMap<String,Object> result  = new HashMap<>();
        result.put("code",code);
        result.put("msg",msg);
        return result;
    }
}
