package com.javacn.myblog.config;

import com.javacn.myblog.util.AppVar;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Description：登录判断的拦截器
 * User: lmp
 * Date: 2023-09-17
 * Time: 12:06(李明浦)
 */
@Component
//通过上面这个注释，将下面这个拦截器托管给IOC容器，在项目加载的时候就可以通过注入的方式使用了
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.先得到httpsession对象
        //注意里面要写成false，如果是true的话就是如果有会话信息就用，没有的话就创建新的会话信息，我们这个是拦截器，不需要创建，所以要用false
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute(AppVar.SESSION_KEY_USERINFO)!=null){
            //已经登录了
            return true;
        }
        response.sendRedirect("/login.html");
        return false;
    }
}
