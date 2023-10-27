package com.example.myblog.model.vo;


import com.example.myblog.model.UserInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class UserInfoVO extends UserInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1660890311645535668L;
    private String checkCode;
    private String codeKey;
}
