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
        return true; // true를 반환해야 다음 인터셉터나 컨트롤러로 넘어감
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        // 로깅은 에러 발생 시에도 실행을 보장하는 afterCompletion으로 이동했습니다.
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        Long startTime = (Long) request.getAttribute("startTime");
        if (startTime == null) return; // 정적 리소스 등 preHandle을 거치지 않은 경우 방어

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        request.removeAttribute("startTime"); // 메모리 정리

        String controllerInfo = "Unknown Controller";

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String className = handlerMethod.getBeanType().getSimpleName();
            String methodName = handlerMethod.getMethod().getName();
            controllerInfo = className + "." + methodName;
        }

        // 1. IP 주소 추출
        String clientIp = getClientIp(request);

        // 2. 접속 기기 및 브라우저 (인터넷) 정보
        String userAgent = request.getHeader("User-Agent");

        // 3. HTTP 메서드 (GET, POST 등)
        String httpMethod = request.getMethod();

        // 4. 에러 발생 여부 확인 (옵션)
        String status = (ex != null) ? "ERROR" : "SUCCESS";

        // 최종 로그 출력
        log.info("[{}] {} | Status: {} | Time: {}ms | Controller: {} | IP: {} | Agent: {}",
                httpMethod, request.getRequestURI(), status, executionTime, controllerInfo, clientIp, userAgent);
    }

    /**
     * 클라이언트의 실제 IP를 추출하는 유틸리티 메서드
     * (AWS, Nginx 등 프록시나 로드밸런서를 거쳐올 경우를 대비한 세팅)
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // X-Forwarded-For 헤더에 여러 IP가 콤마로 구분되어 들어올 수 있으므로 첫 번째 IP만 추출
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        return ip;
    }
}