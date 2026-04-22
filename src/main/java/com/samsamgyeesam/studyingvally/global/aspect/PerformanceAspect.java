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
public class PerformanceAspect {


    // domain 하위의 모든 repository 패키지 내 클래스의 메서드들
    @Around("execution(* com.samsamgyeesam.studyingvally.domain..repository..*.*(..))")
    public Object measureTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // 실제 레포지토리 메서드 실행
        Object proceed = joinPoint.proceed();

        stopWatch.stop();

        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.info("[DB Performance] {}.{} | Execution Time: {}ms",
                className, methodName, stopWatch.getTotalTimeMillis());

        return proceed;
    }
}