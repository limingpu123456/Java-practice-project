package com.example.usermanager.config;

import com.example.usermanager.util.ConstVariable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-25
 * Time: 12:27(李明浦)
 */
//用户拦截器
@Component
public class LoginIntercept implements HandlerInterceptor {
    //返回true表示用户已经登录，会继续访问目标方法
    //返回false表示未登录，跳转到登录页面
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute(ConstVariable.USER_SESSION_KEY)!=null){
            return true;
        }
        //执行到此行表示未登录
        response.sendRedirect("/login.html");
        return false;
    }
}
