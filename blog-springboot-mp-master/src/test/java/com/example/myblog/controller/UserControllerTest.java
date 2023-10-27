package com.example.myblog.controller;

import com.example.myblog.dao.ArticleInfoMapper;
import com.example.myblog.model.vo.UserInfoVO;
import com.example.myblog.util.AjaxResult;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Resource
    private ArticleInfoMapper articleInfoMapper;

    @Test
    void login() {
        UserInfoVO userInfoVO = new UserInfoVO();
        AjaxResult ajaxResult = userController.login(userInfoVO, null);
        System.out.println(ajaxResult.toString());
    }

    @Test
    void getUser() {
    }
}