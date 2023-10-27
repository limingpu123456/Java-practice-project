package com.javacn.myblog.controller;

import com.javacn.myblog.model.UserInfo;
import com.javacn.myblog.model.vo.UserInfoVO;
import com.javacn.myblog.service.UserService;
import com.javacn.myblog.util.AjaxResult;
import com.javacn.myblog.util.AppVar;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description：
 * User: lmp
 * Date: 2023-08-23
 * Time: 20:31(李明浦)
 */
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/reg")
    public AjaxResult reg (UserInfoVO userInfoVO){
        //1.校验参数的正确性
        //StringUtils.hasLength可以确保这个属性不为空且不为null
        if(userInfoVO == null
           || !StringUtils.hasLength(userInfoVO.getLoginname())
           || !StringUtils.hasLength(userInfoVO.getPassword())
           || !StringUtils.hasLength(userInfoVO.getCheckCode())){
           return AjaxResult.fail(-1,"非法参数");
        }
        //todo:验证码校验
        //2.调用 service执行添加操作(添加到数据库)
        int result = userService.reg(userInfoVO);
        //3.将执行结果返回给前端
        return AjaxResult.succ(result);
    }

    @RequestMapping("/login")
    public AjaxResult login(UserInfoVO userInfoVO, HttpSession session){
        //1.非空校验
        if(userInfoVO == null || !StringUtils.hasLength(userInfoVO.getLoginname()) ||
        !StringUtils.hasLength(userInfoVO.getCheckCode())){
            //参数有误
            return AjaxResult.fail(-1,"非法参数");
        }
        //2.根据登录名查询到用户对象
        UserInfo userInfo = userService.getUserByLoginName(userInfoVO.getLoginname());
        //3.判断用户对象是否为空，如果为空说明当前用户名不存在
        if(userInfo== null || userInfo.getUid()<=0){
            //用户对象为null，或者用户为无效对象
            return AjaxResult.fail(-1,"用户名或密码错误");
        }
        //4.如果用户对象不为空，将密码进行比较
        if(userInfoVO.getPassword().equals(userInfo.getPassword())){
            //todo:5.如果输入的密码和真实的密码相同，将用户对象存储到session中
            userInfo.setPassword("");//传递之前先将敏感信息去除
            session.setAttribute(AppVar.SESSION_KEY_USERINFO,userInfo);
            return AjaxResult.succ(userInfo);
        }else{
            //6.如果输入的密码和真实的密码不相同，说明密码错误
            return AjaxResult.fail(-1,"用户名或密码错误");
        }
    }

    @RequestMapping("/logout")
    public AjaxResult logout(HttpSession session){
        try{
          //清除当前用户登录的session信息
          session.removeAttribute(AppVar.SESSION_KEY_USERINFO);
        }catch (Exception e){
            return AjaxResult.fail(-1,"清出失败");
        }
        return AjaxResult.succ(1);
    }

    @RequestMapping("/getuser")
    public AjaxResult getUser(){
        return AjaxResult.succ("userinfo");
    }
}
