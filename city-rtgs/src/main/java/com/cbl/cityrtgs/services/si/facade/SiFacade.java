package com.cbl.cityrtgs.services.si.facade;

import com.cbl.cityrtgs.models.entitymodels.si.SiUpcomingItem;

public interface SiFacade {

    void executeStandingInstruction(SiUpcomingItem siUpcomingItem, Long interCustomerFundTransferId);
}
