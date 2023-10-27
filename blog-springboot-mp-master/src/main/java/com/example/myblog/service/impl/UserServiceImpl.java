package com.example.myblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myblog.dao.UserMapper;
import com.example.myblog.model.UserInfo;
import com.example.myblog.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,UserInfo> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public int reg(UserInfo userInfo) {
        return userMapper.reg(userInfo);
    }

    @Override
    public UserInfo getUserByLoginName(String loginname) {
        return userMapper.getUserByLoginName(loginname);
    }
}
