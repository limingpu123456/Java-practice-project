package com.javacn.myblog.util;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Description：统一返回对象
 * User: lmp
 * Date: 2023-08-23
 * Time: 20:34(李明浦)
 */
@Setter
@Getter
public class AjaxResult implements Serializable {
    private int code;
    private String msg;
    private Object data;

    public static AjaxResult succ(Object data){
        AjaxResult result = new AjaxResult();
        result.setCode(200);
        result.setMsg("");
        result.setData(data);
        return result;
    }

    public static AjaxResult succ(int code,String msg,Object data){
        AjaxResult result = new AjaxResult();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static AjaxResult fail(int code,String msg){
        AjaxResult result = new AjaxResult();
        result.setCode(code);
        result.setMsg(msg);
        result.setData("");
        return result;
    }

    public static AjaxResult fail(int code,String msg,Object data){
        AjaxResult result = new AjaxResult();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
