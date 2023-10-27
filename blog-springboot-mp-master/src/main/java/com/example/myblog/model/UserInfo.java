package com.example.myblog.model;

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
