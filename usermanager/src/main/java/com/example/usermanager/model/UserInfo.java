package com.example.usermanager.model;

import lombok.Data;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-25
 * Time: 11:51(李明浦)
 */
@Data
public class UserInfo {
    private int uid;
    private String username;
    private String loginname;
    private String password;
    private String sex;
    private int age;
    private String address;
    private String qq;
    private String email;
    private boolean isadmin;
    private int state;
    private String createtime;
    private String updatetime;
}
