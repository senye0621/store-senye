package com.senye.storesenye.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getSession().getAttribute("uid") == null){
            // 如果uid为空 就跳转到登录页面
            response.sendRedirect("/web/login.html");
            return false;
        }

        return true;
    }
}
