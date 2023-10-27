package com.example.onlinemusic.controller;

import com.example.onlinemusic.mapper.UserMapper;
import com.example.onlinemusic.model.User;
import com.example.onlinemusic.tools.Constant;
import com.example.onlinemusic.tools.ResponseBodyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-29
 * Time: 15:34(李明浦)
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping("/login1")
    public ResponseBodyMessage<User> login1(@RequestParam("username") String username,@RequestParam("password") String password,
            HttpServletRequest request){
        User userLogin = new User();
        userLogin.setUsername(username);
        userLogin.setPassword(password);

        User user = userMapper.login(userLogin);

        if(user == null){
            System.out.println("登录失败");
            return new ResponseBodyMessage<User>(-1,"登录失败",userLogin);
        }else{
            System.out.println("登录成功");
            request.getSession().setAttribute(Constant.USERINFO_SESSION_KEY,user);
            return new ResponseBodyMessage<User>(0,"登录成功",userLogin);
        }
    }


    @RequestMapping("/login")
    public ResponseBodyMessage<User> login(@RequestParam("username") String username,@RequestParam("password") String password,
                                           HttpServletRequest request){
      User user =  userMapper.selectByName(username);
        if(user == null){
            System.out.println("登录失败");
            return new ResponseBodyMessage<User>(-1,"登录失败",user);
        }else{
            boolean flg = bCryptPasswordEncoder.matches(password,user.getPassword());
            if(!flg){
                return new ResponseBodyMessage<User>(-1,"用户名或者密码错误",user);
            }
            request.getSession().setAttribute(Constant.USERINFO_SESSION_KEY,user);
            return new ResponseBodyMessage<User>(0,"登录成功",user);
        }
    }
}
