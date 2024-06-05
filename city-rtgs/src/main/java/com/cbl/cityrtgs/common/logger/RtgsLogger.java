package com.cbl.cityrtgs.common.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RtgsLogger {
    private final Logger traceLogger;
    private final Logger errorLogger;

    public RtgsLogger(String traceLoggerName, String errorLoggerName) {
        this.traceLogger = LoggerFactory.getLogger(traceLoggerName);
        this.errorLogger = LoggerFactory.getLogger(errorLoggerName);
    }
    //region error logger ==============================================================================================

    public void error(Exception exp) {
        errorLogger.error(exp.getMessage());
    }

    public void error(String exp) {
        errorLogger.error(exp);
    }

    public void error(String message, Exception exp) {
        errorLogger.error(message, exp);
    }

    public void warn(String message) {
        errorLogger.warn(message);
    }

//    public void warn(Exception exp) {
//        errorLogger.warn(exp);
//    }

    public void warn(String message, Exception exp) {
        errorLogger.warn(message, exp);
    }

    //endregion

    //region trace logger ==============================================================================================

    public void info(String message) {
        traceLogger.info(message);
    }

    public void debug(String message) {
        traceLogger.debug(message);
    }

    public void trace(String message) {
        traceLogger.trace(message);
    }
    //endregion
}
