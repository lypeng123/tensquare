package com.tensquare.qa.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 解析token的拦截器
 */
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 拦截器拦截到controller要执行的方法
     * 解析token字符串  如果找到角色是管理员，将token对象放入到request中，如果没有找到，程序在获取request时会得到空值
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器执行了、、、");
        //解析token
        String authorization = request.getHeader("Authorization");
        //判断
        if(authorization!=null){
            //解析请求头内容   authorization=Bearer token
            String token = authorization.substring(7);
            //判断
            if(token!=null){
                //解析token字符串
                Claims claims = jwtUtils.parseToken(token);
                if(claims!=null){ //如果载荷中不为空
                    //获取载荷中的信息
                    String role=(String) claims.get("roles");
                    //判断用户是管理员或者是普通用户
                    if("admin".equals(role)||"user".equals(role)){
                        //说明是管理员或者是普通用户，将载荷对象存放到request中
                        request.setAttribute("user_claims", claims);
                    }
                }
            }
        }

        //拦截器是放行的，没有拦截
        return true;
    }
}
