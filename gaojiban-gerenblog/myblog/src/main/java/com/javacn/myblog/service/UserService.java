package com.javacn.myblog.service;

import com.javacn.myblog.dao.UserMapper;
import com.javacn.myblog.model.UserInfo;
import com.javacn.myblog.model.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description：
 * User: lmp
 * Date: 2023-08-29
 * Time: 23:05(李明浦)
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public int reg(UserInfo userInfo){
        return userMapper.reg(userInfo);
    }

    public UserInfo getUserByLoginName(String loginname){
        return userMapper.getUserByLoginName(loginname);
    }
}
