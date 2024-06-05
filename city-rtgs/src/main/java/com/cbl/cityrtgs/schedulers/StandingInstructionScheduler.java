package com.cbl.cityrtgs.schedulers;

import com.cbl.cityrtgs.services.si.implementation.SiService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class StandingInstructionScheduler {

    private final SiService siService;

    @Async
    //s m h d m weekday
    //@Scheduled(fixedRate  = 7000, initialDelay = 8000)
    //  @Scheduled(cron = "0 45 11 * * *")
    @Scheduled(cron = "*/6600000 * * * * *")
    // @Scheduled(cron = "*/1000 * * * * *")
    public void startSIScheduler() {

        siService.startSiExecution();
    }
}
