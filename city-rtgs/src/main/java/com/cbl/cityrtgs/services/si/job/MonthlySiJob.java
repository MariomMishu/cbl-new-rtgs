package com.cbl.cityrtgs.services.si.job;

import com.cbl.cityrtgs.models.entitymodels.si.SiUpcomingItem;
import com.cbl.cityrtgs.services.si.implementation.SiTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
public class MonthlySiJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) {

        JobDataMap dataMap = context.getMergedJobDataMap();

        SiTransactionService siTransactionService = (SiTransactionService) dataMap.get("siTransactionService");
        SiUpcomingItem siUpcomingItem = (SiUpcomingItem) dataMap.get("upcomingSiItem");
        Long interCustomerFundTransferId = (Long) dataMap.get("interCustomerFundTransferId");

        try {
            siTransactionService.performTransaction(interCustomerFundTransferId, siUpcomingItem);

        } catch (Exception e) {

            log.error("Could not perform transaction: {}", e.getMessage());
            siTransactionService.reactivateSiUpcomingItem(siUpcomingItem);
        }

    }

}
