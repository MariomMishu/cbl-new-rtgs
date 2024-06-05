package com.cbl.cityrtgs.common.aspect;

import com.cbl.cityrtgs.common.logger.RtgsLogger;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Aspect
@Component
public class ControllerLoggerAspect {

    private final RtgsLogger rtgsLogger;
    private final RtgsAspectHelper aspectHelper;

    @Before("within(com.cbl.cityrtgs.controllers..*) ")
    public void endpointBefore(JoinPoint joinPoint) {
        try {
            rtgsLogger.trace(aspectHelper.getRequestDetails(joinPoint));

        } catch (Exception ex) {
            rtgsLogger.error(ex);

        }
    }

    @AfterReturning(value = ("within(com.cbl.cityrtgs.controllers..*)"), returning = "returnValue")
    public void endpointAfterReturning(JoinPoint joinPoint, Object returnValue) {
        try {
            rtgsLogger.trace(aspectHelper.getResponseDetails(returnValue));
        } catch (Exception ex) {
            rtgsLogger.error(ex);
        }
    }

    @AfterThrowing(pointcut = ("within(com.cbl.cityrtgs.controllers..*)"), throwing = "e")
    public void endpointAfterThrowing(JoinPoint joinPoint, Exception e) {
        rtgsLogger.error(e.getMessage());
    }

}
