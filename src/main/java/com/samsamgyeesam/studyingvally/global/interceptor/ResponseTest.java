package com.samsamgyeesam.studyingvally.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class ResponseTest implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        request.removeAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        String controllerInfo = "Unknown Controller";

        // 핸들러가 컨트롤러 메서드인지 확인 (정적 리소스 요청 등에서의 캐스팅 에러 방지)
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            // 컨트롤러 클래스 이름 (예: CourseController)
            String className = handlerMethod.getBeanType().getSimpleName();
            // 컨트롤러 메서드 이름 (예: getCourseList)
            String methodName = handlerMethod.getMethod().getName();

            controllerInfo = className + "." + methodName;
        }

        // URI와 함께 컨트롤러 정보, 실행 시간을 로그로 출력
        log.info("[Request URI: {}] [Controller: {}] Execution Time: {} ms",
                request.getRequestURI(), controllerInfo, executionTime);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}