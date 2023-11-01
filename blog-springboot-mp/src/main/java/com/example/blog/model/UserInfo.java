package com.example.blog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 实体类
 */
@Setter
@Getter
@TableName("userinfo")
public class UserInfo implements Serializable {
    //通过serialVersionUID，可以确保序列化和反序列化过程中数据的一致性。
    @Serial
    private static final long serialVersionUID = 6259842500624893439L;
    @TableId(type = IdType.AUTO)
    private long uid;
    private String loginname;
    private String nickname;
    private String password;
    private String github;
    private String photo;
    private int state;
}
