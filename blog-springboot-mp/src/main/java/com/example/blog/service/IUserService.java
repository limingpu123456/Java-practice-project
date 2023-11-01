package com.example.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.model.UserInfo;

public interface IUserService extends IService<UserInfo> {
    int reg(UserInfo userInfo);
    UserInfo getUserByLoginName(String loginname);
}
