package com.cbl.cityrtgs.schedulers;

import com.cbl.cityrtgs.services.scheduler.abstraction.SchedulerService;
import com.cbl.cityrtgs.test.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class TransactionScheduler {

    private final SchedulerService schedulerService;
    private final TestService testService;

//    @Async
//    @Scheduled(fixedDelay = 60000)
//    public void startInwardScheduler() {

      //  schedulerService.initiateInwardScheduler();
   // }

//    @Async
//    @Scheduled(fixedDelay = 60000)
//    public void startOutwardScheduler() {

      //  schedulerService.initiateOutwardScheduler();
  //  }

//    @Async
//    @Scheduled(fixedDelay = 60000)
//    public void processInwardScheduler() {
//
//        testService.processInwardScheduler();
//    }
}