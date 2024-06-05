package com.cbl.cityrtgs.test;

import com.cbl.cityrtgs.services.transaction.ReferenceNoGenerateService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class newSequenceGenerateScheduler {
    private final ReferenceNoGenerateService referenceNoGenerateService;

   // @Scheduled(cron = "*/10 * * * * *")
//    @Scheduled(cron = "0 0 0 * * *")
//    public void trackOverduePayments() {
//        System.out.println("Scheduled task running");
//        ReferenceGenerateResponse referenceGenerateResponse = referenceNoGenerateService.newSequenceCreate();
//    }
}
