package com.cbl.cityrtgs.common.aspect;

import com.cbl.cityrtgs.common.logger.StpLogger;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Aspect
@Component
public class STPServiceLoggerAspect {
    private final StpLogger stpLogger;
    private final RtgsAspectHelper aspectHelper;

    @Before("within(com.cbl.cityrtgs.engine.service..*) ")
    public void endpointBefore(JoinPoint joinPoint) {
        try {
            stpLogger.trace(aspectHelper.getRequestDetails(joinPoint));

        } catch (Exception ex) {
            stpLogger.error(ex);

        }
    }

    @AfterReturning(value = ("within(com.cbl.cityrtgs.engine.service..*)"), returning = "response")
    public void endpointAfterReturning(Object response) {
        try {
            stpLogger.trace(aspectHelper.getResponseDetails(response));
        } catch (Exception ex) {
            stpLogger.error(ex);
        }
    }

    @AfterThrowing(pointcut = ("within(com.cbl.cityrtgs.engine.service..*)"), throwing = "e")
    public void endpointAfterThrowing(JoinPoint p, Exception e) {
        stpLogger.error(e.getMessage());
    }
}
