package com.tensquare.user;

import com.tensquare.user.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类
 */
@Configuration //表明当前类为配置类
public class ApplicationConfig extends WebMvcConfigurationSupport {

    //注入拦截器对象
    @Autowired
    private JwtInterceptor jwtInterceptor;

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //拦截用户微服务中所有资源(controller)、除了login之外(/**/login)
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/**").excludePathPatterns("/**/login");
    }
}
