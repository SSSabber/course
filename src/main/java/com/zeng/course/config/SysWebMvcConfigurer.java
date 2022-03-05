package com.zeng.course.config;

import com.zeng.course.interceptor.AdminLoginInterceptor;
import com.zeng.course.interceptor.StudentLoginInterceptor;
import com.zeng.course.interceptor.TeacherLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SysWebMvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private AdminLoginInterceptor adminLoginInterceptor;
    @Autowired
    private StudentLoginInterceptor studentLoginInterceptor;
    @Autowired
    private TeacherLoginInterceptor teacherLoginInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        // 添加一个拦截器，拦截以/admin为前缀的url路径（后台登陆拦截）
        registry.addInterceptor(adminLoginInterceptor)
                .addPathPatterns("/admin/**");
        registry.addInterceptor(studentLoginInterceptor)
                .addPathPatterns("/student/**")
                .addPathPatterns("/course/**");
        registry.addInterceptor(teacherLoginInterceptor)
                .addPathPatterns("/teacher/**");
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:D:\\upload\\");
    }
}
