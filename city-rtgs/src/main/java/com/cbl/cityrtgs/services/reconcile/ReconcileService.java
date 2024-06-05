package com.cbl.cityrtgs.services.reconcile;

import com.cbl.cityrtgs.common.enums.ResponseCodeEnum;
import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.accounttype.CbsName;
import com.cbl.cityrtgs.models.dto.configuration.department.DepartmentResponse;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.configuration.settlementaccount.SettlementAccountResponse;
import com.cbl.cityrtgs.models.dto.configuration.shadowaccount.ShadowAccountResponse;
import com.cbl.cityrtgs.models.dto.reconcile.*;
import com.cbl.cityrtgs.models.dto.transaction.ApprovalEventResponse;
import com.cbl.cityrtgs.models.dto.transaction.FundTransferType;
import com.cbl.cityrtgs.models.dto.transaction.CbsResponse;
import com.cbl.cityrtgs.models.dto.transaction.TransactionRequest;
import com.cbl.cityrtgs.models.entitymodels.configuration.AccountTypeEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentAccountEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.ReconcileDepartmentAccountEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.BankFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.InterBankTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.InterCustomerFundTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.AccountTypesRepository;
import com.cbl.cityrtgs.repositories.configuration.DepartmentAccountRepository;
import com.cbl.cityrtgs.repositories.transaction.ReconcileDepartmentAccountRepository;
import com.cbl.cityrtgs.repositories.transaction.SysDateRegisterSummaryRepository;
import com.cbl.cityrtgs.repositories.transaction.b2b.BankFndTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.b2b.InterBankFundTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.CustomerFndTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.InterCustomerFundTransferRepository;
import com.cbl.cityrtgs.services.configuration.*;
import com.cbl.cityrtgs.services.transaction.AccountReportService;
import com.cbl.cityrtgs.services.transaction.AccountTransactionRegisterService;
import com.cbl.cityrtgs.services.transaction.CbsTransactionService;
import com.cbl.cityrtgs.services.transaction.b2b.BankFundTransferService;
import com.cbl.cityrtgs.services.transaction.c2c.CustomerFundTransferService;
import com.cbl.cityrtgs.services.user.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReconcileService {
    private final CustomerFndTransferRepository repository;
    private final CurrencyService currencyService;
    private final DepartmentService departmentService;
    private final DepartmentAccountService departmentAccountService;
    private final SettlementAccountService settlementAccountService;
    private final AccountTypeService accountTypeService;
    private final ReconcileDepartmentAccountRepository reconcileDepartmentAccountRepository;
    private final CbsTransactionService cbsTransactionService;
    private final AccountReportService accountReportService;
    private final InterCustomerFundTransferRepository interC2CTxnRepo;
    private final CustomerFndTransferRepository c2cTxnRepo;
    private final BankFndTransferRepository b2bTxnRepo;
    private final UserInfoService userInfoService;
    private final BankFundTransferService bankFundTransferService;
    private final CustomerFundTransferService customerFundTransferService;
    private final DepartmentAccountRepository departmentAccountRepository;
    private final SysDateRegisterSummaryRepository sysDateRegisterSummaryRepository;
    private final ShadowAccountService shadowAccountService;
    private final AccountTypesRepository accountTypesRepository;
    private final AccountTransactionRegisterService registerService;
    private final BankService bankService;
    private final InterBankFundTransferRepository interB2BTxnRepo;

    public DeptAccReconcileResponse getDeptAccountWiseTxnAmount(ReconcileRequest request) {
        DeptAccReconcileResponse response = new DeptAccReconcileResponse();
        SettlementAccountStatementSearchFilter filter = new SettlementAccountStatementSearchFilter();
        AtomicReference<Boolean> reconcileEnable = new AtomicReference<>(false);
        var currency = currencyService.getById(request.getCurrencyId());

        /// get summary report for reconcile enable or disable
        SettlementAccountResponse settlementAccount = settlementAccountService.getEntityByCurrencyId(request.getCurrencyId());
        filter.setAccountNumber(settlementAccount.getCode());
        filter.setCurrencyCode(currency.getShortCode());

        var recon = accountReportService.getSettlementAccountReconcileMismatchReport(filter);
        if (Objects.nonNull(recon) ) {
            if(recon.getSettlementAccountBalance() != null){
                BigDecimal balance = recon.getSettlementAccountBalance().subtract(recon.getCentralBankClosingBalance());
                if (balance.equals(BigDecimal.ZERO)) {
                    reconcileEnable.set(true);
                } else {
                    reconcileEnable.set(false);
                }
            }
        }

        //get Dept wise Txn Amount
        List<IDepartmentAccount> departmentAccountList;
     //   if (Objects.nonNull(request.getDeptId()) && StringUtils.isNotBlank(request.getDeptId().toString())) {
        if (Objects.nonNull(request.getDeptId()) && Strings.isNotBlank(request.getDeptId().toString())) {
            departmentAccountList = repository.getTxnAmountByDeptId(request.getCurrencyId(), request.getDeptId());
        } else {
            departmentAccountList = repository.getDeptWiseTxnAmount(request.getCurrencyId());
        }

        if (request.getTxnType().equals(ReconcileTxnType.SettlementTxn)) {
            List<DepartmentUnreconciledResponse> unreconciledList = new ArrayList<>();
            List<DepartmentReconciledResponse> reconciledList = new ArrayList<>();

            departmentAccountList.forEach(d -> {
                RoutingType routing;
                if (d.getRoutingType().equals(RoutingType.Incoming.name())) {
                    routing = RoutingType.Incoming;
                } else {
                    routing = RoutingType.Outgoing;
                }
                DepartmentResponse dept = departmentService.getById(d.getDepartmentId());
                DepartmentAccountEntity deptAcc = departmentAccountService.getDepartmentAccEntity(dept.getId(), request.getCurrencyId(), routing);
                double mismatchAmount;
                if (!deptAcc.isReconcile()) {
                    DepartmentUnreconciledResponse unreconciledResponse = new DepartmentUnreconciledResponse();
                    double totalAmount= deptAcc.getBalance().doubleValue();
                    mismatchAmount = totalAmount - d.getAmount().doubleValue();
                    unreconciledResponse.setDepartmentAccId(deptAcc.getId());
                    unreconciledResponse.setDepartmentName(dept.getName());
                    unreconciledResponse.setDeptGlAcc(deptAcc.getAccountNumber());
                    unreconciledResponse.setCurrency(currency.getShortCode());
                    unreconciledResponse.setRoutingType(d.getRoutingType());
                    unreconciledResponse.setAmount(d.getAmount().doubleValue());
                    unreconciledResponse.setVat(d.getVat().doubleValue());
                    unreconciledResponse.setCharge(d.getCharge().doubleValue());
                    unreconciledResponse.setTotalAmount(totalAmount);
                    unreconciledResponse.setMismatchAmount(mismatchAmount);
                    unreconciledResponse.setReconcileEnable(reconcileEnable.get());
                    unreconciledList.add(unreconciledResponse);
                } else {
                    DepartmentReconciledResponse reconciledResponse = new DepartmentReconciledResponse();
                    reconciledResponse.setDepartmentName(dept.getName());
                    reconciledResponse.setDeptGlAcc(deptAcc.getAccountNumber());
                    reconciledResponse.setCurrency(currency.getShortCode());
                    reconciledResponse.setRoutingType(d.getRoutingType());
                    reconciledResponse.setVoucherNumber(deptAcc.getVoucherNumber());
                    reconciledResponse.setReconcileSettlementDate(deptAcc.getReconcileDate());
                    reconciledList.add(reconciledResponse);
                }
              });
            response.setUnreconciledList(unreconciledList);
            response.setReconciledList(reconciledList);
        } else if (request.getTxnType().equals(ReconcileTxnType.ChargeReconcile)) {
            List<DepartmentUnreconciledResponse> unreconciledList = new ArrayList<>();
            List<DepartmentReconciledResponse> reconciledList = new ArrayList<>();

            departmentAccountList.forEach(d -> {
                RoutingType routing;
                if (d.getRoutingType().equals(RoutingType.Incoming.name())) {
                    routing = RoutingType.Incoming;
                } else {
                    routing = RoutingType.Outgoing;
                }
                DepartmentResponse dept = departmentService.getById(d.getDepartmentId());
                DepartmentAccountEntity deptAcc = departmentAccountService.getDepartmentAccEntity(dept.getId(), request.getCurrencyId(), routing);
                if (!deptAcc.isChargeReconcile()) {
                    DepartmentUnreconciledResponse unreconciledResponse = new DepartmentUnreconciledResponse();
                    unreconciledResponse.setDepartmentAccId(deptAcc.getId());
                    unreconciledResponse.setDepartmentName(dept.getName());
                    unreconciledResponse.setDeptChargeGlAcc(deptAcc.getChargeAccNumber());
                    unreconciledResponse.setCurrency(currency.getShortCode());
                    unreconciledResponse.setRoutingType(d.getRoutingType());
                    unreconciledResponse.setCharge(d.getCharge().doubleValue());
                    unreconciledResponse.setReconcileEnable(reconcileEnable.get());
                    unreconciledList.add(unreconciledResponse);
                } else {
                    DepartmentReconciledResponse reconciledResponse = new DepartmentReconciledResponse();
                    reconciledResponse.setDepartmentName(dept.getName());
                    reconciledResponse.setDeptChargeGlAcc(deptAcc.getChargeAccNumber());
                    reconciledResponse.setCurrency(currency.getShortCode());
                    reconciledResponse.setRoutingType(d.getRoutingType());
                    reconciledResponse.setChargeVoucherNumber(deptAcc.getVoucherNumber());
                    reconciledResponse.setChargeReconcileSetDate(deptAcc.getReconcileDate());
                    reconciledList.add(reconciledResponse);
                }
            });
            response.setUnreconciledList(unreconciledList);
            response.setReconciledList(reconciledList);
        } else if (request.getTxnType().equals(ReconcileTxnType.VatReconcile)) {
            List<DepartmentUnreconciledResponse> unreconciledList = new ArrayList<>();
            List<DepartmentReconciledResponse> reconciledList = new ArrayList<>();

            departmentAccountList.forEach(d -> {
                RoutingType routing;
                if (d.getRoutingType().equals(RoutingType.Incoming.name())) {
                    routing = RoutingType.Incoming;
                } else {
                    routing = RoutingType.Outgoing;
                }
                DepartmentResponse dept = departmentService.getById(d.getDepartmentId());
                DepartmentAccountEntity deptAcc = departmentAccountService.getDepartmentAccEntity(dept.getId(), request.getCurrencyId(), routing);
                if (!deptAcc.isVatReconcile()) {
                    DepartmentUnreconciledResponse unreconciledResponse = new DepartmentUnreconciledResponse();
                    unreconciledResponse.setDepartmentAccId(deptAcc.getId());
                    unreconciledResponse.setDepartmentName(dept.getName());
                    unreconciledResponse.setDeptVatGlAcc(deptAcc.getVatAccNumber());
                    unreconciledResponse.setCurrency(currency.getShortCode());
                    unreconciledResponse.setRoutingType(d.getRoutingType());
                    unreconciledResponse.setVat(d.getVat().doubleValue());
                    unreconciledResponse.setReconcileEnable(reconcileEnable.get());
                    unreconciledList.add(unreconciledResponse);
                } else {
                    DepartmentReconciledResponse reconciledResponse = new DepartmentReconciledResponse();
                    reconciledResponse.setDepartmentName(dept.getName());
                    reconciledResponse.setDeptVatGlAcc(deptAcc.getVatAccNumber());
                    reconciledResponse.setCurrency(currency.getShortCode());
                    reconciledResponse.setRoutingType(d.getRoutingType());
                    reconciledResponse.setVatVoucherNumber(deptAcc.getVoucherNumber());
                    reconciledResponse.setVatReconcileSetDate(deptAcc.getReconcileDate());
                    reconciledList.add(reconciledResponse);
                }
            });
            response.setUnreconciledList(unreconciledList);
            response.setReconciledList(reconciledList);
        }

        return response;
    }

    public CbsResponse doDepartmentAccountSettlementTxn(ReconcileRequest request) throws Exception {
        LocalDateTime date = LocalDateTime.now();
        try {
            SettlementAccountResponse settlementAccount = settlementAccountService.getEntityByCurrencyId(request.getCurrencyId());
            AccountTypeEntity accountType = accountTypeService.getAccountByRtgsAccountIdAndCbsName(settlementAccount.getId(), CbsName.FINACLE);

            DepartmentAccountEntity deptAcc = departmentAccountService.getEntityById(request.getDepartmentAccId());
            UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();

            if (deptAcc.isReconcile()) {
                throw new Exception("Department Settlement txn already done for account no :" + deptAcc.getAccountNumber());
            } else {
                TransactionRequest txnRequest = new TransactionRequest();
                String narration = "Rtgs Recon " + deptAcc.getDept().getName()+" "+deptAcc.getRoutingType();
                txnRequest.setAmount(deptAcc.getBalance());
                txnRequest.setChargeEnabled(false);
                if (RoutingType.Outgoing.equals(deptAcc.getRoutingType())) {
                    txnRequest.setCrAccount(accountType.getCbsAccountNumber());
                    txnRequest.setDrAccount(deptAcc.getAccountNumber());
                } else {
                    txnRequest.setCrAccount(deptAcc.getAccountNumber());
                    txnRequest.setDrAccount(accountType.getCbsAccountNumber());
                }

                txnRequest.setCurrencyCode(deptAcc.getCurrency().getShortCode());
                txnRequest.setNarration(narration.length() > 30 ? narration.substring(0, 30) : narration);
                txnRequest.setCbsName(CbsName.FINACLE.toString());
                CbsResponse cbsResponse;
                if (txnRequest.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                    cbsResponse = cbsTransactionService.cbsTransaction(txnRequest);
                } else {
                    cbsResponse = new CbsResponse();
                    cbsResponse.setResponseCode("000");
                    cbsResponse.setResponseMessage("Amount Mismatch");
                    cbsResponse.setTransactionRefNumber("123456789");
                }

                if (!cbsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                    throw new Exception(cbsResponse.getResponseMessage());
                } else {
                    departmentAccountService.updateDepAcc(deptAcc.getId(), cbsResponse.getTransactionId(), ReconcileTxnType.SettlementTxn);
                    ReconcileDepartmentAccountEntity recoDeptAcc;

                    try {
                        recoDeptAcc = reconcileDepartmentAccountRepository.getReconcileDeptAccByRouteCurrencyDeptReconDate(deptAcc.getRoutingType(), deptAcc.getCurrency().getId(), deptAcc.getDept().getId());
                        if (Objects.isNull(recoDeptAcc)) {
                            recoDeptAcc = new ReconcileDepartmentAccountEntity();
                            recoDeptAcc.setAccountNumber(deptAcc.getAccountNumber());
                            recoDeptAcc.setDepartmentId(request.getDeptId());
                            recoDeptAcc.setCurrencyId(deptAcc.getCurrency().getId());
                            recoDeptAcc.setRoutingType(deptAcc.getRoutingType());
                        }
                        recoDeptAcc.setAccountNumber(deptAcc.getAccountNumber());
                        recoDeptAcc.setConfirmTxnNo(deptAcc.getConfirmTxnNo());
                        recoDeptAcc.setReconcileDate(date);
                        recoDeptAcc.setReconcileTime(date);
                        recoDeptAcc.setReconcileUser(currentUser.getUsername());
                        recoDeptAcc.setAmount(deptAcc.getBalance());
                        recoDeptAcc.setVoucherNumber(cbsResponse.getTransactionRefNumber().trim());
                        reconcileDepartmentAccountRepository.save(recoDeptAcc);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return cbsResponse;
                }
            }
        } catch (Exception e) {
            throw new Exception("Department Settlement txn Failed "+e.getMessage());
        }
    }

    public CbsResponse doDepartmentChargeSettlementTxn(ReconcileRequest request) throws Exception {
        LocalDateTime date = LocalDateTime.now();
        try {
            DepartmentAccountEntity deptAcc = departmentAccountService.getEntityById(request.getDepartmentAccId());
            UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();

            if (deptAcc.isChargeReconcile()) {
                throw new Exception("Charge reconcile txn already done for account no :" + deptAcc.getAccountNumber());
            } else {
                TransactionRequest txnRequest = new TransactionRequest();
                String narration = "Rtgs Recon " + deptAcc.getDept().getName() + "Charge";
                txnRequest.setAmount(deptAcc.getCharge());
                txnRequest.setChargeEnabled(false);
                if (RoutingType.Outgoing.equals(deptAcc.getRoutingType())) {
                    txnRequest.setCrAccount(deptAcc.getChargeAccNumber());
                    txnRequest.setDrAccount(deptAcc.getAccountNumber());
                } else {
                    txnRequest.setCrAccount(deptAcc.getAccountNumber());
                    txnRequest.setDrAccount(deptAcc.getChargeAccNumber());
                }

                txnRequest.setCurrencyCode(deptAcc.getCurrency().getShortCode());
                txnRequest.setNarration(narration.length() > 30 ? narration.substring(0, 30) : narration);
                txnRequest.setCbsName(CbsName.FINACLE.toString());
                CbsResponse cbsResponse;
                if (txnRequest.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                    cbsResponse = cbsTransactionService.cbsTransaction(txnRequest);
                } else {
                    cbsResponse = new CbsResponse();
                    cbsResponse.setResponseCode("000");
                    cbsResponse.setResponseMessage("Amount Mismatch");
                    cbsResponse.setTransactionRefNumber("123456789");
                }

                if (!cbsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                    throw new Exception(cbsResponse.getResponseMessage());
                } else {
                    ReconcileDepartmentAccountEntity recoDeptAcc = new ReconcileDepartmentAccountEntity();

                    try {
                        recoDeptAcc = reconcileDepartmentAccountRepository.getReconcileDeptAccByRouteCurrencyDeptReconDate(deptAcc.getRoutingType(), deptAcc.getCurrency().getId(), deptAcc.getDept().getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (recoDeptAcc == null) {
                        recoDeptAcc = new ReconcileDepartmentAccountEntity();
                        recoDeptAcc.setDepartmentId(deptAcc.getDept().getId());
                        recoDeptAcc.setCurrencyId(deptAcc.getCurrency().getId());
                        recoDeptAcc.setRoutingType(deptAcc.getRoutingType());
                    }

                    recoDeptAcc.setChargeAccountNumber(deptAcc.getChargeAccNumber());
                    recoDeptAcc.setAccountNumber(deptAcc.getAccountNumber());
                    recoDeptAcc.setReconcileDate(date);
                    recoDeptAcc.setChargeReconcileTime(date);
                    recoDeptAcc.setChargeReconcileUser(currentUser.getUsername());
                    recoDeptAcc.setChargeAmount(deptAcc.getCharge());
                    recoDeptAcc.setChargeVoucherNumber(cbsResponse.getTransactionId());
                    reconcileDepartmentAccountRepository.save(recoDeptAcc);
                    departmentAccountService.updateDepAcc(deptAcc.getId(), cbsResponse.getTransactionId(), ReconcileTxnType.ChargeReconcile);

                    return cbsResponse;
                }
            }
        } catch (Exception e) {
            throw new Exception("Department Charge Settlement txn Failed " + e.getMessage());
        }
    }

    public CbsResponse doDepartmentVatSettlementTxn(ReconcileRequest request) throws Exception {
        LocalDateTime date = LocalDateTime.now();
        try {
            DepartmentAccountEntity deptAcc = departmentAccountService.getEntityById(request.getDepartmentAccId());
            UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();

            if (deptAcc.isVatReconcile()) {
                throw new Exception("VAT reconcile txn already done for account no :" + deptAcc.getAccountNumber());
            } else {
                TransactionRequest txnRequest = new TransactionRequest();
                String narration = "Rtgs Recon " + deptAcc.getDept().getName() + "Vat";
                txnRequest.setAmount(deptAcc.getVat());
                txnRequest.setChargeEnabled(false);
                if (RoutingType.Outgoing.equals(deptAcc.getRoutingType())) {
                    txnRequest.setCrAccount(deptAcc.getVatAccNumber());
                    txnRequest.setDrAccount(deptAcc.getAccountNumber());
                } else {
                    txnRequest.setCrAccount(deptAcc.getAccountNumber());
                    txnRequest.setDrAccount(deptAcc.getVatAccNumber());
                }

                txnRequest.setCurrencyCode(deptAcc.getCurrency().getShortCode());
                txnRequest.setNarration(narration.length() > 30 ? narration.substring(0, 30) : narration);
                txnRequest.setCbsName(CbsName.FINACLE.toString());
                CbsResponse cbsResponse;
                if (txnRequest.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                    cbsResponse = cbsTransactionService.cbsTransaction(txnRequest);
                } else {
                    cbsResponse = new CbsResponse();
                    cbsResponse.setResponseCode("000");
                    cbsResponse.setResponseMessage("Amount Mismatch");
                    cbsResponse.setTransactionRefNumber("123456789");
                }

                if (!cbsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                    throw new Exception(cbsResponse.getResponseMessage());
                } else {
                    ReconcileDepartmentAccountEntity recoDeptAcc = new ReconcileDepartmentAccountEntity();

                    try {
                        recoDeptAcc = reconcileDepartmentAccountRepository.getReconcileDeptAccByRouteCurrencyDeptReconDate(deptAcc.getRoutingType(), deptAcc.getCurrency().getId(), deptAcc.getDept().getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (recoDeptAcc == null) {
                        recoDeptAcc = new ReconcileDepartmentAccountEntity();
                        recoDeptAcc.setAccountNumber(deptAcc.getAccountNumber());
                        recoDeptAcc.setDepartmentId(deptAcc.getDept().getId());
                        recoDeptAcc.setCurrencyId(deptAcc.getCurrency().getId());
                        recoDeptAcc.setRoutingType(deptAcc.getRoutingType());
                    }
                    recoDeptAcc.setAccountNumber(deptAcc.getAccountNumber());
                    recoDeptAcc.setVatAccountNumber(deptAcc.getVatAccNumber());
                    recoDeptAcc.setReconcileDate(date);
                    recoDeptAcc.setVatReconcileTime(date);
                    recoDeptAcc.setReconcileUser(currentUser.getUsername());
                    recoDeptAcc.setVatAmount(deptAcc.getVat());
                    recoDeptAcc.setVatVoucherNumber(cbsResponse.getTransactionRefNumber());
                    reconcileDepartmentAccountRepository.save(recoDeptAcc);
                    departmentAccountService.updateDepAcc(deptAcc.getId(), cbsResponse.getTransactionId(), ReconcileTxnType.VatReconcile);
                    return cbsResponse;
                }
            }
        } catch (Exception e) {
            throw new Exception("Department Settlement txn Failed for account no " + e.getMessage());
        }
    }

    public List<ReconcileResponse> getSettlementTxnList(List<String> accountNumberList) {
        List<ReconcileResponse> responseList = new ArrayList<>();

        if (accountNumberList.size() == 1) {
            var settlementAcc = settlementAccountService.getSettlementAccByCode(accountNumberList.get(0));
            BigDecimal balance = BigDecimal.ZERO;
            ReconcileResponse response = new ReconcileResponse();
            var recon = accountReportService.getSettlementAccountReport(settlementAcc.getCode(), settlementAcc.getCurrencyCode());
            var reconciled = departmentAccountRepository.existsByCurrencyIdAndReconcileIsTrue(settlementAcc.getCurrencyId());
            if (Objects.nonNull(recon)) {
                if (recon.getSettlementAccountBalance() != null) {
                    balance = recon.getSettlementAccountBalance().subtract(recon.getCentralBankClosingBalance());
                }
                response
                        .setAccountNumber(recon.getAccountNumber())
                        .setCurrencyCode(recon.getCurrencyCode())
                        .setCentralBankClosingBalance(Objects.nonNull(recon.getCentralBankClosingBalance()) ? recon.getCentralBankClosingBalance() : BigDecimal.ZERO)
                        .setSettlementAccountBalance(Objects.nonNull(recon.getSettlementAccountBalance()) ? recon.getSettlementAccountBalance() : BigDecimal.ZERO)
                        .setMismatchAmount(balance)
                       // .setMismatchAmount(BigDecimal.ZERO)
                        .setCreateDate(recon.getCreateDate())
                        .setCreateTime(recon.getCreateTime());
                if (!balance.equals(BigDecimal.ZERO)) {
                    response.setReconciled(reconciled);
                } else {
                    response.setReconciled(reconciled);
                }
                responseList.add(response);
            }
        } else {
            var settlementAccList = settlementAccountService.getSettlementAccList();
            settlementAccList.forEach(settlementAcc -> {
                BigDecimal balance = BigDecimal.ZERO;
                ReconcileResponse response = new ReconcileResponse();
                var recon = accountReportService.getSettlementAccountReport(settlementAcc.getCode(), settlementAcc.getCurrencyCode());

                var reconciled = departmentAccountRepository.existsByCurrencyIdAndReconcileIsTrue(settlementAcc.getCurrencyId());
                if (Objects.nonNull(recon)) {
                    if (recon.getSettlementAccountBalance() != null) {
                        balance = recon.getSettlementAccountBalance().subtract(recon.getCentralBankClosingBalance());
                    }
                    response
                            .setAccountNumber(recon.getAccountNumber())
                            .setCurrencyCode(recon.getCurrencyCode())
                            .setCentralBankClosingBalance(Objects.nonNull(recon.getCentralBankClosingBalance()) ? recon.getCentralBankClosingBalance() : BigDecimal.ZERO)
                            .setSettlementAccountBalance(Objects.nonNull(recon.getSettlementAccountBalance()) ? recon.getSettlementAccountBalance() : BigDecimal.ZERO)
                            .setMismatchAmount(balance)
                            .setCreateDate(recon.getCreateDate())
                            .setCreateTime(recon.getCreateTime());
                    if (!balance.equals(BigDecimal.ZERO)) {
                        response.setReconciled(reconciled);
                    } else {
                        response.setReconciled(reconciled);
                    }
                    responseList.add(response);
                }
            });
        }
        return responseList;
    }

    public String returnSettlementBalance(String accountNo) throws Exception {
        String voucherNumber = null;
        var settlementAcc = settlementAccountService.getSettlementAccByCode(accountNo);
        var recon = accountReportService.getSettlementAccountReport(settlementAcc.getCode(), settlementAcc.getCurrencyCode());
        BigDecimal balance = BigDecimal.ZERO;
        ReconcileResponse response = new ReconcileResponse();
        if (Objects.isNull(recon)) {
            throw new Exception(String.format("No Settlement Account found for account: %s (%s)", settlementAcc.getCode(), settlementAcc.getCurrencyCode()));
        } else {
            response
                    .setAccountNumber(recon.getAccountNumber())
                    .setCurrencyCode(recon.getCurrencyCode())
                    .setCentralBankClosingBalance(Objects.nonNull(recon.getCentralBankClosingBalance()) ? recon.getCentralBankClosingBalance() : BigDecimal.ZERO)
                    .setSettlementAccountBalance(Objects.nonNull(recon.getSettlementAccountBalance()) ? recon.getSettlementAccountBalance() : BigDecimal.ZERO)
                    .setMismatchAmount(balance)
                    .setCreateDate(recon.getCreateDate())
                    .setCreateTime(recon.getCreateTime());
            AccountTypeEntity accType;
            Optional<AccountTypeEntity> _accType = accountTypesRepository.findByRtgsAccountIdAndCbsNameAndIsDeletedFalse(settlementAcc.getId(), CbsName.FINACLE);

            if (_accType.isPresent()) {
                accType = _accType.get();
                ShadowAccountResponse shadowAccount = shadowAccountService.getShadowAcc(settlementAcc.getBankId(), settlementAcc.getCurrencyId());
                if (shadowAccount != null) {
                    var sysDateRegisterSummary = sysDateRegisterSummaryRepository.getRegisterSummaryByAccNo(settlementAcc.getCode());
                    if (sysDateRegisterSummary.getTotalBalance().compareTo(response.getCentralBankClosingBalance()) == 0) {
                        if (sysDateRegisterSummary.getTotalBalance().compareTo(BigDecimal.ZERO) == 1) {
                            voucherNumber = doSettlementTxn(
                                    shadowAccount.getIncomingGl(),
                                    accType.getCbsAccountNumber(),
                                    response.getCentralBankClosingBalance(),
                                    settlementAcc.getCurrencyCode(),
                                    settlementAcc,
                                    accType.getCbsName());
                        }else if (sysDateRegisterSummary.getTotalBalance().compareTo(BigDecimal.ZERO) == -1) {
                            voucherNumber = doSettlementTxn(
                                    accType.getCbsAccountNumber(),
                                    shadowAccount.getIncomingGl(),
                                    response.getCentralBankClosingBalance(),
                                    settlementAcc.getCurrencyCode(),
                                    settlementAcc,
                                    accType.getCbsName());
                        }
                    } else {
                        throw new Exception("Bangladesh Bank Closing Book Balance and Rtgs Balance Mismatch");
                    }
                }
            }
        }
        return voucherNumber;
    }

    private String doSettlementTxn(String debitAccount,
                                   String creditAccount,
                                   BigDecimal amount,
                                   String currencyCode,
                                   SettlementAccountResponse settlementAccount,
                                   CbsName cbsName) throws Exception {
        Date date = new Date();
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        var ownerBank = bankService.getOwnerBank();
        String voucherNumber;
        String narration = "Settlement A/C Balance Return to Current A/C";
        TransactionRequest request = new TransactionRequest();
        request.setAmount(amount);
        request.setCrAccount(creditAccount);
        request.setDrAccount(debitAccount);
        request.setCurrencyCode(currencyCode);
        request.setEntryUser(currentUser.getUsername());
        request.setNarration(narration.substring(0, 30));
        request.setFundTransferType(FundTransferType.BankToBank);
        request.setCbsName(cbsName.toString());
        request.setCharge(BigDecimal.ZERO);
        request.setVat(BigDecimal.ZERO);
        request.setRoutingType(RoutingType.Incoming);
        request.setChargeEnabled(false);
        CbsResponse cbsResponse;

        if (request.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            cbsResponse = cbsTransactionService.cbsTransaction(request);
            if (!cbsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                throw new Exception(cbsResponse.getResponseMessage());
            } else {
                voucherNumber = cbsResponse.getTransactionRefNumber();
                registerService.doRegister(null, settlementAccount.getCurrencyId(), amount, voucherNumber, settlementAccount.getBankId(), ownerBank.getId(), null, null, date, date, null, null, narration, CbsName.FINACLE.toString());
                return voucherNumber;
            }
        }
        return null;
    }

    public List<ApprovalEventResponse> getAllPendingFundTransfers() {
        List<ApprovalEventResponse> response = new ArrayList<>();
        List<ApprovalEventResponse> c2cList = this.getAllPendingC2COutward();
        List<ApprovalEventResponse> b2bList = this.getAllPendingB2BOutward();
        response.addAll(c2cList);
        response.addAll(b2bList);
        return response;
    }

    public List<ApprovalEventResponse> getAllPendingC2COutward() {
        List<ApprovalEventResponse> approvalEventResponses = new ArrayList<>();
        List<InterCustomerFundTransferEntity> pendingList = interC2CTxnRepo.getAllPendingTxn();
        pendingList.forEach(p -> {
            ApprovalEventResponse response;
            response = c2cTxnToResponse(p.getId());
            approvalEventResponses.add(response);
        });
        return approvalEventResponses;
    }

    public List<ApprovalEventResponse> getAllPendingB2BOutward() {
        List<ApprovalEventResponse> approvalEventResponses;
        List<InterBankTransferEntity> pendingList = interB2BTxnRepo.getAllPendingTxn();
        List<Long> transactionIds = new ArrayList<>();
        pendingList.forEach(p -> transactionIds.add(p.getId()));
        List<BankFndTransferEntity> fndTransferEntities = b2bTxnRepo.getAllPendingTxn(transactionIds);
        approvalEventResponses = fndTransferEntities.stream().map(this::b2bTxnToResponse).collect(Collectors.toList());
        return approvalEventResponses;
    }

    public ApprovalEventResponse b2bTxnToResponse(BankFndTransferEntity entity) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        UserInfoEntity userInfoDetails = userInfoService.getEntityById(currentUser.getId());
        ApprovalEventResponse domain = new ApprovalEventResponse();
        domain.setId(entity.getTransactions());
        domain.setRoutingType(entity.getRoutingType());
        domain.setFundTransferType(FundTransferType.BankToBank.toString());
        domain.setStatus("Submitted");
        domain.setEventName(entity.getBenName());
        domain.setEventName("Bank To Bank Fund Transfer");
        domain.setParentBatchNumber(entity.getParentBatchNumber());
        domain.setReferenceText("Tr. Type : Bank To Bank Fund Transfer Routing Type : " + entity.getRoutingType().toString());
        domain.setEntryUser(userInfoDetails.getUsername());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setSettlementDate(entity.getSettlementDate());
        return domain;
    }


    public ApprovalEventResponse c2cTxnToResponse(Long id) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        UserInfoEntity userInfoDetails = userInfoService.getEntityById(currentUser.getId());
        List<CustomerFndTransferEntity> c2cTxns = c2cTxnRepo.findAllByTransactionsAndIsDeletedFalse(id);
        ApprovalEventResponse domain = new ApprovalEventResponse();
        if (c2cTxns.size() > 0) {
            domain.setId(c2cTxns.get(0).getTransactions());
            domain.setRoutingType(c2cTxns.get(0).getRoutingType());
            domain.setFundTransferType(FundTransferType.CustomerToCustomer.toString());
            domain.setStatus("Submitted");
            domain.setEventName("Customer To Customer Fund Transfer");
            domain.setParentBatchNumber(c2cTxns.get(0).getParentBatchNumber());
            domain.setReferenceText("Tr. Type : Customer To Customer  Fund Transfer Routing Type : " + c2cTxns.get(0).getRoutingType().toString());
            domain.setEntryUser(userInfoDetails.getUsername());
            domain.setCreatedAt(c2cTxns.get(0).getCreatedAt());
            domain.setSettlementDate(c2cTxns.get(0).getSettlementDate());
        }
        return domain;
    }

    public void rejectAllPendingFundTransfers() {
        //b2b reject
        List<InterBankTransferEntity> b2bPendingList = interB2BTxnRepo.getAllPendingTxn();
        b2bPendingList.forEach(p -> bankFundTransferService.rejectTransaction(p.getId(), "Admin Reject"));
        //c2c reject
        List<InterCustomerFundTransferEntity> c2cPendingList = interC2CTxnRepo.getAllPendingTxn();
        c2cPendingList.forEach(p -> customerFundTransferService.rejectTransaction(p.getId(), "Admin Reject"));
    }

}
