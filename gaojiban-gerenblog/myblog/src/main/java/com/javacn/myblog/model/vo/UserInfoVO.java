package com.javacn.myblog.model.vo;

import com.javacn.myblog.model.UserInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Description：
 * User: lmp
 * Date: 2023-08-23
 * Time: 20:46(李明浦)
 */
@Setter
@Getter
public class UserInfoVO extends UserInfo implements Serializable {
    private String checkCode;
    private String codeKey;
}
