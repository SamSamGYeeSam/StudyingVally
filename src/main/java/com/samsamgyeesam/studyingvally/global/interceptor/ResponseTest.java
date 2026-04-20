package com.samsamgyeesam.studyingvally.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String className = handlerMethod.getBeanType().getSimpleName();
            String methodName = handlerMethod.getMethod().getName();
            controllerInfo = className + "." + methodName;
        }

        // 1. IP 주소 추출 (프록시나 로드밸런서를 거친 경우를 대비)
        String clientIp = getClientIp(request);

        // 2. User-Agent 추출 (사용자 브라우저 및 OS 정보)
        String userAgent = request.getHeader("User-Agent");

        // 3. 요청 파라미터 추출 (GET 요청의 쿼리 스트링)
        String queryString = request.getQueryString();
        String requestUriWithQuery = request.getRequestURI() + (queryString != null ? "?" + queryString : "");

        // 4. 사용자 식별 정보 추출 (인증 방식에 따라 다름)
        String userId = getUserId(request);

        // 최종 통합 로그 출력
        log.info("[IP: {}] [User: {}] [Agent: {}] [URI: {}] [Controller: {}] Execution: {} ms",
                clientIp, userId, userAgent, requestUriWithQuery, controllerInfo, executionTime);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    /**
     * 클라이언트의 실제 IP를 추출하는 메서드
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
            ip = request.getRemoteAddr();
        }
        // X-Forwarded-For에 여러 IP가 쉼표로 구분되어 들어올 경우 첫 번째 IP가 실제 클라이언트 IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }

    /**
     * 현재 요청을 보낸 사용자의 ID를 추출하는 메서드
     */
    private String getUserId(HttpServletRequest request) {
        // Spring Security를 사용하는 경우 (가장 권장)
        if (request.getUserPrincipal() != null) {
            return request.getUserPrincipal().getName();
        }
        return "Anonymous"; // 비로그인 사용자
    }
}