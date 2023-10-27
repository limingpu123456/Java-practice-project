package com.javacn.myblog.dao;

import com.javacn.myblog.model.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Description：
 * User: lmp
 * Date: 2023-08-29
 * Time: 23:00(李明浦)
 */
//第一步
@SpringBootTest //不能省略
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void reg() {
        UserInfo userInfo = new UserInfo();
        userInfo.setLoginname("张三");
        userInfo.setPassword("123456");
        int result = userMapper.reg(userInfo);
        System.out.println("添加结果"+result);
    }
}