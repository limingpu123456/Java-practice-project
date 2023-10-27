package com.example.gerenblog.service;

import com.example.gerenblog.mapper.UserMapper;
import com.example.gerenblog.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-22
 * Time: 15:40(李明浦)
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public int add(String username,String password){
        return userMapper.add(username,password);
    }

    public UserInfo login(String username, String password){
        return userMapper.login(username,password);
    }
}
