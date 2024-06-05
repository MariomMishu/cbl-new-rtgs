package com.cbl.cityrtgs.services.si.facade;

import com.cbl.cityrtgs.models.entitymodels.si.SiUpcomingItem;
import com.cbl.cityrtgs.services.si.facade.instructions.DailyStandingInstruction;
import com.cbl.cityrtgs.services.si.facade.instructions.MonthlyStandingInstruction;
import com.cbl.cityrtgs.services.si.facade.instructions.WeeklyStandingInstruction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StandingInstructionDriver {

    private final DailyStandingInstruction dailyStandingInstruction;
    private final WeeklyStandingInstruction weeklyStandingInstruction;
    private final MonthlyStandingInstruction monthlyStandingInstruction;

    public void executeDailySi(SiUpcomingItem siUpcomingItem, Long interCustomerFundTransferId){
        dailyStandingInstruction.executeStandingInstruction(siUpcomingItem, interCustomerFundTransferId);
    }

    public void executeWeeklySi(SiUpcomingItem siUpcomingItem, Long interCustomerFundTransferId){
        weeklyStandingInstruction.executeStandingInstruction(siUpcomingItem, interCustomerFundTransferId);
    }

    public void executeMonthlySi(SiUpcomingItem siUpcomingItem, Long interCustomerFundTransferId){
        monthlyStandingInstruction.executeStandingInstruction(siUpcomingItem, interCustomerFundTransferId);
    }
}
