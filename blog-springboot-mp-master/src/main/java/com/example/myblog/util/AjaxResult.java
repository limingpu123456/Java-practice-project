package com.example.myblog.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 接口统一返回对象
 */
@Getter
@Setter
@ToString
public class AjaxResult implements Serializable {
    @Serial
    private static final long serialVersionUID = -1288242817048774436L;
    // 状态码
    private int code;
    // 描述信息
    private String msg;
    // 返回数据
    private Object data;

    /**
     * 返回统一成功时调用的方法
     *
     * @param data
     * @return
     */
    public static AjaxResult succ(Object data) {
        AjaxResult result = new AjaxResult();
        result.setCode(200);
        result.setMsg("");
        result.setData(data);
        return result;
    }

    public static AjaxResult succ(int code, String msg, Object data) {
        AjaxResult result = new AjaxResult();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    /**
     * 返回统一失败对象
     *
     * @param code
     * @param msg
     * @return
     */
    public static AjaxResult fail(int code, String msg) {
        AjaxResult result = new AjaxResult();
        result.setCode(code);
        result.setMsg(msg);
        result.setData("");
        return result;
    }

    public static AjaxResult fail(int code, String msg, Object data) {
        AjaxResult result = new AjaxResult();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

}
