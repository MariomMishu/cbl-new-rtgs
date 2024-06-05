package com.cbl.cityrtgs.common.aspect;

import com.cbl.cityrtgs.common.logger.RtgsLogger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

@Component
public class RtgsAspectHelper {
    private final RtgsLogger rtgsLogger;
    private final ObjectMapper objectMapper;

    public RtgsAspectHelper(RtgsLogger rtgsLogger, ObjectMapper objectMapper) {
        this.rtgsLogger = rtgsLogger;
        this.objectMapper = objectMapper;
    }

    public String getRequestDetails(JoinPoint joinPoint) {
        String result = null;
        try {
            result = String.format("\nClass: %s\nMethod: %s\nArguments: %s\n", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), objectMapper.writeValueAsString(joinPoint.getArgs()));
        } catch (Exception ex) {
            rtgsLogger.error("Could not convert to json", ex);
        }
        return result;
    }

    public String getResponseDetails(Object response) {
        String result = null;
        try {
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            result = String.format("\nReturn: %s\n", objectMapper.writeValueAsString(response));
        } catch (Exception ex) {
            rtgsLogger.error("Could not convert to json", ex);
        }
        return result;
    }
}
