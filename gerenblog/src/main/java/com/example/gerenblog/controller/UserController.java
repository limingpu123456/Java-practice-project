package com.example.gerenblog.controller;

import com.example.gerenblog.common.AjaxResult;
import com.example.gerenblog.common.Constant;
import com.example.gerenblog.model.ArticleInfo;
import com.example.gerenblog.model.UserInfo;
import com.example.gerenblog.service.UserService;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-22
 * Time: 15:39(李明浦)
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/reg")
    public Object reg(String username,String password){
        //1.非空校验
        if(!StringUtils.hasLength(username) || !StringUtils.hasLength(password)){
            return AjaxResult.fail(-1,"非法参数请求");
        }
        //2.进行添加操作
        int result  = userService.add(username,password);
        if(result == 1){
           return AjaxResult.success("添加成功！",1);
        }else{
            return AjaxResult.fail(-1,"数据库添加出错");
        }
    }

    @RequestMapping("/login")
    public int login(HttpServletRequest request, String username, String password){
        //1.非空校验
        if(!StringUtils.hasLength(username) || !StringUtils.hasLength(password)){
            return 0;
        }
        //2.进行添加操作
        UserInfo userInfo = userService.login(username,password);
        if(userInfo == null ||  userInfo.getId() <= 0){
            return -1;
        }else{
            //将userinfo保存到session中
            HttpSession session = request.getSession();
            session.setAttribute(Constant.session_userinfo_key,userInfo);
           return 1;
        }
    }

    @RequestMapping("/logout")
    public boolean logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute(Constant.session_userinfo_key)!=null){
            session.removeAttribute(Constant.session_userinfo_key);
        }
        return true;
    }

    @RequestMapping("/myinfo")
    public UserInfo myInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(Constant.session_userinfo_key) != null) {
           return  (UserInfo) session.getAttribute(Constant.session_userinfo_key);
        }
        return null;
    }


}
