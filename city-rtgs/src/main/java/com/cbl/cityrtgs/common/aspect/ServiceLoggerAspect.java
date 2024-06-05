package com.cbl.cityrtgs.common.aspect;

import com.cbl.cityrtgs.common.logger.RtgsLogger;
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
public class ServiceLoggerAspect {

    private final RtgsLogger rtgsLogger;
    private final RtgsAspectHelper aspectHelper;

    @Before("within(com.cbl.cityrtgs.services..*) ")
    public void endpointBefore(JoinPoint joinPoint) {
        try {
            rtgsLogger.trace(aspectHelper.getRequestDetails(joinPoint));

        } catch (Exception ex) {
            rtgsLogger.error(ex);

        }
    }

    @AfterReturning(value = ("within(com.cbl.cityrtgs.services..*)"), returning = "response")
    public void endpointAfterReturning(Object response) {

        try {
            rtgsLogger.trace(aspectHelper.getResponseDetails(response));
        } catch (Exception ex) {
            rtgsLogger.error(ex);
        }
    }

    @AfterThrowing(pointcut = ("within(com.cbl.cityrtgs.services..*)"), throwing = "e")
    public void endpointAfterThrowing(JoinPoint p, Exception e) {
        rtgsLogger.error(e.getMessage());
    }

}
