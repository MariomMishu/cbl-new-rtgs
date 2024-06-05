package com.cbl.cityrtgs.services.si.implementation;

import com.cbl.cityrtgs.common.enums.ResponseCodeEnum;
import com.cbl.cityrtgs.common.enums.Status;
import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.models.dto.transaction.CbsResponse;
import com.cbl.cityrtgs.models.entitymodels.messagelog.MsgLogEntity;
import com.cbl.cityrtgs.models.entitymodels.si.SiConfiguration;
import com.cbl.cityrtgs.models.entitymodels.si.SiHistory;
import com.cbl.cityrtgs.models.entitymodels.si.SiUpcomingItem;
import com.cbl.cityrtgs.models.entitymodels.transaction.CbsTxnLogEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.si.SiHistoryRepository;
import com.cbl.cityrtgs.repositories.si.SiUpcomingItemRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.CustomerFndTransferRepository;
import com.cbl.cityrtgs.services.si.utility.SiUtility;
import com.cbl.cityrtgs.services.transaction.c2c.CustomerFundTransferService;
import com.cbl.cityrtgs.services.user.UserInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiTransactionService {

    private final SiUpcomingItemRepository siUpcomingItemRepository;
    private final CustomerFundTransferService customerFundTransferService;
    private final SiHistoryRepository siHistoryRepository;
    private final SiUpcomingItemService siUpcomingItemService;
    private final SiHistoryService siHistoryService;
    private final CustomerFndTransferRepository customerFndTransferRepository;
    private final UserInfoService userInfoService;


    public void performTransaction(Long interCustomerFundTransferId, SiUpcomingItem siUpcomingItem) {

        try {

            SiConfiguration configuration = siUpcomingItem.getSiConfiguration();

            LocalDate nextDate = calculateNextFireDate(siUpcomingItem);

            ResponseDTO response = customerFundTransferService.executeTransaction(
                    interCustomerFundTransferId,
                    configuration.getAmountType().getType(),
                    configuration.getCreatedBy());

            // CbsTxnLogEntity cbsTxnLogEntity = (CbsTxnLogEntity) response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            CbsResponse cbsResponse = objectMapper.convertValue(response.getBody(), CbsResponse.class);
            siHistoryService.save(siUpcomingItem,
                    !response.isError() ? Status.SUCCESS.name() : Status.FAILED.name(),
                    cbsResponse.getResponseCode(),
                    cbsResponse.getResponseMessage(),
                    nextDate);

            if (cbsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                CustomerFndTransferEntity entity = customerFndTransferRepository.findById(siUpcomingItem.getCustomerFundTransfer().getId()).get();
                entity.setVoucherNumber(cbsResponse.getTransactionRefNumber());
                entity.setTransactionStatus("Pending");
                entity.setVerificationStatus("Approved");
                entity.setSettlementDate(new Date());
                entity.setTransactionDate(new Date());
                siUpcomingItem.setCustomerFundTransfer(entity);
            }

            siUpcomingItemService.updateSiNextDate(siUpcomingItem, nextDate);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
        }
    }

    public void reprocessTransaction(Long interCustomerFundTransferId, SiHistory siHistory) {

        try {
            UserInfoEntity userInfoDetails = userInfoService.getEntityById(LoggedInUserDetails.getUserInfoDetails().getId());
            ResponseDTO response = customerFundTransferService.executeTransaction(interCustomerFundTransferId, siHistory.getSiamountType().getType(), userInfoDetails);

            CbsTxnLogEntity cbsTxnLogEntity = (CbsTxnLogEntity) response.getBody();
            siHistory.setStatus(!response.isError() ? Status.SUCCESS.name() : Status.FAILED.name());
            siHistory.setResponseCode(cbsTxnLogEntity.getCbsResponseCode());
            siHistory.setMessage(cbsTxnLogEntity.getCbsResponseMessage());
            siHistory.setIsFired(false);
            siHistoryRepository.save(siHistory);

        } catch (Exception e) {
            log.error("{}", e.getMessage());

            siHistory.setIsFired(false);
            siHistoryRepository.save(siHistory);
        }
    }

    public LocalDate calculateNextFireDate(SiUpcomingItem siUpcomingItem) {

        String frequency = siUpcomingItem.getSiConfiguration().getSiFrequency().getFrequency();
        LocalDate nextDate = null;

        if (frequency.equalsIgnoreCase("DAILY")) {

            nextDate = SiUtility.deferDateByOneDay(siUpcomingItem.getDeferredDate());
        } else if (frequency.equalsIgnoreCase("WEEKLY")) {

            nextDate = SiUtility.deferDateByOneWeek(siUpcomingItem.getDeferredDate());
        } else if (frequency.equalsIgnoreCase("MONTHLY")) {
            nextDate = SiUtility.deferDateByOneMonth(siUpcomingItem.getDeferredDate());
        }

        return nextDate;
    }

    public void reactivateSiUpcomingItem(SiUpcomingItem siUpcomingItem) {

        try {
            siUpcomingItem.setIsFired(false);
            siUpcomingItemRepository.save(siUpcomingItem);
        } catch (Exception e) {
            log.error("SI reactivation error: {}", e.getMessage());
        }
    }
}
