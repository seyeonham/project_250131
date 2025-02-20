package com.project_250131.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect // 부가 기능 정의(advice) + 어디에 적용(pointcut) => aspect
@Component
public class TimeTraceAop {

    // @Around("execution(* com.project_250131..*(..))")
    @Around("@annotation(TimeTrace)")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch sw = new StopWatch();
        sw.start();

        Object proceedObject = joinPoint.proceed(); // 원래 할 일 수행

        sw.stop();
        log.info("실행시간(ms): {}", sw.getTotalTimeMillis());
        log.info(sw.prettyPrint());

        return proceedObject;
    }
}
