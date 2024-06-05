package com.cbl.cityrtgs.services.si.implementation;

import com.cbl.cityrtgs.models.entitymodels.si.SiUpcomingItem;
import com.cbl.cityrtgs.repositories.si.SiUpcomingItemRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.CustomerFndTransferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Service
public class SiUpcomingItemService {

    private final SiUpcomingItemRepository siUpcomingItemRepository;

    public void updateSiNextDate(SiUpcomingItem siUpcomingItem, LocalDate nextDate) {
       // customerFndTransferRepository.findById(siUpcomingItem.getCustomerFundTransfer().getId());
        siUpcomingItem.setExecutionDate(nextDate);
        siUpcomingItem.setDeferredDate(nextDate);
        siUpcomingItem.setIsFired(false);
       // siUpcomingItem.setCustomerFundTransfer(customerFndTransferRepository.findById(siUpcomingItem.getCustomerFundTransfer().getId()).get());

        try {
            siUpcomingItemRepository.save(siUpcomingItem);
            log.info("SI Date Updated: {}", siUpcomingItem.getId());
        } catch (Exception e) {
            log.error("SI Date Update error: {}", e.getMessage());
        }
    }
}
