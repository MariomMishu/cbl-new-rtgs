package com.cbl.cityrtgs.services.si.facade.instructions;

import com.cbl.cityrtgs.models.entitymodels.si.SiUpcomingItem;
import com.cbl.cityrtgs.repositories.si.SiUpcomingItemRepository;
import com.cbl.cityrtgs.services.si.implementation.SiTransactionService;
import com.cbl.cityrtgs.services.si.facade.SiFacade;
import com.cbl.cityrtgs.services.si.job.MonthlySiJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;


@Slf4j
@RequiredArgsConstructor
@Component
public class MonthlyStandingInstruction implements SiFacade {

    private final SiUpcomingItemRepository siUpcomingItemRepository;
    private final SiTransactionService siTransactionService;

    @Override
    public void executeStandingInstruction(SiUpcomingItem siUpcomingItem, Long interCustomerFundTransferId) {

        try {

            JobDataMap map = new JobDataMap();
            map.put("interCustomerFundTransferId", interCustomerFundTransferId);
            map.put("siTransactionService", siTransactionService);
            map.put("upcomingSiItem", siUpcomingItem);

            log.info("Scheduling Monthly SI JOB: {}", siUpcomingItem.getId());
            String jobId = "SI-" + siUpcomingItem.getId();
            String triggerId = "Trigger-" + siUpcomingItem.getId();
            String group = siUpcomingItem.getSiConfiguration().getSiFrequency().getFrequency();

            JobDetail job = newJob(MonthlySiJob.class)
                    .usingJobData(map)
                    .withIdentity(jobId, group)
                    .build();

            SimpleTrigger trigger = (SimpleTrigger) newTrigger()
                    .withIdentity(triggerId, group)
                    .startNow()
                    .build();

            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();

            if(!scheduler.checkExists(job.getKey())){

                scheduler.start();
                scheduler.scheduleJob(job, trigger);

                siUpcomingItem.setIsFired(true);
                siUpcomingItemRepository.save(siUpcomingItem);
            }

        } catch (SchedulerException e) {

            log.error("{}", e.getMessage());

            siUpcomingItem.setIsFired(false);
            siUpcomingItemRepository.save(siUpcomingItem);
        }
    }
}
