package com.javacn.myblog.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import java.io.Serializable;

/**
 * Description：
 * User: lmp
 * Date: 2023-08-21
 * Time: 22:27(李明浦)
 */
@Getter
@Setter
public class UserInfo implements Serializable {
    private long uid;
    private String loginname;
    private String nickname;
    private String password;
    private String github;
    private String photo;
    private int state;
}
