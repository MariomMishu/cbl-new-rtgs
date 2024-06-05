package com.cbl.cityrtgs.services.si.implementation;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.projection.SIProjection;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.models.dto.si.*;
import com.cbl.cityrtgs.models.dto.transaction.TransactionResponse;
import com.cbl.cityrtgs.models.dto.transaction.c2c.CustomerFndTransfer;
import com.cbl.cityrtgs.models.dto.transaction.c2c.CustomerFndTransferTxn;
import com.cbl.cityrtgs.models.dto.transaction.c2c.PayerDetailsResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.CurrencyEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.TxnCfgSetupEntity;
import com.cbl.cityrtgs.models.entitymodels.si.*;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.InterCustomerFundTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import com.cbl.cityrtgs.repositories.configuration.*;
import com.cbl.cityrtgs.repositories.si.*;
import com.cbl.cityrtgs.repositories.transaction.c2c.CustomerFndTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.InterCustomerFundTransferRepository;
import com.cbl.cityrtgs.services.si.facade.StandingInstructionDriver;
import com.cbl.cityrtgs.services.si.utility.SiUtility;
import com.cbl.cityrtgs.services.transaction.CustomerAccountDetailsService;
import com.cbl.cityrtgs.services.transaction.c2c.CustomerFundTransferService;
import com.cbl.cityrtgs.repositories.specification.RejectedSISpecification;
import com.cbl.cityrtgs.repositories.specification.SISpecification;
import com.cbl.cityrtgs.repositories.specification.SubmittedSISpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiService {

    private final CurrencyRepository currencyRepository;
    private final SiFrequencyRepository siFrequencyRepository;
    private final SiConfigurationRepository siConfigurationRepository;
    private final SiUpcomingItemRepository siUpcomingItemRepository;
    private final SiHistoryRepository siHistoryRepository;
    private final SiAmountTypeRepository siAmountTypeRepository;
    private final TxnCfgSetupRepository txnCfgSetupRepository;
    private final UserInfoRepository userInfoRepository;
    private final InterCustomerFundTransferRepository interCustomerFundTransferRepository;
    private final SiTransactionService siTransactionService;
    private final CustomerFundTransferService customerFundTransferService;
    private final StandingInstructionDriver standingInstructionDriver;
    private final CustomerAccountDetailsService customerAccountDetailsService;
    private final BranchRepository branchRepository;
    private final BankRepository bankRepository;
    private final CustomerFndTransferRepository customerFndTransferRepository;


    public ResponseDTO create(SiRequest request) {

        if (request.getCurrencyShortCode() == null) {

            return ResponseDTO.builder()
                    .error(true)
                    .message("Please provide SI currency code!")
                    .build();
        }

        if (request.getAmountTypeId() == null) {

            return ResponseDTO.builder()
                    .error(true)
                    .message("Please provide SI Amount Type!")
                    .build();
        }

        if (request.getStartDate() == null) {

            return ResponseDTO.builder()
                    .error(true)
                    .message("Please provide SI start date!")
                    .build();
        }

        if (request.getExpiryDate() == null) {

            return ResponseDTO.builder()
                    .error(true)
                    .message("Please provide SI expiry date!")
                    .build();
        }

        if (request.getFrequencyId() == null) {

            return ResponseDTO.builder()
                    .error(true)
                    .message("Please provide SI frequency type!")
                    .build();
        } else {

            if (request.getFrequencyId() == 1 && request.getFireDay() == null) {

                return ResponseDTO.builder()
                        .error(true)
                        .message("Please provide SI daily frequency!")
                        .build();
            }

            if (request.getFrequencyId() == 2 && request.getFireDay() == null) {

                return ResponseDTO.builder()
                        .error(true)
                        .message("Please provide SI weekly frequency!")
                        .build();
            }

            if (request.getFrequencyId() == 3 && request.getMonthlyFireDay() == null) {

                return ResponseDTO.builder()
                        .error(true)
                        .message("Please provide SI monthly frequency!")
                        .build();
            }
        }

        if (request.getTransactionTypeCode() == null) {

            return ResponseDTO.builder()
                    .error(true)
                    .message("Please provide SI transaction type code!")
                    .build();
        }

        if (request.getActive() == null) {

            return ResponseDTO.builder()
                    .error(true)
                    .message("Please provide SI status!")
                    .build();
        }

        LocalDate startDate = LocalDate.parse(request.getStartDate());

        if (!startDate.equals(LocalDate.now())) {
            if (startDate.isBefore(LocalDate.now())) {

                return ResponseDTO.builder()
                        .error(true)
                        .message("SI start date can not be earlier than present day!")
                        .build();
            }
        }

        SiFrequency siFrequency = siFrequencyRepository.findById(request.getFrequencyId()).get();
        var currency = currencyRepository.findByShortCodeAndIsDeletedFalse(request.getCurrencyShortCode()).get();
        SiAmountType siAmountType = siAmountTypeRepository.findById(request.getAmountTypeId()).get();
        UserInfoEntity createdBy = LoggedInUserDetails.getUserInfoDetails();

        SiConfiguration siConfiguration = SiRequest.toSiConfigurationModel(request);
        siConfiguration.setCreatedBy(createdBy);
        siConfiguration.setSiFrequency(siFrequency);
        siConfiguration.setCurrency(currency);
        siConfiguration.setAmountType(siAmountType);
        siConfiguration.setDepartment(createdBy.getDept() != null ? createdBy.getDept() : null);

        try {
            SiConfiguration newSiConfig = siConfigurationRepository.save(siConfiguration);

            SiUpcomingItem siUpcomingItem = SiRequest.toModel(request);
            siUpcomingItem.setSiConfiguration(newSiConfig);

            String frequency = newSiConfig.getSiFrequency().getFrequency();

            if (frequency.equalsIgnoreCase("DAILY")) {

                LocalDate executionDate = SiUtility.calculateNextFireDate(siConfiguration.getStartDate());
                siUpcomingItem.setExecutionDate(executionDate);
                siUpcomingItem.setDeferredDate(executionDate);
            } else if (frequency.equalsIgnoreCase("WEEKLY")) {

                if ((request.getFireDay() > 0) && (request.getFireDay() < 8)) {

                    LocalDate executionDate = SiUtility.calculateNextWeeklyFireDay(startDate, request.getFireDay());
                    siUpcomingItem.setExecutionDate(executionDate);
                    siUpcomingItem.setDeferredDate(executionDate);
                } else {
                    return ResponseDTO.builder()
                            .error(true)
                            .message("Please provide SI Weekly Fire Day !")
                            .build();
                }
            } else if (frequency.equalsIgnoreCase("MONTHLY")) {

                if ((request.getMonthlyFireDay() > 0) && (request.getMonthlyFireDay() < 32)) {

                    LocalDate executionDate = SiUtility.calculateNextMonthlyFireDate(startDate, request.getMonthlyFireDay());
                    siUpcomingItem.setExecutionDate(executionDate);
                    siUpcomingItem.setDeferredDate(executionDate);
                } else {
                    return ResponseDTO.builder()
                            .error(true)
                            .message("Please provide SI Monthly Fire Day !")
                            .build();
                }
            }

            siUpcomingItem = siUpcomingItemRepository.save(siUpcomingItem);

            String benBank = "";
            String benBranch = "";

            if (bankRepository.findByIdAndIsDeletedFalse(siUpcomingItem.getBeneficiaryBankId()).isPresent()) {
                benBank = bankRepository.findByIdAndIsDeletedFalse(siUpcomingItem.getBeneficiaryBankId()).get().getName();
            }

            if (branchRepository.findByIdAndIsDeletedFalse(siUpcomingItem.getBeneficiaryBranchId()).isPresent()) {
                benBranch = branchRepository.findByIdAndIsDeletedFalse(siUpcomingItem.getBeneficiaryBranchId()).get().getName();
            }

            SiResponse response = SiResponse.toDto(siUpcomingItem, benBank, benBranch);

            return ResponseDTO.builder()
                    .error(false)
                    .message("SI Submitted! Reference Number: " + response.getId())
                    .body(response)
                    .build();
        } catch (Exception e) {

            log.error("{}", e.getMessage());

            return ResponseDTO.builder()
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }

    }


    private ResponseDTO createTransaction(SiUpcomingItem si) {
        List<CustomerFndTransferTxn> txnList = new ArrayList<>();
        try {

           // CustomerFundTransferTransaction customerFundTransferTransaction = CustomerFundTransferTransaction.toModel(si);
            CustomerFndTransferTxn customerFundTransferTransaction = CustomerFundTransferTransaction.toTXnModel(si);

            if (si.getSiConfiguration().getAmountType().getType().equals("FULLBALANCE")) {

                PayerDetailsResponse payerDetailsResponse = customerAccountDetailsService.getAccountDetails(customerFundTransferTransaction.getPayerAccNo());

                if (!payerDetailsResponse.getResponseCode().equals("100")) {

                    return ResponseDTO.builder()
                            .error(true)
                            .message("Could not bring account detail!")
                            .build();
                }
                customerFundTransferTransaction.setAmount(BigDecimal.valueOf(Double.parseDouble(payerDetailsResponse.getAvailBalance())));
            }

//            CustomerFundTransfer customerFundTransfer = CustomerFundTransfer.builder()
//                    .priorityCode("0015")
//                    .transactionTypeCode(si.getTransactionTypeCode())
//                    .currencyId(si.getSiConfiguration().getCurrency().getId())
//                    .customerFundTransferTransaction(customerFundTransferTransaction)
//                    .build();
            txnList.add(customerFundTransferTransaction);
            CustomerFndTransfer fndTransfer=new CustomerFndTransfer();
            fndTransfer.setPriorityCode("0015");
            fndTransfer.setTxnTypeCode(si.getTransactionTypeCode());
            fndTransfer.setCurrencyId(si.getSiConfiguration().getCurrency().getId());
            fndTransfer.setCustomerFndTransferTxns(txnList);
           // ResponseDTO response = customerFundTransferService.createCustomerFundTransferTransaction(customerFundTransfer, si.getSiConfiguration().getCreatedBy());
            TransactionResponse response = customerFundTransferService.createCustomerFundTransferTransaction(fndTransfer, si.getSiConfiguration().getCreatedBy());

            if (response.isError()) {

                return ResponseDTO.builder()
                        .error(true)
                        .message(response.getMessage())
                        .build();
            }

           // CustomerFndTransferEntity entity = (CustomerFndTransferEntity) response.getBody();
            CustomerFndTransferEntity entity = customerFndTransferRepository.findByParentBatchNumber((String)response.getBody()).get(0);
            si.setCustomerFundTransfer(entity);
            si = siUpcomingItemRepository.save(si);

            entity.setSourceReference(si.getId());

            return ResponseDTO.builder()
                    .error(false)
                    .body(si)
                    .build();
        } catch (Exception e) {

            log.error("SI Transaction creation failed! {}", e.getMessage());

            return ResponseDTO.builder()
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }
    }


    public ResponseDTO edit(Long id, SiEditRequest request) {

        if (request.getCurrencyShortCode() == null) {

            return ResponseDTO.builder()
                    .error(true)
                    .message("Please provide SI currency code!")
                    .build();
        }

        if (request.getAmountTypeId() == null) {

            return ResponseDTO.builder()
                    .error(true)
                    .message("Please provide SI Amount Type!")
                    .build();
        }

        if (request.getStartDate() == null) {

            return ResponseDTO.builder()
                    .error(true)
                    .message("Please provide SI start date!")
                    .build();
        }

        if (request.getExpiryDate() == null) {

            return ResponseDTO.builder()
                    .error(true)
                    .message("Please provide SI expiry date!")
                    .build();
        }

        if (request.getFrequencyId() == null) {

            return ResponseDTO.builder()
                    .error(true)
                    .message("Please provide SI frequency type!")
                    .build();
        } else {

            if (request.getFrequencyId() == 1 && request.getFireDay() == null) {

                return ResponseDTO.builder()
                        .error(true)
                        .message("Please provide SI daily frequency!")
                        .build();
            }

            if (request.getFrequencyId() == 2 && request.getFireDay() == null) {

                return ResponseDTO.builder()
                        .error(true)
                        .message("Please provide SI weekly frequency!")
                        .build();
            }

            if (request.getFrequencyId() == 3 && request.getMonthlyFireDay() == null) {

                return ResponseDTO.builder()
                        .error(true)
                        .message("Please provide SI monthly frequency!")
                        .build();
            }
        }

        if (request.getTransactionTypeCode() == null) {

            return ResponseDTO.builder()
                    .error(true)
                    .message("Please provide SI transaction type code!")
                    .build();
        }

        if (request.getActive() == null) {

            return ResponseDTO.builder()
                    .error(true)
                    .message("Please provide SI status!")
                    .build();
        }

        LocalDate startDate = LocalDate.parse(request.getStartDate());
//
//        if (!startDate.equals(LocalDate.now())) {
//
//            if (startDate.isBefore(LocalDate.now())) {
//
//                return ResponseDTO.builder()
//                        .error(true)
//                        .message("SI start date can not be earlier than present day")
//                        .build();
//            }
//        }

        Optional<SiUpcomingItem> optional = siUpcomingItemRepository.findById(id);

        if (optional.isEmpty()) {

            return ResponseDTO.builder()
                    .error(false)
                    .message("SI not found!")
                    .build();
        }

        try {

            SiUpcomingItem oldSi = optional.get();
            var amount = request.getAmount();
            SiFrequency siFrequency = siFrequencyRepository.findById(request.getFrequencyId()).get();
            CurrencyEntity currency = currencyRepository.findByShortCodeAndIsDeletedFalse(request.getCurrencyShortCode()).get();
            SiAmountType siAmountType = siAmountTypeRepository.findById(request.getAmountTypeId()).get();
            UserInfoEntity createdBy = userInfoRepository.findById(LoggedInUserDetails.getUserInfoDetails().getId()).get();

            SiConfiguration siConfiguration = SiEditRequest.toSiConfigurationModel(oldSi.getSiConfiguration(), request);
            siConfiguration.setCurrency(currency);
            siConfiguration.setSiFrequency(siFrequency);
            siConfiguration.setAmountType(siAmountType);
            siConfiguration.setCreatedBy(createdBy);
            SiConfiguration updatedSiConfig = siConfigurationRepository.save(siConfiguration);

            oldSi.setSiConfiguration(updatedSiConfig);

            oldSi = SiEditRequest.toSiUpcomingItemModel(oldSi, request);

            String frequency = updatedSiConfig.getSiFrequency().getFrequency();

            if (frequency.equalsIgnoreCase("DAILY")) {

                LocalDate executionDate = SiUtility.calculateNextFireDate(siConfiguration.getStartDate());
                oldSi.setExecutionDate(executionDate);
                oldSi.setDeferredDate(executionDate);
            } else if (frequency.equalsIgnoreCase("WEEKLY")) {

                if ((request.getFireDay() > 0) && (request.getFireDay() < 8)) {

                    LocalDate executionDate = SiUtility.calculateNextWeeklyFireDay(startDate, request.getFireDay());
                    oldSi.setExecutionDate(executionDate);
                    oldSi.setDeferredDate(executionDate);
                } else {
                    return ResponseDTO.builder()
                            .error(true)
                            .message("Please provide a valid SI Weekly Fire Day !")
                            .build();
                }
            } else if (frequency.equalsIgnoreCase("MONTHLY")) {

                if ((request.getMonthlyFireDay() > 0) && (request.getMonthlyFireDay() < 32)) {

                    LocalDate executionDate = SiUtility.calculateNextMonthlyFireDate(startDate, request.getMonthlyFireDay());
                    oldSi.setExecutionDate(executionDate);
                    oldSi.setDeferredDate(executionDate);
                } else {
                    return ResponseDTO.builder()
                            .error(true)
                            .message("Please provide a valid SI Monthly Fire Day !")
                            .build();
                }
            }

            String benBank = "";
            String benBranch = "";

            if (bankRepository.findByIdAndIsDeletedFalse(oldSi.getBeneficiaryBankId()).isPresent()) {
                benBank = bankRepository.findByIdAndIsDeletedFalse(oldSi.getBeneficiaryBankId()).get().getName();
            }

            if (branchRepository.findByIdAndIsDeletedFalse(oldSi.getBeneficiaryBranchId()).isPresent()) {
                benBranch = branchRepository.findByIdAndIsDeletedFalse(oldSi.getBeneficiaryBranchId()).get().getName();
            }

            return ResponseDTO.builder()
                    .error(false)
                    .body(SiResponse.toDto(siUpcomingItemRepository.save(oldSi), benBank, benBranch))
                    .build();
        } catch (Exception e) {

            log.error("{}", e.getMessage());

            return ResponseDTO.builder()
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }
    }


    public void startSiExecution() {

        try {

            //      List<SiUpcomingItem> scheduledTransactionLists = siUpcomingItemRepository.findAllCandidateSIsForToday();
            List<SIProjection> scheduledTransactionLists = siUpcomingItemRepository.findAllCandidateSIsForToday();

            if (!scheduledTransactionLists.isEmpty()) {

                log.info("Total Number of SI: {}", scheduledTransactionLists.size());

                scheduledTransactionLists.forEach(item -> {
                    //  upcomingSiItem
                    // SiConfiguration siConfiguration = upcomingSiItem.getSiConfiguration();
                    Optional<SiConfiguration> optionalSiConfiguration = siConfigurationRepository.findById(item.getSiConfigId());
                    Optional<SiUpcomingItem> optionalUpcomingSiItem = siUpcomingItemRepository.findById(item.getSiUpcomId());
                    if (optionalSiConfiguration.isPresent()) {
                        SiConfiguration siConfiguration = optionalSiConfiguration.get();

                        Optional<TxnCfgSetupEntity> optional = txnCfgSetupRepository.findByCurrencyIdAndIsDeletedFalse(siConfiguration.getCurrency().getId());
                        if (optional.isPresent() && optionalUpcomingSiItem.isPresent()) {

                            TxnCfgSetupEntity txnCfgSetupEntity = optional.get();
                            SiUpcomingItem upcomingSiItem = optionalUpcomingSiItem.get();

                            if (txnCfgSetupEntity.getTxnActive()) {

                                boolean isRestricted = txnCfgSetupEntity.getTimeRestricted();

                                Map<String, Integer> timeMap = SiUtility.getExecutionTime(
                                        txnCfgSetupEntity.getStartTime(),
                                        txnCfgSetupEntity.getEndTime(),
                                        isRestricted ? 10 : 30
                                );

                                Date startTime = SiUtility.getExecutionDateTime(timeMap.get("startHour"), timeMap.get("startMinute"));
                                Date endTime = SiUtility.getExecutionDateTime(timeMap.get("endHour"), timeMap.get("endMinute"));
                                Date currentTime = SiUtility.getCurrentDateTime();

                             //   if (currentTime.after(startTime) && currentTime.before(endTime)) {
                                    initiateSiProcess(upcomingSiItem);
//                                } else {
//                                    log.info("Deferring SI by 1 Day due to out of timeframe...");
//                                    LocalDate deferDate = SiUtility.deferDateByOneDay(upcomingSiItem.getDeferredDate());
//                                    upcomingSiItem.setExecutionDate(deferDate);
//                                    upcomingSiItem.setDeferredDate(deferDate);
//                                    upcomingSiItem.setIsFired(false);
//                                    siUpcomingItemRepository.save(upcomingSiItem);
//                                }
                            } else {

                                log.info("Currency Inactive...");
                                log.info("Deferring SI by 1 Day...");
                                LocalDate deferDate = SiUtility.deferDateByOneDay(upcomingSiItem.getDeferredDate());
                                upcomingSiItem.setExecutionDate(deferDate);
                                upcomingSiItem.setDeferredDate(deferDate);
                                upcomingSiItem.setIsFired(false);
                                siUpcomingItemRepository.save(upcomingSiItem);
                            }
                        } else {
                            log.error("SI Currency not found!");
                        }
                    }


                });
            }
        } catch (Exception e) {
            log.error("{}", e.getMessage());
        }
    }

    private void initiateSiProcess(SiUpcomingItem siUpcomingItem) {

        ResponseDTO response = createTransaction(siUpcomingItem);

        if (response.isError()) {
            log.error("Could not initiate SI transaction: {}", response.getMessage());
        } else {

            siUpcomingItem = (SiUpcomingItem) response.getBody();

            String siFrequency = siUpcomingItem.getSiConfiguration().getSiFrequency().getFrequency();
            String batchNumber = siUpcomingItem.getCustomerFundTransfer().getParentBatchNumber();

            Optional<InterCustomerFundTransferEntity> optional = interCustomerFundTransferRepository.findByBatchNumberAndIsDeletedFalse(batchNumber);

            if (optional.isEmpty()) {
                log.error("Invalid batch: {}", batchNumber);
            } else {

                Long id = optional.get().getId();

                siUpcomingItem.setIsFired(true);
                siUpcomingItemRepository.save(siUpcomingItem);

                if (siFrequency.equalsIgnoreCase("DAILY")) {

                    log.info("DAILY....");
                    standingInstructionDriver.executeDailySi(siUpcomingItem, id);
                } else if (siFrequency.equalsIgnoreCase("WEEKLY")) {

                    log.info("WEEKLY....");
                    standingInstructionDriver.executeWeeklySi(siUpcomingItem, id);
                } else if (siFrequency.equalsIgnoreCase("MONTHLY")) {

                    log.info("MONTHLY...");
                    standingInstructionDriver.executeMonthlySi(siUpcomingItem, id);
                } else {
                    log.error("SI ruleset is not defined! {}", siFrequency);
                }
            }
        }
    }


    public ResponseDTO activate(long id) {

        Optional<SiUpcomingItem> optional = siUpcomingItemRepository.findById(id);

        if (optional.isEmpty()) {
            return ResponseDTO.builder()
                    .error(true)
                    .message("SI not found!")
                    .build();
        }

        SiUpcomingItem siUpcomingItem = optional.get();

        if (!siUpcomingItem.getExecutionState().equals("APPROVED")) {

            return ResponseDTO.builder()
                    .error(true)
                    .message("SI not approved!")
                    .build();
        }

        try {
            siUpcomingItemRepository.activate(id);

            return ResponseDTO.builder()
                    .error(false)
                    .message("SI activated")
                    .build();
        } catch (Exception e) {
            return ResponseDTO.builder()
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }
    }


    public ResponseDTO deactivate(long id) {

        Optional<SiUpcomingItem> optional = siUpcomingItemRepository.findById(id);

        if (optional.isEmpty()) {
            return ResponseDTO.builder()
                    .error(true)
                    .message("SI not found!")
                    .build();
        }

        SiUpcomingItem siUpcomingItem = optional.get();

        if (!siUpcomingItem.getExecutionState().equals("APPROVED")) {

            return ResponseDTO.builder()
                    .error(true)
                    .message("SI not approved!")
                    .build();
        }

        siUpcomingItem.setIsFired(false);

        try {
            siUpcomingItemRepository.deactivate(id);

            return ResponseDTO.builder()
                    .error(false)
                    .message("SI activated")
                    .build();
        } catch (Exception e) {
            return ResponseDTO.builder()
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }
    }


    public ResponseDTO getStandingInstructionDetails(long id) {

        Optional<SiUpcomingItem> optional = siUpcomingItemRepository.findById(id);

        if (optional.isPresent()) {

            SiUpcomingItem siUpcomingItem = optional.get();

            String benBank = "";
            String benBranch = "";

            if (bankRepository.findByIdAndIsDeletedFalse(siUpcomingItem.getBeneficiaryBankId()).isPresent()) {
                benBank = bankRepository.findByIdAndIsDeletedFalse(siUpcomingItem.getBeneficiaryBankId()).get().getName();
            }

            if (branchRepository.findByIdAndIsDeletedFalse(siUpcomingItem.getBeneficiaryBranchId()).isPresent()) {
                benBranch = branchRepository.findByIdAndIsDeletedFalse(siUpcomingItem.getBeneficiaryBranchId()).get().getName();
            }

            return ResponseDTO.builder()
                    .error(false)
                    .body(SiResponse.toDto(siUpcomingItem, benBank, benBranch))
                    .build();
        }

        return ResponseDTO.builder()
                .error(true)
                .message("SI not found!")
                .build();
    }


    public ResponseDTO reprocessStandingInstruction(long id) {

        try {

            Optional<SiHistory> siHistoryOptional = siHistoryRepository.findById(id);

            if (siHistoryOptional.isEmpty()) {
                return ResponseDTO.builder()
                        .error(true)
                        .message("SI not found")
                        .build();
            }

            SiHistory siHistory = siHistoryOptional.get();

            Map<String, Integer> timeMap;
            long currencyId = siHistory.getCurrency().getId();
            Optional<TxnCfgSetupEntity> optional = txnCfgSetupRepository.findByCurrencyIdAndIsDeletedFalse(currencyId);

            if (optional.isEmpty()) {
                return ResponseDTO.builder()
                        .error(true)
                        .message("Currency not found")
                        .build();
            }

            TxnCfgSetupEntity txnCfgSetupEntity = optional.get();

            if (txnCfgSetupEntity.getTxnActive()) {

                if (txnCfgSetupEntity.getTimeRestricted()) {
                    timeMap = SiUtility.getExecutionTime(txnCfgSetupEntity.getStartTime(), txnCfgSetupEntity.getEndTime(), 10);
                } else {
                    timeMap = SiUtility.getExecutionTime(txnCfgSetupEntity.getStartTime(), txnCfgSetupEntity.getEndTime(), 30);
                }

                Date startTime = SiUtility.getExecutionDateTime(timeMap.get("startHour"), timeMap.get("startMinute"));
                Date endTime = SiUtility.getExecutionDateTime(timeMap.get("endHour"), timeMap.get("endMinute"));
                Date currentTime = SiUtility.getCurrentDateTime();

                // if (currentTime.after(startTime) && currentTime.before(endTime)) {

                ResponseDTO response = reInitiateFailedSiProcess(siHistory);

                if (response.isError()) {
                    return ResponseDTO.builder()
                            .error(true)
                            .message(response.getMessage())
                            .build();
                }

                return ResponseDTO.builder()
                        .error(false)
                        .message("SI re-initiated!")
                        .build();
//                } else {
//                    return ResponseDTO.builder()
//                            .error(false)
//                            .message("Out of timeframe...")
//                            .build();
//                }
            } else {

                return ResponseDTO.builder()
                        .error(false)
                        .message("Deferring SI by 1 Day due to inactive currency...")
                        .build();
            }
        } catch (Exception e) {

            log.error("{}", e.getMessage());

            return ResponseDTO.builder()
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }
    }


    public ResponseDTO search(String name, String status, String state, String expiryDate,
                              String beneficiaryAccountNo, String payerAccountNo,
                              String currency, int pageNo, int pageSize, String sortBy, String sortOrder) {

        List<SiResponse> responses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        Sort sort;

        if (name.equals("") && status.equals("") && state.equals("") && expiryDate.equals("")
                && beneficiaryAccountNo.equals("") && payerAccountNo.equals("") && currency.equals("")) {

            response.put("result", responses);

            return ResponseDTO.builder()
                    .error(false)
                    .message("Please provide search parameter(s)")
                    .body(response)
                    .build();
        }

        if (!sortBy.equals("")) {
            sort = Sort.by(sortBy.toLowerCase());
        } else {
            sort = Sort.by("id");
        }

        if (sortOrder.equals("") || sortOrder.equals("desc")) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<SiUpcomingItem> pages;

        Specification<SiUpcomingItem> specification = siSearch(name, status, state, expiryDate, beneficiaryAccountNo, payerAccountNo, currency);

        pages = siUpcomingItemRepository.findAll(specification, pageable);

        pages.forEach(siUpcomingItem -> {

            String benBank = "";
            String benBranch = "";

            if (bankRepository.findByIdAndIsDeletedFalse(siUpcomingItem.getBeneficiaryBankId()).isPresent()) {
                benBank = bankRepository.findByIdAndIsDeletedFalse(siUpcomingItem.getBeneficiaryBankId()).get().getName();
            }

            if (branchRepository.findByIdAndIsDeletedFalse(siUpcomingItem.getBeneficiaryBranchId()).isPresent()) {
                benBranch = branchRepository.findByIdAndIsDeletedFalse(siUpcomingItem.getBeneficiaryBranchId()).get().getName();
            }

            responses.add(SiResponse.toDto(siUpcomingItem, benBank, benBranch));
        });

        response.put("result", responses);
        response.put("currentPage", pages.getNumber());
        response.put("totalItems", pages.getTotalElements());
        response.put("totalPages", pages.getTotalPages());

        return ResponseDTO.builder()
                .error(false)
                .message(responses.size() + " data found!")
                .body(response)
                .build();
    }

    @NotNull
    private Specification<SiUpcomingItem> siSearch(String name, String status, String state, String expiryDate, String beneficiaryAccountNo, String payerAccountNo, String currency) {

        Specification<SiUpcomingItem> specification = Specification.where(null);

        if (!name.equals("")) {

            specification = specification.and(SISpecification.hasName(name));
        }

        if (!status.equals("")) {

            specification = specification.and(SISpecification.hasStatus(status));
        }

        if (!state.equals("")) {

            specification = specification.and(SISpecification.hasState(state));
        }

        if (!expiryDate.equals("")) {

            specification = specification.and(SISpecification.hasExpiryDate(expiryDate));
        }

        if (!beneficiaryAccountNo.equals("")) {

            specification = specification.and(SISpecification.hasBeneficiaryAccount(beneficiaryAccountNo));
        }

        if (!payerAccountNo.equals("")) {

            specification = specification.and(SISpecification.hasPayerAccount(payerAccountNo));
        }

        if (!currency.equals("")) {

            specification = specification.and(SISpecification.hasCurrency(currency));
        }

        return specification;
    }

    public ResponseDTO searchSubmittedSI(String name, String status, String expiryDate,
                                         String beneficiaryAccountNo, String payerAccountNo,
                                         String currency, int pageNo, int pageSize, String sortBy, String sortOrder) {

        List<SiResponse> responses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        Sort sort;

        if (!sortBy.equals("")) {
            sort = Sort.by(sortBy.toLowerCase());
        } else {
            sort = Sort.by("id");
        }

        if (sortOrder.equals("") || sortOrder.equals("desc")) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Specification<SiUpcomingItem> specification = searchSubmitted(name, status, expiryDate, beneficiaryAccountNo, payerAccountNo, currency);

        Page<SiUpcomingItem> pages = siUpcomingItemRepository.findAll(specification, pageable);

        pages.forEach(siUpcomingItem -> {

            String benBank = "";
            String benBranch = "";

            if (bankRepository.findByIdAndIsDeletedFalse(siUpcomingItem.getBeneficiaryBankId()).isPresent()) {
                benBank = bankRepository.findByIdAndIsDeletedFalse(siUpcomingItem.getBeneficiaryBankId()).get().getName();
            }

            if (branchRepository.findByIdAndIsDeletedFalse(siUpcomingItem.getBeneficiaryBranchId()).isPresent()) {
                benBranch = branchRepository.findByIdAndIsDeletedFalse(siUpcomingItem.getBeneficiaryBranchId()).get().getName();
            }

            responses.add(SiResponse.toDto(siUpcomingItem, benBank, benBranch));
        });

        response.put("result", responses);
        response.put("currentPage", pages.getNumber());
        response.put("totalItems", pages.getTotalElements());
        response.put("totalPages", pages.getTotalPages());

        return ResponseDTO.builder()
                .error(false)
                .message(responses.size() + " data found!")
                .body(response)
                .build();
    }

    private Specification<SiUpcomingItem> searchSubmitted(String name, String status, String expiryDate,
                                                          String beneficiaryAccountNo, String payerAccountNo,
                                                          String currency) {

        UserInfoEntity user = LoggedInUserDetails.getUserInfoDetails();
        Long userId = user.getId();
        Long departmentId = user.getDept().getId();

        Specification<SiUpcomingItem> specification = Specification.where(null);

        specification = specification.and(SubmittedSISpecification.hasState("SUBMITTED"));
        specification = specification.and(SubmittedSISpecification.hasDepartment(departmentId));
        specification = specification.and(SubmittedSISpecification.hasNotUser(userId));

        if (!name.equals("")) {

            specification = specification.and(SubmittedSISpecification.hasName(name));
        }

        if (!status.equals("")) {

            specification = specification.and(SubmittedSISpecification.hasStatus(status));
        }

        if (!expiryDate.equals("")) {

            specification = specification.and(SubmittedSISpecification.hasExpiryDate(expiryDate));
        }

        if (!beneficiaryAccountNo.equals("")) {

            specification = specification.and(SubmittedSISpecification.hasBeneficiaryAccount(beneficiaryAccountNo));
        }

        if (!payerAccountNo.equals("")) {

            specification = specification.and(SubmittedSISpecification.hasPayerAccount(payerAccountNo));
        }

        if (!currency.equals("")) {

            specification = specification.and(SubmittedSISpecification.hasCurrency(currency));
        }

        return specification;
    }

    public ResponseDTO searchRejectedSI(String name, String status, String expiryDate,
                                        String beneficiaryAccountNo, String payerAccountNo,
                                        String currency, int pageNo, int pageSize, String sortBy, String sortOrder) {

        List<SiResponse> responses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        Sort sort;

        if (!sortBy.equals("")) {
            sort = Sort.by(sortBy.toLowerCase());
        } else {
            sort = Sort.by("id");
        }

        if (sortOrder.equals("") || sortOrder.equals("desc")) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Specification<SiUpcomingItem> specification = searchRejected(name, status, expiryDate, beneficiaryAccountNo, payerAccountNo, currency);

        Page<SiUpcomingItem> pages = siUpcomingItemRepository.findAll(specification, pageable);

        pages.forEach(siUpcomingItem -> {

            String benBank = "";
            String benBranch = "";

            if (bankRepository.findByIdAndIsDeletedFalse(siUpcomingItem.getBeneficiaryBankId()).isPresent()) {
                benBank = bankRepository.findByIdAndIsDeletedFalse(siUpcomingItem.getBeneficiaryBankId()).get().getName();
            }

            if (branchRepository.findByIdAndIsDeletedFalse(siUpcomingItem.getBeneficiaryBranchId()).isPresent()) {
                benBranch = branchRepository.findByIdAndIsDeletedFalse(siUpcomingItem.getBeneficiaryBranchId()).get().getName();
            }

            responses.add(SiResponse.toDto(siUpcomingItem, benBank, benBranch));
        });

        response.put("result", responses);
        response.put("currentPage", pages.getNumber());
        response.put("totalItems", pages.getTotalElements());
        response.put("totalPages", pages.getTotalPages());

        return ResponseDTO.builder()
                .error(false)
                .message(responses.size() + " data found!")
                .body(response)
                .build();
    }

    private Specification<SiUpcomingItem> searchRejected(String name, String status, String expiryDate,
                                                         String beneficiaryAccountNo, String payerAccountNo,
                                                         String currency) {

        UserInfoEntity user = LoggedInUserDetails.getUserInfoDetails();
        Long userId = user.getId();
        Long departmentId = user.getDept().getId();

        Specification<SiUpcomingItem> specification = Specification.where(null);

        specification = specification.and(RejectedSISpecification.hasState("REJECTED"));
        specification = specification.and(RejectedSISpecification.hasDepartment(departmentId));
        specification = specification.and(RejectedSISpecification.hasNotUser(userId));

        if (!name.equals("")) {

            specification = specification.and(RejectedSISpecification.hasName(name));
        }

        if (!status.equals("")) {

            specification = specification.and(RejectedSISpecification.hasStatus(status));
        }

        if (!expiryDate.equals("")) {

            specification = specification.and(RejectedSISpecification.hasExpiryDate(expiryDate));
        }

        if (!beneficiaryAccountNo.equals("")) {

            specification = specification.and(RejectedSISpecification.hasBeneficiaryAccount(beneficiaryAccountNo));
        }

        if (!payerAccountNo.equals("")) {

            specification = specification.and(RejectedSISpecification.hasPayerAccount(payerAccountNo));
        }

        if (!currency.equals("")) {

            specification = specification.and(RejectedSISpecification.hasCurrency(currency));
        }

        return specification;
    }

    private void updateSiHistoryState(SiHistory siHistory, boolean state) {
        siHistory.setIsFired(state);
        siHistoryRepository.save(siHistory);
    }

    private ResponseDTO reInitiateFailedSiProcess(SiHistory siHistory) {

        try {

            updateSiHistoryState(siHistory, true);

            String batchNumber = siHistory.getCustomerFundTransfer().getParentBatchNumber();

            if (interCustomerFundTransferRepository.findByBatchNumberAndIsDeletedFalse(batchNumber).isPresent()) {

                Long interCustomerFundTransferEntityId = interCustomerFundTransferRepository.findByBatchNumberAndIsDeletedFalse(batchNumber).get().getId();
                siTransactionService.reprocessTransaction(interCustomerFundTransferEntityId, siHistory);
            } else {

                updateSiHistoryState(siHistory, false);

                return ResponseDTO.builder()
                        .error(true)
                        .message("Invalid batch number")
                        .build();
            }
        } catch (Exception e) {

            log.error("{}", e.getMessage());
            updateSiHistoryState(siHistory, false);

            return ResponseDTO.builder()
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }

        return ResponseDTO.builder()
                .error(false)
                .message("SI re-initiated!")
                .build();
    }


    public ResponseDTO approve(long id) {

        try {

            Optional<SiUpcomingItem> optional = siUpcomingItemRepository.findById(id);

            if (optional.isEmpty()) {

                return ResponseDTO.builder()
                        .error(true)
                        .message("Invalid SI reference number!")
                        .build();
            }

            UserInfoEntity user = LoggedInUserDetails.getUserInfoDetails();
            Long userId = user.getId();

            if (!Objects.equals(optional.get().getSiConfiguration().getCreatedBy().getId(), userId)) {
                // siUpcomingItemRepository.approveSi(id, user.getId());
                SiUpcomingItem entity = optional.get();
                entity.setApprover(user);
                entity.setExecutionState("APPROVED");
                entity.setApprovedTime(LocalDate.now());
                siUpcomingItemRepository.save(optional.get());
            }

        } catch (Exception e) {
            log.error("{}", e.getMessage());

            return ResponseDTO.builder()
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }

        return ResponseDTO.builder()
                .error(false)
                .message("SI Approved!")
                .build();
    }


    public ResponseDTO reject(long id, RejectRequest request) {

        if (request.getReason() == null) {

            return ResponseDTO.builder()
                    .error(true)
                    .message("Must specify SI reject reason!")
                    .build();
        }

        try {

            Optional<SiUpcomingItem> optional = siUpcomingItemRepository.findById(id);

            if (optional.isEmpty()) {

                return ResponseDTO.builder()
                        .error(true)
                        .message("Invalid SI reference number!")
                        .build();
            }

            UserInfoEntity user = LoggedInUserDetails.getUserInfoDetails();
            Long userId = user.getId();

            if (optional.get().getSiConfiguration().getCreatedBy().getId() != userId) {
                siUpcomingItemRepository.rejectSi(id, LocalDate.now(), user, request.getReason());
            }
        } catch (Exception e) {

            log.error("{}", e.getMessage());

            return ResponseDTO.builder()
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }

        return ResponseDTO.builder()
                .error(false)
                .message("SI Rejected!")
                .build();
    }


    public ResponseDTO cancel(long id) {

        try {

            Optional<SiUpcomingItem> optional = siUpcomingItemRepository.findById(id);

            if (optional.isEmpty()) {

                return ResponseDTO.builder()
                        .error(true)
                        .message("Invalid SI reference number!")
                        .build();
            }

            UserInfoEntity user = LoggedInUserDetails.getUserInfoDetails();
            Long userId = user.getId();

            if (optional.get().getSiConfiguration().getCreatedBy().getId() != userId) {

                siUpcomingItemRepository.cancelSi(id, LocalDate.now(), user);
            }
        } catch (Exception e) {

            log.error("{}", e.getMessage());

            return ResponseDTO.builder()
                    .error(true)
                    .message(e.getMessage())
                    .body(new ArrayList<>())
                    .build();
        }

        return ResponseDTO.builder()
                .error(false)
                .message("SI Cancelled!")
                .build();
    }
}