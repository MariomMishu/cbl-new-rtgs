package com.cbl.cityrtgs.repositories.transaction.c2c;

import com.cbl.cityrtgs.models.dto.report.IbReport;
import com.cbl.cityrtgs.models.entitymodels.transaction.IbTransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface IbTransactionRepository
        extends JpaRepository<IbTransactionEntity, Long>, JpaSpecificationExecutor<IbTransactionEntity> {
    Page<IbTransactionEntity> findAllByIsDeletedFalse(Pageable pageable);
    Optional<IbTransactionEntity> findByResponseReferenceAndIsDeletedFalse(String responseReference);
    List<IbTransactionEntity> findAllByIsDeletedFalse();
    @Query(value = "SELECT TXN.TRANSACTIONDATE, " +
            "TXN.ROUTINGTYPE, " +
            "TXN.FUNDTRANSFERTYPE, " +
            "TXN.BENACCOUNT AS BeneficiaryAccountNo, " +
            "TXN.BENNAME AS beneficiaryName, " +
            "TXN.PAYERACCOUNT AS payerAccNo, " +
            "TXN.PAYERNAME, " +
            "TXN.REQUESTREFERENCE, " +
            "TXN.RESPONSEREFERENCE, " +
            "BANK.NAME AS beneficiaryBank, " +
            "TXN.CURRENCY, " +
            "TXN.AMOUNT, " +
            "TXN.TXNSTATUS, " +
            "TBL_CBS_TXN_LOG.CBSREFERENCENUMBER AS VoucherNumber " +
            "FROM TBL_IB_TRANSACTION TXN, RTGS_BRANCHS BRANCH, RTGS_BANKS BANK, TBL_CBS_TXN_LOG " +
            "WHERE " +
            "TXN.BENBRANCHROUTINGNO = BRANCH.ROUTINGNUMBER  " +
            "AND BRANCH.BANK_ID = BANK.ID  " +
            "AND (:status IS NULL OR  TXN.TXNSTATUS =:status) " +
            "AND (:currency IS NULL OR  TXN.CURRENCY =:currency) " +
            "AND (:deliveryChannel IS NULL OR  TXN.DELIVERYCHANNEL =:deliveryChannel) " +
            "AND TBL_CBS_TXN_LOG.RTGSREFERENCENUMBER = TXN.RESPONSEREFERENCE  " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate  " +
            "ORDER BY TXN.TRANSACTIONDATE DESC ", nativeQuery = true)
    List<IbReport> getIBTransactionReport(LocalDate fromDate, LocalDate toDate, String currency, String status, String deliveryChannel);

    @Query(value = "SELECT TXN.TRANSACTIONDATE, " +
            "TXN.ROUTINGTYPE, " +
            "TXN.FUNDTRANSFERTYPE, " +
            "TXN.BENACCOUNT AS BeneficiaryAccountNo, " +
            "TXN.BENNAME AS beneficiaryName, " +
            "TXN.PAYERACCOUNT AS payerAccNo, " +
            "TXN.PAYERNAME, " +
            "TXN.REQUESTREFERENCE, " +
            "TXN.RESPONSEREFERENCE, " +
            "BANK.NAME AS beneficiaryBank, " +
            "TXN.CURRENCY, " +
            "TXN.AMOUNT,  " +
            "TXN.TXNSTATUS,  " +
            "TBL_CBS_TXN_LOG.CBSREFERENCENUMBER AS VoucherNumber " +
            "FROM TBL_IB_TRANSACTION TXN, RTGS_BRANCHS BRANCH, RTGS_BANKS BANK, TBL_CBS_TXN_LOG " +
            "WHERE  " +
            "TXN.BENBRANCHROUTINGNO = BRANCH.ROUTINGNUMBER  " +
            "AND BRANCH.BANK_ID = BANK.ID  " +
            "AND TXN.TXNSTATUS = 'PENDING' " +
            "AND TXN.CURRENCY = 'BDT' " +
            "AND TXN.DELIVERYCHANNEL = 'TREASURY' " +
            "AND TBL_CBS_TXN_LOG.RTGSREFERENCENUMBER = TXN.RESPONSEREFERENCE  " +
            "AND TRUNC(TRANSACTIONDATE) >= TO_DATE('2023-02-22', 'YYYY-MM-dd') " +
            "AND TRUNC(TRANSACTIONDATE) <= TO_DATE('2023-12-11', 'YYYY-MM-dd') " +
            "ORDER BY TXN.TRANSACTIONDATE DESC ", nativeQuery = true)
    List<IbReport> getIBTransactionReportTest();

}
