package com.cbl.cityrtgs.services.transaction;

import com.cbl.cityrtgs.common.utility.DateUtility;
import com.cbl.cityrtgs.models.dto.projection.BankFundTransferOutwardProjection;
import com.cbl.cityrtgs.models.dto.projection.BankFundTransferProjection;
import com.cbl.cityrtgs.models.dto.transaction.TransactionSearchFilter;
import com.cbl.cityrtgs.models.dto.transaction.c2c.C2CTxnTransactionResponse;
import com.cbl.cityrtgs.repositories.transaction.b2b.BankFndTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.InterCustomerFundTransferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionSearchService {
    private final BankFndTransferRepository repository;
    private final InterCustomerFundTransferRepository interCustomerFundTransferRepository;

    public List<BankFundTransferProjection> getAllBankTxn(TransactionSearchFilter filter) {

        LocalDate fromDate;
        LocalDate toDate;
        String currency = filter.getCurrency();
        String bank = filter.getBank();
        String voucher = filter.getVoucher();
        String reference = filter.getReference();
        String batchNumber = filter.getBatchNumber();
        String transactionStatus = filter.getTransactionStatus();

        if (!filter.getFromDate().isEmpty()) {
            fromDate = DateUtility.toDate(filter.getFromDate());
        } else {
            fromDate = LocalDate.now();
        }

        if (!filter.getToDate().isEmpty()) {
            toDate = DateUtility.toDate(filter.getToDate());
        } else {
            toDate = LocalDate.now();
        }

        if (StringUtils.isBlank(currency)) {
            currency = "%%";
        }
        if (StringUtils.isBlank(bank)) {
            bank = "%%";
        }
        if (StringUtils.isBlank(voucher)) {
            voucher = "%%";
        }
        if (StringUtils.isBlank(reference)) {
            reference = "%%";
        }
        if (StringUtils.isBlank(batchNumber)) {
            batchNumber = "%%";
        }

        if (StringUtils.isBlank(transactionStatus)) {
            transactionStatus = "%%";
        }

        return repository.getAllB2BInward(fromDate,
                toDate,
                filter.getRoutingType().name(),
                currency,
                batchNumber,
                reference,
                voucher,
                transactionStatus,
                bank
        );


    }

    public List<BankFundTransferOutwardProjection> getAllBankTxnOutward(TransactionSearchFilter filter) {

        LocalDate fromDate;
        LocalDate toDate;
        String currency = filter.getCurrency();
        String bank = filter.getBank();
        String voucher = filter.getVoucher();
        String reference = filter.getReference();
        String batchNumber = filter.getBatchNumber();
        String transactionStatus = filter.getTransactionStatus();
        String dept = filter.getDept();

        if (!filter.getFromDate().isEmpty()) {
            fromDate = DateUtility.toDate(filter.getFromDate());
        } else {
            fromDate = LocalDate.now();
        }

        if (!filter.getToDate().isEmpty()) {
            toDate = DateUtility.toDate(filter.getToDate());
        } else {
            toDate = LocalDate.now();
        }

        if (StringUtils.isBlank(currency)) {
            currency = "%%";
        }
        if (StringUtils.isBlank(bank)) {
            bank = "%%";
        }
        if (StringUtils.isBlank(voucher)) {
            voucher = "%%";
        }
        if (StringUtils.isBlank(reference)) {
            reference = "%%";
        }
        if (StringUtils.isBlank(batchNumber)) {
            batchNumber = "%%";
        }

        if (StringUtils.isBlank(transactionStatus)) {
            transactionStatus = "%%";
        }

        if (StringUtils.isBlank(dept)) {
            dept = "%%";
        }

        return repository.getAllB2BOutWard(fromDate,
                toDate,
                filter.getRoutingType().name(),
                currency,
                batchNumber,
                reference,
                voucher,
                transactionStatus,
                bank,
                dept
        );
    }

    public List<C2CTxnTransactionResponse> getAllOutwardCustomerTxn(String fromDate, String toDate, String bank, String currency, String dept, String transactionStatus, String voucher, String reference, String batchNumber, String payerAccount, String benAccount) {
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
        if (StringUtils.isBlank(currency)) {
            currency = "%%";
        }
        if (StringUtils.isBlank(bank)) {
            bank = "%%";
        }
        if (StringUtils.isBlank(voucher)) {
            voucher = "%%";
        }
        if (StringUtils.isBlank(reference)) {
            reference = "%%";
        }
        if (StringUtils.isBlank(batchNumber)) {
            batchNumber = "%%";
        }
        if (StringUtils.isBlank(payerAccount)) {
            payerAccount = "%%";
        }
        if (StringUtils.isBlank(benAccount)) {
            benAccount = "%%";
        }
        if (StringUtils.isBlank(transactionStatus)) {
            transactionStatus = "%%";
        }
        return interCustomerFundTransferRepository.getOutwardCustomerTxn(fromDate1, toDate1, bank, currency, dept, transactionStatus, voucher, reference, batchNumber, payerAccount, benAccount);
    }

    public List<C2CTxnTransactionResponse> getAllInwardCustomerTxn(String fromDate, String toDate, String bank, String currency, String transactionStatus, String voucher, String reference, String batchNumber, String payerAccount, String benAccount) {
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
        if (StringUtils.isBlank(bank)) {
            bank = "%%";
        }
        if (StringUtils.isBlank(voucher)) {
            voucher = "%%";
        }
        if (StringUtils.isBlank(reference)) {
            reference = "%%";
        }
        if (StringUtils.isBlank(batchNumber)) {
            batchNumber = "%%";
        }
        if (StringUtils.isBlank(payerAccount)) {
            payerAccount = "%%";
        }
        if (StringUtils.isBlank(benAccount)) {
            benAccount = "%%";
        }
        if (StringUtils.isBlank(transactionStatus)) {
            transactionStatus = "%%";
        }
        return interCustomerFundTransferRepository.getInwardCustomerTxn(fromDate1, toDate1, bank, currency, transactionStatus, voucher, reference, batchNumber, payerAccount, benAccount);
    }
}
