package com.example.blog.model.vo;


import com.example.blog.model.UserInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class UserInfoVO extends UserInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1660890311645535668L;
    private String checkCode; // 输入的验证码
    private String codeKey; //验证码
}
