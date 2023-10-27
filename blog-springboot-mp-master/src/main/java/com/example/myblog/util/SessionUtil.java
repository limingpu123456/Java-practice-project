package com.example.myblog.util;

import com.example.myblog.model.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {

    /**
     * 获取当前登录用户的信息
     * @param session
     * @return
     */
    public static UserInfo getUserInfo(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session!=null && session.getAttribute(AppVar.SESSION_KEY_USERINFO)!=null){
            // 用户已登录
            return (UserInfo) session.getAttribute(AppVar.SESSION_KEY_USERINFO);
        }
        return null;
    }

}







