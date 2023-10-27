package com.example.gerenblog.common;

import com.example.gerenblog.model.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-24
 * Time: 15:46(李明浦)
 */
public class SessionUtil {
    public static UserInfo getLoginUser(HttpServletRequest request){
        HttpSession session  = request.getSession(false);
        if(session != null && session.getAttribute(Constant.session_userinfo_key)!=null){
           return  (UserInfo)session.getAttribute(Constant.session_userinfo_key);
        }
        return null;
    }
}
