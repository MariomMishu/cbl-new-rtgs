package com.cbl.cityrtgs.config;

import com.cbl.cityrtgs.common.logger.RtgsLogger;
import com.cbl.cityrtgs.common.logger.StpLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CommonBeanConfig {
    //region app logger ================================================================================================
    @Bean
    public RtgsLogger getAppLogger(@Value("${app.logger.trace}") String traceLoggerName, @Value("${app.logger.error}") String errorLoggerName) {
        return new RtgsLogger(traceLoggerName, errorLoggerName);
    }

    @Bean
    public StpLogger getStpAppLogger(@Value("${app.logger.stp.trace}") String traceLoggerName, @Value("${app.logger.stp.error}") String errorLoggerName) {
        return new StpLogger(traceLoggerName, errorLoggerName);
    }
    //endregion
}
