package org.example.controller;

import org.example.exception.AppException;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public Object register(User user,
                           //上传的头像：1.保存在本地的文件夹(web服务器需要加载到)2.把url存放在注册用户的head字段
                           @RequestParam(value = "headFile",required = false) MultipartFile headFile){
        //校验请求数据：后端必须要有，我们省略
        //保存上传的用户头像到服务端本地
        if(headFile != null) {
            String head = userService.saveHead(headFile);
            //上传的路径映射为http服务路径
            //用户头像的http路径设置到user.head，把user插入数据库
            user.setHead(head);
        }
        userService.register(user);

        return null;
    }

    @PostMapping("/login")
    public Object login(@RequestBody User user, HttpServletRequest req){//username, password
        //根据账号查用户
        User exist = userService.queryByUsername(user.getUsername());
        //用户不存在
        if(exist == null) throw new AppException("LOG001", "用户不存在");
        //用户存在，校验密码
        if(!user.getPassword().equals(exist.getPassword()))
            throw new AppException("LOG002", "账号或密码错误");
        //校验通过，保存数据库的用户(包含所有字段)到session
        HttpSession session = req.getSession();//先创建session
        session.setAttribute("user", exist);
        return null;//登录成功
    }

    @GetMapping("/logout")
    public Object logout(HttpSession session){
        session.removeAttribute("user");
        return null;
    }
}
