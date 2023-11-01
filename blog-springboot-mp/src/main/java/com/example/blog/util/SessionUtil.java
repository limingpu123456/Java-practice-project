package com.example.blog.util;

import com.example.blog.model.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Description：登录用户的工具类
 * User: lmp
 * Date: 2023-10-09
 * Time: 23:25(李明浦)
 */
public class SessionUtil {
    //从session中获取当前登录的用户对象
    public static UserInfo getUserInfo(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute(AppVar.SESSION_KEY_USERINFO)!=null){
            //用户已经登录了
            return (UserInfo) session.getAttribute(AppVar.SESSION_KEY_USERINFO);
        }
        return null;
    }
}
