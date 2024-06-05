package com.cbl.cityrtgs.services.report.implementation;

import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.report.*;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.models.dto.transaction.FundTransferType;
import com.cbl.cityrtgs.repositories.transaction.AccountTransactionRegisterRepository;
import com.cbl.cityrtgs.repositories.transaction.ReconcileDepartmentAccountRepository;
import com.cbl.cityrtgs.repositories.transaction.SettlementAccountStatementDetailRepository;
import com.cbl.cityrtgs.repositories.transaction.b2b.InterBankFundTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.CustomerFndTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.IbTransactionRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.InterCustomerFundTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.notification.FailedTxnNotificationRepository;
import com.cbl.cityrtgs.services.report.abstraction.ReportService;
import com.cbl.cityrtgs.common.utility.DateUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final InterCustomerFundTransferRepository interCustFundTransferRepository;
    private final InterBankFundTransferRepository interBankFundTransferRepository;
    private final CustomerFndTransferRepository customerFndTransferRepository;
    private final IbTransactionRepository ibTransactionRepository;
    private final AccountTransactionRegisterRepository registerRepository;
    private final SettlementAccountStatementDetailRepository settlementAccountStatementDetailRepository;
    private final FailedTxnNotificationRepository failedTxnNotificationRepository;
    private final ReconcileDepartmentAccountRepository reconcileDepartmentAccountRepository;

    @Override
    public Map<String, Object> getDepartmentWiseReport(String fromDate, String toDate, String routing, String currency, String status, String fromAmount, String toAmount, String dept, String cbsName) {
        Map<String, Object> response = new HashMap<>();

        List<DepartmentWiseReport> txnResponse;
        List<DepartmentWiseTxnReport> responses = new ArrayList<>();

        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }
        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }
        if (status.equals("")) {
            status = null;
        }
        if (routing.equals("")) {
            routing = null;
        }
        if (currency.equals("")) {
            currency = null;
        }
        if (StringUtils.isBlank(cbsName) || cbsName.equalsIgnoreCase("All")) {
            cbsName = "%%";
        }
        if (StringUtils.isBlank(dept) || dept.equalsIgnoreCase("All")) {
            List<DepartmentList> deptList = customerFndTransferRepository.getAllDepartmentList(fromDate1, toDate1, routing, currency, status, cbsName);
            if (deptList.size() > 0) {
                for (int i = 0; i < deptList.size(); i++) {
                    DepartmentWiseTxnReport txn = new DepartmentWiseTxnReport();
                    txnResponse = customerFndTransferRepository.getDepartmentWiseReport(fromDate1, toDate1, routing, currency, status, deptList.get(i).getDeptId(), cbsName);
                    if (!txnResponse.isEmpty()) {
                        txn.setDepartment(deptList.get(i).getDeptName());
                        txn.setTxnReports(txnResponse);
                        responses.add(txn);
                    }
                }
            }
        } else {
            DepartmentWiseTxnReport txn = new DepartmentWiseTxnReport();
            txnResponse = customerFndTransferRepository.getDepartmentWiseReport(fromDate1, toDate1, routing, currency, status, dept, cbsName);
            if (!txnResponse.isEmpty()) {
                txn.setDepartment(Objects.nonNull(txnResponse.get(0).getDeptName()) ? txnResponse.get(0).getDeptName() : "");
                txn.setTxnReports(txnResponse);
                responses.add(txn);
            }

        }

        response.put("result", responses);
        return response;
    }

    @Override
    public Map<String, Object> getIBTransactionReport(String fromDate, String toDate, String currency, String status, String deliveryChannel) {
        Map<String, Object> response = new HashMap<>();

        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }
        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }
        if (status.equals("")) {
            status = null;
        }

        if (currency.equals("")) {
            currency = null;
        }
        if (deliveryChannel.equals("")) {
            deliveryChannel = null;
        }

        var responses = ibTransactionRepository.getIBTransactionReport(fromDate1, toDate1, currency, status, deliveryChannel);
        response.put("result", responses);
        return response;
    }

    @Override
    public Map<String, Object> getCardTransactionReport(String fromDate, String toDate, String currency) {
        Map<String, Object> response = new HashMap<>();

        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }

        var responses = customerFndTransferRepository.getCardTransactionReport(fromDate1, toDate1, currency);
        response.put("result", responses);
        return response;
    }

    @Override
    public Map<String, Object> getChargeVatReport(String fromDate, String toDate, String currency, String cbsName) {
        Map<String, Object> response = new HashMap<>();
        List<ChargeVatReport> txnResponse;
        List<DepartmentChargeVatReport> responses = new ArrayList<>();
        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }

        if (StringUtils.isBlank(cbsName) || cbsName.equalsIgnoreCase("All")) {
            cbsName = "%%";
        }
        List<DepartmentList> deptList = customerFndTransferRepository.getDepartmentList(fromDate1, toDate1, currency, cbsName);
        if (deptList.size() > 0) {
            for (int i = 0; i < deptList.size(); i++) {
                DepartmentChargeVatReport txn = new DepartmentChargeVatReport();
                txnResponse = customerFndTransferRepository.getChargeVatReport(fromDate1, toDate1, currency, cbsName, deptList.get(i).getDeptId());
                if (!txnResponse.isEmpty()) {
                    txn.setDepartment(deptList.get(i).getDeptName());
                    txn.setChargeVatReports(txnResponse);
                    responses.add(txn);
                }
            }
        }
        response.put("result", responses);
        return response;
    }

    @Override
    public Map<String, Object> getIBChargeVatReport(String fromDate, String toDate, String deliveryChannel, String currency, String cbsName) {
        Map<String, Object> response = new HashMap<>();

        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }
        if (StringUtils.isBlank(cbsName) || cbsName.equalsIgnoreCase("All")) {
            cbsName = "%%";
        }

        var responses = customerFndTransferRepository.getIBChargeVatReport(fromDate1, toDate1, deliveryChannel, currency, cbsName);

        response.put("result", responses);
        return response;
    }

    @Override
    public Map<String, Object> getTransactionReport(String fromDate, String toDate, String branch, String currency, String cbsName) {
        Map<String, Object> response = new HashMap<>();
        List<BranchTxnReport> responses = new ArrayList<>();
        List<BranchWiseTxnReport> txnResponse;
        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }
        if (StringUtils.isBlank(cbsName) || cbsName.equalsIgnoreCase("All")) {
            cbsName = "%%";
        }
        if (StringUtils.isBlank(branch) || branch.equalsIgnoreCase("All")) {
            List<BranchList> branchList = interCustFundTransferRepository.getPayerBranchList(fromDate1, toDate1, currency, "Confirmed", cbsName);
            if (branchList.size() > 0) {

                for (int i = 0; i < branchList.size(); i++) {
                    BranchTxnReport txn = new BranchTxnReport();
                    txnResponse = interCustFundTransferRepository.getBranchWiseCustomerTxnReport(fromDate1, toDate1, currency, "Confirmed", cbsName, branchList.get(i).getBranch());
                    if (!txnResponse.isEmpty()) {
                        txn.setBranch(branchList.get(i).getBranch());
                        txn.setRtgsTransactionReport(txnResponse);
                        responses.add(txn);
                    }
                }
            }
        } else {
            BranchTxnReport txn = new BranchTxnReport();
            txnResponse = interCustFundTransferRepository.getBranchWiseCustomerTxnReport(fromDate1, toDate1, currency, "Confirmed", cbsName, branch);
            if (!txnResponse.isEmpty()) {
                txn.setBranch(branch);
                txn.setRtgsTransactionReport(txnResponse);
                responses.add(txn);
            }
        }

        response.put("result", responses);
        return response;
    }

    @Override
    public Map<String, Object> getUserWiseReport(String fromDate, String toDate, String routingType, String currency, String user) {
        Map<String, Object> response = new HashMap<>();
        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }

        List<UserWiseTxnReport> responses = interCustFundTransferRepository.getUserWiseReport(fromDate1, toDate1, routingType, currency, user);
        response.put("result", responses);
        return response;
    }

    @Override
    public Map<String, Object> getFundTransferSummaryReport(String txnDate, String fundTransferType, String currency) {
        Map<String, Object> response = new HashMap<>();
        List<TxnSummaryReport> responses = new ArrayList<>();
        LocalDate txnDate1;

        if (!txnDate.equals("")) {
            txnDate1 = DateUtility.toDate(txnDate);
        } else {
            txnDate1 = LocalDate.now();
        }

        if (fundTransferType.equals(FundTransferType.CustomerToCustomer.toString())) {
            responses = interCustFundTransferRepository.getC2CFundTransferSummaryReport(txnDate1, currency);
        }

        if (fundTransferType.equals(FundTransferType.BankToBank.toString())) {
            responses = interBankFundTransferRepository.getB2BFundTransferSummaryReport(txnDate1, currency);
        }

        response.put("result", responses);
        return response;
    }

    @Override
    public Map<String, Object> getCustomDutyReport(String fromDate, String toDate, String dept, String cbsName) {
        Map<String, Object> response = new HashMap<>();
        List<CustomDutyReport> txnResponse;
        List<DepartmentWiseCustomeDutyReport> responses = new ArrayList<>();
        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }
        if (StringUtils.isBlank(cbsName) || cbsName.equalsIgnoreCase("All")) {
            cbsName = "%%";
        }

        if (StringUtils.isBlank(dept) || dept.equalsIgnoreCase("All")) {
           List<DepartmentList> deptList = customerFndTransferRepository.getAllDepartmentListForCustomduty(fromDate1, toDate1,cbsName);
            if (deptList.size() > 0) {
                for (int i = 0; i < deptList.size(); i++) {
                    DepartmentWiseCustomeDutyReport txn = new DepartmentWiseCustomeDutyReport();
                    txnResponse = interCustFundTransferRepository.getCustomDutyReport(fromDate1, toDate1, deptList.get(i).getDeptId(), cbsName);
                    if (!txnResponse.isEmpty()) {
                        txn.setDepartment(deptList.get(i).getDeptName());
                        txn.setCustomDutyReports(txnResponse);
                        responses.add(txn);
                    }
                }
            }
        } else {
            DepartmentWiseCustomeDutyReport txn = new DepartmentWiseCustomeDutyReport();
            txnResponse = interCustFundTransferRepository.getCustomDutyReport(fromDate1, toDate1, dept, cbsName);
            if (!txnResponse.isEmpty()) {
                txn.setDepartment(txnResponse.get(0).getDeptName());
                txn.setCustomDutyReports(txnResponse);
                responses.add(txn);
            }
        }

        response.put("result", responses);
        return response;
    }

    @Override
    public Map<String, Object> getTransactionsForAcknowledgementSlip(String fromDate, String toDate, String reference, String payerAcc, String benAcc, String routingType, String currency) {
        Map<String, Object> response = new HashMap<>();

        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }

        if (StringUtils.isBlank(reference)) {
            reference = "%%";
        }
        if (StringUtils.isBlank(payerAcc)) {
            payerAcc = "%%";
        }
        if (StringUtils.isBlank(benAcc)) {
            benAcc = "%%";
        }
        if (StringUtils.isBlank(routingType)) {
            routingType = "%%";
        }
        if (StringUtils.isBlank(currency)) {
            currency = "%%";
        }
        var responses = customerFndTransferRepository.getAcknowledgementSlip(fromDate1, toDate1, reference, payerAcc, benAcc, currency, routingType);
        response.put("result", responses);
        return response;
    }

    @Override
    public Map<String, Object> getSettlementAccountRegisterReport(String fromDate, String toDate, String reference, String voucher, String accounting, String settlementAcc) {
        Map<String, Object> response = new HashMap<>();

        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }

        if (StringUtils.isBlank(reference)) {
            reference = "%%";
        }
        if (StringUtils.isBlank(voucher)) {
            voucher = "%%";
        }
        if (StringUtils.isBlank(accounting)) {
            accounting = "%%";
        }
        if (StringUtils.isBlank(settlementAcc)) {
            settlementAcc = "%%";
        }

        var responses = registerRepository.getSettlementAccountRegisterReport(fromDate1, toDate1, reference, voucher, settlementAcc);
        response.put("result", responses);
        return response;
    }

    @Override
    public Map<String, Object> getBbSettlementReport(String txnDate, String currency) {
        Map<String, Object> response = new HashMap<>();

        LocalDate txnDate1;

        if (!txnDate.equals("")) {
            txnDate1 = DateUtility.toDate(txnDate);
        } else {
            txnDate1 = LocalDate.now();
        }

        var responses = settlementAccountStatementDetailRepository.getBbSettlementReport(txnDate1, currency);
        response.put("result", responses);
        return response;
    }

    @Override
    public Map<String, Object> getReportForCustomerTxn(String fromDate, String toDate, String bank, String currency, String routingType, String status, String dept, String cbsName) {
        Map<String, Object> response = new HashMap<>();

        List<RtgsTransactionReport> txnResponse;
        List<BankTxnReport> responses = new ArrayList<>();
        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }
        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }
        if (StringUtils.isBlank(dept)) {
            dept = "%%";
        }
        if (routingType.equals(RoutingType.Outgoing.toString())) {
            if (StringUtils.isBlank(bank) || bank.equalsIgnoreCase("All")) {
                List<BankList> bankList = interCustFundTransferRepository.getBeneficiaryBankList(fromDate1, toDate1, currency, status, dept, cbsName);
                if (bankList.size() > 0) {
                    for (int i = 0; i < bankList.size(); i++) {
                        BankTxnReport txn = new BankTxnReport();
                        txnResponse = interCustFundTransferRepository.getReportForOutwardCustomerTxn(fromDate1, toDate1, bankList.get(i).getBank(), currency, status, dept, cbsName);
                        if (!txnResponse.isEmpty()) {
                            txn.setBank(bankList.get(i).getBank());
                            txn.setRtgsTxnList(txnResponse);
                            responses.add(txn);
                        }
                    }
                }
            } else {
                BankTxnReport txn = new BankTxnReport();
                txnResponse = interCustFundTransferRepository.getReportForOutwardCustomerTxn(fromDate1, toDate1, bank, currency, status, dept, cbsName);
                if (!txnResponse.isEmpty()) {
                    txn.setBank(bank);
                    txn.setRtgsTxnList(txnResponse);
                    responses.add(txn);
                }
            }
        }
        if (routingType.equals(RoutingType.Incoming.toString())) {
            if (StringUtils.isBlank(bank) || bank.equalsIgnoreCase("All")) {
                List<BankList> bankList = interCustFundTransferRepository.getPayerBankList(fromDate1, toDate1, currency, status, dept, cbsName);
                if (bankList.size() > 0) {
                    for (int i = 0; i < bankList.size(); i++) {
                        BankTxnReport txn = new BankTxnReport();
                        txnResponse = interCustFundTransferRepository.getReportForInwardCustomerTxn(fromDate1, toDate1, bankList.get(i).getBank(), currency, status, dept, cbsName);
                        if (!txnResponse.isEmpty()) {
                            txn.setBank(bankList.get(i).getBank());
                            txn.setRtgsTxnList(txnResponse);
                            responses.add(txn);
                        }
                    }
                }
            } else {
                BankTxnReport txn = new BankTxnReport();
                txnResponse = interCustFundTransferRepository.getReportForInwardCustomerTxn(fromDate1, toDate1, bank, currency, status, dept, cbsName);
                if (!txnResponse.isEmpty()) {
                    txn.setBank(bank);
                    txn.setRtgsTxnList(txnResponse);
                    responses.add(txn);
                }
            }
        }

        response.put("result", responses);
        return response;
    }

    @Override
    public Map<String, Object> getReportForBankTxn(String fromDate, String toDate, String bank, String currency, String routingType, String status, String dept, String cbsName) {
        Map<String, Object> response = new HashMap<>();

        List<RtgsTransactionReport> txnResponse;
        List<BankTxnReport> responses = new ArrayList<>();

        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }
        if (StringUtils.isBlank(dept)) {
            dept = "%%";
        }

        if (routingType.equals(RoutingType.Outgoing.toString())) {
            if (StringUtils.isBlank(bank) || bank.equalsIgnoreCase("All")) {
                List<BankList> bankList = interBankFundTransferRepository.getBeneficiaryBankList(fromDate1, toDate1, currency, status, dept);
                if (bankList.size() > 0) {
                    for (int i = 0; i < bankList.size(); i++) {
                        BankTxnReport txn = new BankTxnReport();
                        txnResponse = interBankFundTransferRepository.getReportForOutwardBankTxn(fromDate1, toDate1, bankList.get(i).getBank(), currency, status, dept);
                        if (!txnResponse.isEmpty()) {
                            txn.setBank(bankList.get(i).getBank());
                            txn.setRtgsTxnList(txnResponse);
                            responses.add(txn);
                        }
                    }
                }
            } else {
                BankTxnReport txn = new BankTxnReport();
                txnResponse = interBankFundTransferRepository.getReportForOutwardBankTxn(fromDate1, toDate1, bank, currency, status, dept);
                if (!txnResponse.isEmpty()) {
                    txn.setBank(bank);
                    txn.setRtgsTxnList(txnResponse);
                    responses.add(txn);
                }
            }
        }
        if (routingType.equals(RoutingType.Incoming.toString())) {
            if (StringUtils.isBlank(bank) || bank.equalsIgnoreCase("All")) {
                List<BankList> bankList = interBankFundTransferRepository.getPayerBankList(fromDate1, toDate1, currency, status, dept);
                if (bankList.size() > 0) {
                    for (int i = 0; i < bankList.size(); i++) {
                        BankTxnReport txn = new BankTxnReport();
                        txnResponse = interBankFundTransferRepository.getReportForInwardBankTxn(fromDate1, toDate1, bankList.get(i).getBank(), currency, status, dept);
                        if (!txnResponse.isEmpty()) {
                            txn.setBank(bankList.get(i).getBank());
                            txn.setRtgsTxnList(txnResponse);
                            responses.add(txn);
                        }
                    }
                }
            } else {
                BankTxnReport txn = new BankTxnReport();
                txnResponse = interBankFundTransferRepository.getReportForInwardBankTxn(fromDate1, toDate1, bank, currency, status, dept);
                if (!txnResponse.isEmpty()) {
                    txn.setBank(bank);
                    txn.setRtgsTxnList(txnResponse);
                    responses.add(txn);
                }
            }
        }

        response.put("result", responses);
        return response;
    }

    @Override
    public Map<String, Object> getBranchWiseCustomerTxnReport(String fromDate, String toDate, String currency, String routingType, String status, String cbsName) {
        Map<String, Object> response = new HashMap<>();
        List<BranchWiseTxnReport> txnResponse;
        List<BranchTxnReport> responses = new ArrayList<>();
        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }

        if (StringUtils.isBlank(cbsName) || cbsName.equalsIgnoreCase("All")) {
            cbsName = "%%";
        }
        if (routingType.equals(RoutingType.Outgoing.toString())) {
            List<BranchList> branchList = interCustFundTransferRepository.getPayerBranchList(fromDate1, toDate1, currency, status, cbsName);
            if (branchList.size() > 0) {

                for (int i = 0; i < branchList.size(); i++) {
                    BranchTxnReport txn = new BranchTxnReport();
                    txnResponse = interCustFundTransferRepository.getBranchWiseReportForOutwardCustomerTxn(fromDate1, toDate1, currency, status, cbsName, branchList.get(i).getBranch());
                    if (!txnResponse.isEmpty()) {
                        txn.setBranch(branchList.get(i).getBranch());
                        txn.setRtgsTransactionReport(txnResponse);
                        responses.add(txn);
                    }
                }
            }
        }
        if (routingType.equals(RoutingType.Incoming.toString())) {
            List<BranchList> branchList = interCustFundTransferRepository.getBeneficiaryBranchList(fromDate1, toDate1, currency, status, cbsName);
            if (branchList.size() > 0) {

                for (int i = 0; i < branchList.size(); i++) {
                    BranchTxnReport txn = new BranchTxnReport();
                    txnResponse = interCustFundTransferRepository.getBranchWiseReportForInwardCustomerTxn(fromDate1, toDate1, currency, status, cbsName, branchList.get(i).getBranch());
                    if (!txnResponse.isEmpty()) {
                        txn.setBranch(branchList.get(i).getBranch());
                        txn.setRtgsTransactionReport(txnResponse);
                        responses.add(txn);
                    }
                }
            }
        }

        response.put("result", responses);
        return response;
    }

    @Override
    public Map<String, Object> getBranchWiseBankTxnReport(String fromDate, String toDate, String currency, String routingType, String status, String cbsName) {
        Map<String, Object> response = new HashMap<>();
        List<BranchTxnReport> responses = new ArrayList<>();
        List<BranchWiseTxnReport> txnResponse;
        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }

        if (routingType.equals(RoutingType.Outgoing.toString())) {
            List<BranchList> branchList = interBankFundTransferRepository.getPayerBranchList(fromDate1, toDate1, currency, status);
            if (branchList.size() > 0) {

                for (int i = 0; i < branchList.size(); i++) {
                    BranchTxnReport txn = new BranchTxnReport();
                    txnResponse = interBankFundTransferRepository.getBranchWiseReportForOutwardBankTxn(fromDate1, toDate1, currency, status, branchList.get(i).getBranch());
                    if (!txnResponse.isEmpty()) {
                        txn.setBranch(branchList.get(i).getBranch());
                        txn.setRtgsTransactionReport(txnResponse);
                        responses.add(txn);
                    }
                }

            }
        }
        if (routingType.equals(RoutingType.Incoming.toString())) {
            List<BranchList> branchList = interBankFundTransferRepository.getBeneficiaryBranchList(fromDate1, toDate1, currency, status);
            if (branchList.size() > 0) {

                for (int i = 0; i < branchList.size(); i++) {
                    BranchTxnReport txn = new BranchTxnReport();
                    txnResponse = interBankFundTransferRepository.getBranchWiseReportForInwardBankTxn(fromDate1, toDate1, currency, status, branchList.get(i).getBranch());
                    if (!txnResponse.isEmpty()) {
                        txn.setBranch(branchList.get(i).getBranch());
                        txn.setRtgsTransactionReport(txnResponse);
                        responses.add(txn);
                    }
                }

            }
        }
        response.put("result", responses);
        return response;
    }

    @Override
    public Map<String, Object> getErrorReport(String fromDate, String toDate) {
        Map<String, Object> response = new HashMap<>();

        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }

        var responses = failedTxnNotificationRepository.getErrorReport(fromDate1, toDate1);
        response.put("result", responses);
        return response;
    }

    @Override
    public Map<String, Object> getReconciledReport(String fromDate, String toDate, String currency, String dept) {
        Map<String, Object> response = new HashMap<>();
        List<ReconciledDepartmentReport> responses = new ArrayList<>();
        List<ReconciledReport> txnResponse;

        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }

        if (StringUtils.isBlank(dept)|| dept.equalsIgnoreCase("All")) {
            List<DepartmentList> deptList = reconcileDepartmentAccountRepository.getReconciledDepartmentList(fromDate1, toDate1, currency);
            if (deptList.size() > 0) {
                for (int i = 0; i < deptList.size(); i++) {
                    ReconciledDepartmentReport txn = new ReconciledDepartmentReport();
                    txnResponse = reconcileDepartmentAccountRepository.getReconciledReport(fromDate1, toDate1, currency, deptList.get(i).getDeptName());
                    if (!txnResponse.isEmpty()) {
                        txn.setDepartment(deptList.get(i).getDeptName());
                        txn.setReconciledReportList(txnResponse);
                        responses.add(txn);
                    }
                }
            }
        } else {
            ReconciledDepartmentReport txn = new ReconciledDepartmentReport();
            txnResponse = reconcileDepartmentAccountRepository.getReconciledReport(fromDate1, toDate1, currency, dept);
            if (!txnResponse.isEmpty()) {
                txn.setDepartment(dept);
                txn.setReconciledReportList(txnResponse);
                responses.add(txn);
            }
        }
        response.put("result", responses);
        return response;
    }

    @Override
    public Map<String, Object> getAbabilReport(String fromDate, String toDate, String currency, String routingType) {
        Map<String, Object> response = new HashMap<>();
        List<RtgsTransactionReport> responses = new ArrayList<>();

        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }
        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }
        if (StringUtils.isBlank(currency)) {
            currency = "%%";
        }

        if (routingType.equals(RoutingType.Outgoing.toString())) {
            responses = interCustFundTransferRepository.getAbabilReportForOutwardCustomerTxn(fromDate1, toDate1, currency);
        }
        if (routingType.equals(RoutingType.Incoming.toString())) {
            responses = interCustFundTransferRepository.getAbabilReportForInwardCustomerTxn(fromDate1, toDate1, currency);
        }
        response.put("result", responses);
        return response;
    }

    @Override
    public Map<String, Object> getReportForBankTxn(String fromDate, String toDate, String routingType, String reference) {
        Map<String, Object> response = new HashMap<>();
        List<RtgsTransactionReport> responses;

        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }

        if (StringUtils.isBlank(reference)) {
            reference = "%%";
        }
        if (StringUtils.isBlank(routingType)) {
            routingType = "%%";
        }
        responses = interBankFundTransferRepository.getBankTxnList(fromDate1, toDate1, reference, routingType);

        response.put("result", responses);
        return response;
    }

    @Override
    public Map<String, Object> getReportForCustomerTxn(String fromDate, String toDate, String routingType, String reference) {
        Map<String, Object> response = new HashMap<>();
        List<RtgsTransactionReport> responses;

        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }

        if (StringUtils.isBlank(reference)) {
            reference = "%%";
        }
        if (StringUtils.isBlank(routingType)) {
            routingType = "%%";
        }
        responses = interCustFundTransferRepository.getCustomerTxnList(fromDate1, toDate1, reference, routingType);

        response.put("result", responses);
        return response;
    }

    @Override
    public ResponseDTO getSIReport(String fromDate, String toDate, String branch, String currencyCode) {

        LocalDate from = null;
        LocalDate to = null;

        if (!fromDate.equals("") && !toDate.equals("")) {
            from = DateUtility.toDate(fromDate);
            to = DateUtility.toDate(toDate);
        }

        List<SiReport> report = customerFndTransferRepository.getSIReport(from, to, branch, currencyCode);

        return ResponseDTO.builder()
                .error(false)
                .message(report.size() + " data found")
                .body(report)
                .build();
    }
}
