package com.example.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myblog.model.UserInfo;

public interface IUserService extends IService<UserInfo> {
    int reg(UserInfo userInfo);
    UserInfo getUserByLoginName(String loginname);
}
