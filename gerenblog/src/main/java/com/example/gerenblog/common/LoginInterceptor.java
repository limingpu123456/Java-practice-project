package com.example.gerenblog.common;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-23
 * Time: 14:48(李明浦)
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从session中得到用户的信息[如果从session中得到了userinfo对象，说明用户已经登陆了，如果没得到userinfo对象，说明未登录]
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute(Constant.session_userinfo_key)!=null) {
            //当前用户已经登陆了
            return true;
        }
        response.setStatus(401);
        return false;
    }
}
