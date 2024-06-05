package com.cbl.cityrtgs;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import java.util.TimeZone;


@Slf4j
@EnableAsync
@SpringBootApplication
public class CityRtgsApplication {
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+6:00"));

    }

    public static void main(String[] args) {
      //  disableConsoleLogging();
        SpringApplication.run(CityRtgsApplication.class, args);
        log.info("----------------- Hello from City RTGS -----------------)");
    }

    private static void disableConsoleLogging() {
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.OFF);
    }
}
