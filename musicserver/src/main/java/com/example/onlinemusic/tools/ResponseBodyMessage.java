package com.example.onlinemusic.tools;

import lombok.Data;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-29
 * Time: 15:52(李明浦)
 */
@Data
public class ResponseBodyMessage <T>{
    private int status;
    private String message;
    private T data; //返回给前端的数据

    public ResponseBodyMessage(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
