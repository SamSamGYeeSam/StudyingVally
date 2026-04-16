package com.samsamgyeesam.studyingvally.global.interceptor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final ResponseTest responseTest;

    @Autowired
    public WebConfiguration(ResponseTest responseTest) {
        this.responseTest = responseTest;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //요청도 제한할 수 있음. /employee
        registry.addInterceptor(responseTest)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/images/**")
                .excludePathPatterns("/error/**");
        //* 은 파일들만(폴더포함 x), **은 하위폴더 포함.
    }
}
