package com.cbl.cityrtgs.services.transaction;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.transaction.*;
import com.cbl.cityrtgs.models.entitymodels.configuration.RoleEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.InterBankTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.InterCustomerFundTransferEntity;
import com.cbl.cityrtgs.repositories.transaction.b2b.BankFndTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.b2b.InterBankFundTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.CustomerFndTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.InterCustomerFundTransferRepository;
import com.cbl.cityrtgs.services.user.UserInfoService;
import com.cbl.cityrtgs.common.utility.DateUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApprovalEventService {
    private final UserInfoService userInfoService;
    private final InterCustomerFundTransferRepository interC2CTxnRepo;
    private final CustomerFndTransferRepository c2cTxnRepo;
    private final InterBankFundTransferRepository interB2BTxnRepo;
    private final BankFndTransferRepository b2bTxnRepo;

    public List<IApprovalEventResponse> getAll() {
        List<IApprovalEventResponse> response = new ArrayList<>();
        List<IApprovalEventResponse> c2cList = getAllTxnForC2C();
        List<IApprovalEventResponse> b2bList = getAllTxnForB2B();
        if (!c2cList.isEmpty()) {
            response.addAll(c2cList);
        }
        if (!b2bList.isEmpty()) {
            response.addAll(b2bList);
        }
        return response;
    }

    public List<IApprovalEventResponse> getAllTxnForB2B() {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        UserInfoEntity userInfoDetails = userInfoService.getEntityById(currentUser.getId());
        List<IApprovalEventResponse> approvalEventResponses = new ArrayList<>();
        for (RoleEntity role: userInfoDetails.getRoles()) {
            if(role.getName().contains("Admin")){
                approvalEventResponses = interB2BTxnRepo.getUnApprovedB2BTxns(currentUser.getId());
                break;
            }else{
                approvalEventResponses = interB2BTxnRepo.getUnApprovedB2BTxns(currentUser.getId(), userInfoDetails.getBranch().getId(), userInfoDetails.getDept().getId());
            }
        }
        return approvalEventResponses;
    }

    public List<IApprovalEventResponse> getAllTxnForC2C() {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        UserInfoEntity userInfoDetails = userInfoService.getEntityById(currentUser.getId());
        List<IApprovalEventResponse> approvalEventResponses = new ArrayList<>();
        for (RoleEntity role: userInfoDetails.getRoles()) {
            if(role.getName().contains("Admin")){
                approvalEventResponses = interC2CTxnRepo.getUnApprovedC2CTxns(currentUser.getId());
                break;
            }else{
                approvalEventResponses = interC2CTxnRepo.getUnApprovedC2CTxns(currentUser.getId(), userInfoDetails.getBranch().getId(), userInfoDetails.getDept().getId());
            }
        }
        return approvalEventResponses;
    }

    public Map<String, Object> getAllRejectedOutwardTransactions() {
        Map<String, Object> response = new HashMap<>();
        List<RejectedOutwardTransactions> responses = new ArrayList<>();
        List<RejectedOutwardTransactions> c2cList = getAllOutwardRejectedC2C();
        List<RejectedOutwardTransactions> b2bList = getAllOutwardRejectedB2B();

        if (!c2cList.isEmpty()) {
            responses.addAll(c2cList);
        }

        if (!b2bList.isEmpty()) {
            responses.addAll(b2bList);
        }
        response.put("result", responses);

        return response;
    }

    public List<RejectedOutwardTransactions> getAllOutwardRejectedC2C() {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        List<RejectedOutwardTransactions> rejectedOutwardTransactions = new ArrayList<>();

        List<InterCustomerFundTransferEntity> fundTransferList = interC2CTxnRepo.getAllOutwardRejectedC2CTxn(currentUser.getId());

        if (!fundTransferList.isEmpty()) {
            fundTransferList.forEach(p -> {
                if (currentUser.getId().equals(p.getCreatedBy())) {
                    RejectedOutwardTransactions rejectedOutwardTxn = new RejectedOutwardTransactions();
                    rejectedOutwardTxn
                            .setId(p.getId())
                            .setStatus(p.getTxnVerificationStatus().toString())
                            .setBatchNumber(p.getBatchNumber())
                            .setEntryUser(p.getEntryUser())
                            .setCreatedDate(p.getCreatedAt())
                            .setCreatedTime(p.getCreateTime())
                            .setFundTransferType(FundTransferType.CustomerToCustomer.toString());
                    rejectedOutwardTransactions.add(rejectedOutwardTxn);
                }
            });
        }
        return rejectedOutwardTransactions;
    }

    public List<RejectedOutwardTransactions> getAllOutwardRejectedB2B() {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        List<RejectedOutwardTransactions> rejectedOutwardTransactions = new ArrayList<>();

        List<InterBankTransferEntity> fundTransferList = interB2BTxnRepo.getAllOutwardRejectedB2BTxns(currentUser.getId());

        fundTransferList.forEach(p -> {
            if (currentUser.getId().equals(p.getCreatedBy())) {
                RejectedOutwardTransactions rejectedOutwardTxn = new RejectedOutwardTransactions();
                rejectedOutwardTxn
                        .setId(p.getId())
                        .setStatus(p.getTxnVerificationStatus().toString())
                        .setBatchNumber(p.getBatchNumber())
                        .setEntryUser(p.getEntryUser())
                        .setCreatedDate(p.getCreatedAt())
                        .setCreatedTime(p.getCreateTime())
                        .setFundTransferType(FundTransferType.BankToBank.toString());
                rejectedOutwardTransactions.add(rejectedOutwardTxn);
            }
        });
        return rejectedOutwardTransactions;
    }

    public Map<String, Object> getAllConfirmedInwardTransactions(String fromDate, String toDate, String reference) {

        Map<String, Object> response = new HashMap<>();

        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.isEmpty()) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }
        if (!toDate.isEmpty()) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }

        if (StringUtils.isBlank(reference)) {
            reference = "%%";
        }

        List<ConfirmedInwardTransactions> fundTransferList = c2cTxnRepo.getAllInwardConfirmedC2CTxn(fromDate1, toDate1, reference);

        response.put("result", fundTransferList);
        return response;
    }

    public Map<String, Object> getAllPendingOutwardTransactions(String fromDate, String toDate, String reference) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        Map<String, Object> response = new HashMap<>();
        List<PendingOutwardTransactions> responses = new ArrayList<>();
        List<PendingOutwardTransactions> c2cList = getAllOutwardPendingC2C(currentUser.getId(), fromDate, toDate, reference);
        List<PendingOutwardTransactions> b2bList = getAllOutwardPendingB2B(currentUser.getId(), fromDate, toDate, reference);

        if (!c2cList.isEmpty()) {
            responses.addAll(c2cList);
        }

        if (!b2bList.isEmpty()) {
            responses.addAll(b2bList);
        }
        response.put("result", responses);

        return response;
    }
    public List<PendingOutwardTransactions> getAllOutwardPendingC2C(Long userId, String fromDate, String toDate, String reference) {
        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.isEmpty()) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }
        if (!toDate.isEmpty()) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }

        if (StringUtils.isBlank(reference)) {
            reference = "%%";
        }

        return c2cTxnRepo.getAllOutwardPendingC2CTxn(fromDate1, toDate1, reference);
    }

    public List<PendingOutwardTransactions> getAllOutwardPendingB2B(Long userId, String fromDate, String toDate, String reference) {
        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.isEmpty()) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }
        if (!toDate.isEmpty()) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }

        if (StringUtils.isBlank(reference)) {
            reference = "%%";
        }

        return b2bTxnRepo.getAllOutwardPendingB2BTxn(fromDate1, toDate1, reference);
    }
}
