package com.samsamgyeesam.studyingvally.global.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class ServicePerformanceAspect {

    // 모든 도메인 하위의 service 패키지 내 클래스 메서드 대상
    @Around("execution(* com.samsamgyeesam.studyingvally.domain..service..*.*(..))")
    public Object measureServiceTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            return joinPoint.proceed();
        } finally {
            stopWatch.stop();

            String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
            String methodName = joinPoint.getSignature().getName();
            long totalTime = stopWatch.getTotalTimeMillis();

            // 서비스 로직이 500ms 이상 걸리면 주의 로그 출력
            if (totalTime > 500) {
                log.warn("[Service Slow] {}.{} | Time: {}ms", className, methodName, totalTime);
            } else {
                log.info("[Service Performance] {}.{} | Time: {}ms", className, methodName, totalTime);
            }
        }
    }
}