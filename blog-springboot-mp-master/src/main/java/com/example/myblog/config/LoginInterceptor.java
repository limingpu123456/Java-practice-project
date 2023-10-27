package com.example.myblog.config;


import com.example.myblog.util.AppVar;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录判断的拦截器
 */
@Component
@Order(1)
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.先得到 HttpSession 对象
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(AppVar.SESSION_KEY_USERINFO) != null) {
            // 已经登录
            return true;
        }
        // 代码执行到此处，说明用户未登录
        response.sendRedirect("/login.html");
        return false;
    }
}
