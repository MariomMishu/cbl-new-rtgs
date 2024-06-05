package com.cbl.cityrtgs.repositories.transaction.c2c;

import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.report.*;
import com.cbl.cityrtgs.models.dto.transaction.IApprovalEventResponse;
import com.cbl.cityrtgs.models.dto.transaction.c2c.C2CTxnTransactionResponse;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.InterCustomerFundTransferEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
//@Transactional
public interface InterCustomerFundTransferRepository extends JpaRepository<InterCustomerFundTransferEntity, Long> {
    Page<InterCustomerFundTransferEntity> findAllByIsDeletedFalse(Pageable pageable);

    Optional<InterCustomerFundTransferEntity> findByIdAndIsDeletedFalse(Long id);

    Optional<InterCustomerFundTransferEntity> findByBatchNumberAndIsDeletedFalse(String batchNumber);
    List<InterCustomerFundTransferEntity> findAllByBatchNumberAndIsDeletedFalse(String batchNumber);

    Optional<InterCustomerFundTransferEntity> findByBatchNumberAndRoutingTypeAndIsDeletedFalse(String batchNumber, RoutingType routingType);

    List<InterCustomerFundTransferEntity> findAllByIsDeletedFalse();


    @Query(value = "SELECT * FROM TBL_INTERCUSTFNDTRANSFER " +
            "WHERE VERIFICATIONSTATUS IN (4,7) " +
            "AND ROUTINGTYPE = 'Incoming' AND TO_CHAR(CREATEDATE, 'YYYY-MM-dd') = " +
            "(SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) ORDER BY ID ASC", nativeQuery = true)
    List<InterCustomerFundTransferEntity> findAllByVerificationStatusInAndRoutingTypeAndIsDeletedFalse();

    @Query(value = "SELECT * FROM TBL_INTERCUSTFNDTRANSFER " +
            "WHERE VERIFICATIONSTATUS = 2 " +
            "AND ROUTINGTYPE = 'Incoming' AND TO_CHAR(CREATEDAT, 'YYYY-MM-dd') = " +
            "(SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) ORDER BY ID ASC", nativeQuery = true)
    List<InterCustomerFundTransferEntity> findAllByVerificationStatusAndRoutingType();
    @Query(value = "SELECT * FROM TBL_INTERCUSTFNDTRANSFER " +
            "WHERE VERIFICATIONSTATUS = 0 " +
            "AND ROUTINGTYPE = 'Outgoing' AND TO_CHAR(CREATEDAT, 'YYYY-MM-dd') = " +
            "(SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) ORDER BY ID ASC", nativeQuery = true)
    List<InterCustomerFundTransferEntity> getAllPendingTxn();

    @Query(value = "SELECT * FROM TBL_INTERCUSTFNDTRANSFER " +
            "WHERE BATCHNUMBER = :batchNumber " +
            "AND ROUTINGTYPE = 'Outgoing' AND TO_CHAR(CREATEDAT, 'YYYY-MM-dd') = " +
            "(SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) ", nativeQuery = true)
    Optional<InterCustomerFundTransferEntity> getByBatchNumberAndRoutingTypeAndIsDeletedFalse(String batchNumber);

    @Query(value = "SELECT * FROM TBL_INTERCUSTFNDTRANSFER " +
            "WHERE BATCHNUMBER = :batchNumber " +
            "AND ROUTINGTYPE = 'Incoming' AND TO_CHAR(CREATEDAT, 'YYYY-MM-dd') = " +
            "(SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) ", nativeQuery = true)
    Optional<InterCustomerFundTransferEntity> getByBatchNumberAndRoutingTypeAndDate(String batchNumber);
    @Query(value = "SELECT MAKER, MAKER_BRANCH, MAKER_DEPARTMENT, CHECKER, CHECKER_BRANCH, CHECKER_DEPARTMENT, MAKER_NAME, " +
            "CHECKER_NAME, TRANSACTIONDATE, PAYER_ACC_NO, PAYER_NAME, PAYER_BANK_NAME, BEN_ACC_NO, " +
            "BEN_NAME, BEN_BANK_NAME, AMOUNT, REFERENCENUMBER, CURRENCY, PARENTBATCHNUMBER, PAYERBRANCH, " +
            "BENEFICIARYBRANCH, TRANSACTIONSTATUS, ROUTINGTYPE, FUNDTRANSFER_TYPE, NARRATION, " +
            "RETURNCODE, RETURNREASON " +
            "FROM USERWISEREPORT, " +
            "TBL_RTGS_CURRENCIES CUR " +
            "WHERE " +
            "CURRENCY = CUR.SHORTCODE " +
            "AND (:routingType IS NULL OR ROUTINGTYPE = :routingType) " +
            "AND (:user IS NULL OR MAKER = :user) " +
            "AND (:currency IS NULL OR CURRENCY = :currency) " +
            "AND TRUNC(TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TRANSACTIONDATE) <= :toDate " +
            "ORDER BY MAKER DESC ", nativeQuery = true)
    List<UserWiseTxnReport> getUserWiseReport(LocalDate fromDate, LocalDate toDate, String routingType, String currency, String user);

    @Query(value = "SELECT " +
            "TXN.TRANSACTIONDATE, " +
            "TXN.BRANCH_NAME AS branch, " +
            "SUM(CASE WHEN TXN.ROUTINGTYPE = 'Incoming' AND TXN.TRANSACTIONSTATUS = 'Confirmed' THEN 1 ELSE 0 END) totalInwardCount, " +
            "SUM(CASE WHEN TXN.ROUTINGTYPE = 'Incoming' AND TXN.TRANSACTIONSTATUS = 'Confirmed' THEN AMOUNT ELSE 0 END) totalInwardAmount, " +
            "SUM(CASE WHEN TXN.ROUTINGTYPE = 'Outgoing' AND TXN.TRANSACTIONSTATUS = 'Confirmed' THEN 1 ELSE 0 END) totalOutwardCount, " +
            "SUM(CASE WHEN TXN.ROUTINGTYPE = 'Outgoing' AND TXN.TRANSACTIONSTATUS = 'Confirmed' THEN AMOUNT ELSE 0 END) totalOutwardAmount, " +
            "SUM(CASE WHEN TXN.ROUTINGTYPE = 'Incoming' AND TXN.TRANSACTIONSTATUS = 'Returned' THEN 1 ELSE 0 END) totalInwardReversedCount, " +
            "SUM(CASE WHEN TXN.ROUTINGTYPE = 'Incoming' AND TXN.TRANSACTIONSTATUS = 'Returned' THEN AMOUNT ELSE 0 END) totalInwardReversedAmount, " +
            "SUM(CASE WHEN TXN.ROUTINGTYPE = 'Outgoing' AND (TXN.TRANSACTIONSTATUS = 'Reversed' OR TXN.TRANSACTIONSTATUS = 'Rejected') THEN 1 ELSE 0 END) totalOutwardReversedCount, " +
            "SUM(CASE WHEN TXN.ROUTINGTYPE = 'Outgoing' AND (TXN.TRANSACTIONSTATUS = 'Reversed' OR TXN.TRANSACTIONSTATUS = 'Rejected') THEN AMOUNT ELSE 0 END) totalOutwardReversedAmount, " +
            "SUM(CASE WHEN TXN.ROUTINGTYPE = 'Incoming' AND TXN.TRANSACTIONSTATUS = 'Pending' THEN 1 ELSE 0 END) totalInwardPendingCount, " +
            "SUM(CASE WHEN TXN.ROUTINGTYPE = 'Incoming' AND TXN.TRANSACTIONSTATUS = 'Pending' THEN AMOUNT ELSE 0 END) totalInwardPendingAmount, " +
            "SUM(CASE WHEN TXN.ROUTINGTYPE = 'Outgoing' AND TXN.TRANSACTIONSTATUS = 'Pending' THEN 1 ELSE 0 END) totalOutwardPendingCount, " +
            "SUM(CASE WHEN TXN.ROUTINGTYPE = 'Outgoing' AND TXN.TRANSACTIONSTATUS = 'Pending' THEN AMOUNT ELSE 0 END) totalOutwardPendingAmount " +
            "FROM (" +
            "SELECT BR.NAME BRANCH_NAME, TRUNC(TXN.TRANSACTIONDATE) TRANSACTIONDATE, TXN.ROUTINGTYPE, TXN.TRANSACTIONSTATUS, TXN.AMOUNT " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN, RTGS_BRANCHS BR " +
            "WHERE TXN.BEN_BRANCH_ID = BR.ID " +
            "AND TXN.ROUTINGTYPE = 'Incoming' " +
            "AND TXN.CURRENCY_ID =:currency " +
            "AND TRUNC(TRANSACTIONDATE) = :txnDate  " +
            "UNION ALL " +
            "SELECT BR.NAME BRANCH_NAME, TRUNC(TXN.TRANSACTIONDATE) TRANSACTIONDATE, TXN.ROUTINGTYPE, TXN.TRANSACTIONSTATUS, TXN.AMOUNT " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN, RTGS_BRANCHS BR " +
            "WHERE TXN.PAYER_BRANCH_ID = BR.ID " +
            "AND TXN.ROUTINGTYPE = 'Outgoing' " +
            "AND TXN.CURRENCY_ID =:currency " +
            "AND TRUNC(TRANSACTIONDATE) = :txnDate " +
            ") TXN " +
            "GROUP BY TXN.TRANSACTIONDATE, TXN.BRANCH_NAME ", nativeQuery = true)
    List<TxnSummaryReport> getC2CFundTransferSummaryReport(LocalDate txnDate, String currency);


    @Query(value = "SELECT TXN.TRANSACTIONDATE AS transactionDate, TXN.PAYER_ACC_NO AS payerAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBank, TXN.BEN_ACC_NO AS beneficiaryAccNo, TXN.BEN_NAME AS beneficiaryName, BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency, TXN.PARENTBATCHNUMBER AS batchNumber, TXN.RMT_REG_YEAR AS regYear, TXN.RMT_REG_NUM AS regNum, TXN.RMT_CUST_OFFICE_CODE AS custOfficeCode, TXN.RMT_DECLARANT_CODE AS declarantCode, " +
            "TXN.RMT_CUS_CELL_NO AS custCellNo, BRN.NAME AS payerBranch, BRNTWO.NAME AS beneficiaryBranch, TXN.CHARGE AS charge, TXN.VAT AS vat, TXN.VOUCHERNUMBER AS voucher, TXN.TRANSACTIONSTATUS AS txnStatus, DEPT.ID AS deptId, DEPT.NAME AS deptName " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERCUSTFNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE " +
            "TXN.DEPARTMENT_ID = :dept AND " +
            "CUR.SHORTCODE = 'BDT' " +
            "AND TXN.ROUTINGTYPE = 'Outgoing' " +
            "AND PTXN.TXNTYPECODE = '041' " +
            "AND TXN.CBSNAME like :cbsName " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.TRANSACTIONDATE DESC ", nativeQuery = true)
    List<CustomDutyReport> getCustomDutyReport(LocalDate fromDate, LocalDate toDate, String dept, String cbsName);

    @Query(value = "SELECT TXN.TRANSACTIONDATE AS txnDate, TXN.PAYER_ACC_NO AS payerAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBank, TXN.BEN_ACC_NO AS beneficiaryAccNo, TXN.BEN_NAME AS beneficiaryName, BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency, TXN.NARRATION AS narration, TXN.LCNUMBER AS lcNumber, TXN.ROUTINGTYPE AS routingType,  " +
            "BRN.NAME AS payerBranch, BRNTWO.NAME AS beneficiaryBranch, TXN.VOUCHERNUMBER AS voucher, TXN.TRANSACTIONSTATUS AS txnStatus, DEPT.NAME AS deptName " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERCUSTFNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE " +
            "CUR.SHORTCODE like :currency " +
            "AND DEPT.NAME like :dept " +
            "AND BNKTWO.NAME like :bank " +
            "AND TXN.ROUTINGTYPE = 'Outgoing' " +
            "AND TXN.TRANSACTIONSTATUS = :status " +
            "AND TXN.CBSNAME = :cbsName " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.DEPARTMENT_ID DESC, TXN.TRANSACTIONSTATUS ", nativeQuery = true)
    List<RtgsTransactionReport> getReportForOutwardCustomerTxn(LocalDate fromDate, LocalDate toDate, String bank, String currency, String status, String dept, String cbsName);


    @Query(value = "SELECT TXN.TRANSACTIONDATE AS txnDate, TXN.PAYER_ACC_NO AS payerAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBank, TXN.BEN_ACC_NO AS beneficiaryAccNo, TXN.BEN_NAME AS beneficiaryName, BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency, TXN.NARRATION AS narration, TXN.LCNUMBER AS lcNumber, TXN.ROUTINGTYPE AS routingType, " +
            "BRN.NAME AS payerBranch, BRNTWO.NAME AS beneficiaryBranch, TXN.VOUCHERNUMBER AS voucher, TXN.TRANSACTIONSTATUS AS txnStatus, DEPT.NAME AS deptName " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERCUSTFNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE " +
            "CUR.SHORTCODE like :currency " +
            "AND DEPT.NAME like :dept " +
            "AND BNK.NAME like :bank " +
            "AND TXN.ROUTINGTYPE = 'Incoming' " +
            "AND TXN.TRANSACTIONSTATUS = :status " +
            "AND TXN.CBSNAME = :cbsName " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.DEPARTMENT_ID DESC, TXN.TRANSACTIONSTATUS ", nativeQuery = true)
    List<RtgsTransactionReport> getReportForInwardCustomerTxn(LocalDate fromDate, LocalDate toDate, String bank, String currency, String status, String dept, String cbsName);

    @Query(value = "SELECT * FROM (SELECT TXN.TRANSACTIONDATE AS txnDate, TXN.PAYER_ACC_NO AS debitorAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBank, TXN.BEN_ACC_NO AS creditorAccNo, TXN.BEN_NAME AS beneficiaryName, BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency, TXN.NARRATION AS narration, TXN.LCNUMBER AS lcNumber, TXN.ROUTINGTYPE AS routingType, " +
            "BRN.NAME AS payerBranch, BRNTWO.NAME AS beneficiaryBranch, TXN.VOUCHERNUMBER AS voucher, TXN.TRANSACTIONSTATUS AS txnStatus, DEPT.NAME AS deptName, PTXN.ENTRYUSER AS MAKER,  PTXN.APPROVER AS CHECKER " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERCUSTFNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE " +
            "CUR.SHORTCODE like :currency " +
            "AND TXN.ROUTINGTYPE = 'Outgoing' " +
            "AND TXN.TRANSACTIONSTATUS = :status " +
            "AND TXN.CBSNAME = :cbsName " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.PAYER_BRANCH_ID ASC ) WHERE payerBranch like :branch ", nativeQuery = true)
    List<BranchWiseTxnReport> getBranchWiseReportForOutwardCustomerTxn(LocalDate fromDate, LocalDate toDate, String currency, String status, String cbsName, String branch);

    @Query(value = "SELECT * FROM (SELECT TXN.TRANSACTIONDATE AS txnDate, TXN.PAYER_ACC_NO AS debitorAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBank, TXN.BEN_ACC_NO AS creditorAccNo, TXN.BEN_NAME AS beneficiaryName, BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency, TXN.NARRATION AS narration, TXN.LCNUMBER AS lcNumber, TXN.ROUTINGTYPE AS routingType, " +
            "BRN.NAME AS payerBranch, BRNTWO.NAME AS beneficiaryBranch, TXN.VOUCHERNUMBER AS voucher, TXN.TRANSACTIONSTATUS AS txnStatus, DEPT.NAME AS deptName, PTXN.ENTRYUSER AS MAKER,  PTXN.APPROVER AS CHECKER " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERCUSTFNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE " +
            "CUR.SHORTCODE like :currency " +
            "AND TXN.ROUTINGTYPE = 'Incoming' " +
            "AND TXN.TRANSACTIONSTATUS = :status " +
            "AND TXN.CBSNAME = :cbsName " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.BEN_BRANCH_ID ASC ) WHERE beneficiaryBranch like :branch  ", nativeQuery = true)
    List<BranchWiseTxnReport> getBranchWiseReportForInwardCustomerTxn(LocalDate fromDate, LocalDate toDate, String currency, String status, String cbsName, String branch);


    @Query(value = "SELECT COUNT(T.ID) AS txnNumber, T.payerBranch AS branch " +
            "FROM " +
            "(SELECT TXN.ID as id, TXN.TRANSACTIONDATE AS txnDate, TXN.PAYER_ACC_NO AS payerAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBank, TXN.BEN_ACC_NO AS beneficiaryAccNo, TXN.BEN_NAME AS beneficiaryName, BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency, TXN.NARRATION AS narration, TXN.LCNUMBER AS lcNumber, TXN.ROUTINGTYPE AS routingType, " +
            "BRN.NAME AS payerBranch, BRNTWO.NAME AS beneficiaryBranch, TXN.VOUCHERNUMBER AS voucher, TXN.TRANSACTIONSTATUS AS txnStatus, DEPT.NAME AS deptName " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERCUSTFNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE " +
            "CUR.SHORTCODE like :currency " +
            "AND TXN.ROUTINGTYPE = 'Outgoing' " +
            "AND TXN.TRANSACTIONSTATUS = :status " +
            "AND TXN.CBSNAME like :cbsName " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY BRN.ID ASC ) T GROUP BY T.payerBranch ", nativeQuery = true)
    List<BranchList> getPayerBranchList(LocalDate fromDate, LocalDate toDate, String currency, String status, String cbsName);

    @Query(value = "SELECT COUNT(T.ID) AS txnNumber, T.beneficiaryBranch AS branch " +
            "FROM " +
            "(SELECT TXN.ID as id, TXN.TRANSACTIONDATE AS txnDate, TXN.PAYER_ACC_NO AS payerAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBank, TXN.BEN_ACC_NO AS beneficiaryAccNo, TXN.BEN_NAME AS beneficiaryName, BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency, TXN.NARRATION AS narration, TXN.LCNUMBER AS lcNumber, TXN.ROUTINGTYPE AS routingType, " +
            "BRN.NAME AS payerBranch, BRNTWO.NAME AS beneficiaryBranch, TXN.VOUCHERNUMBER AS voucher, TXN.TRANSACTIONSTATUS AS txnStatus, DEPT.NAME AS deptName " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERCUSTFNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE " +
            "CUR.SHORTCODE like :currency " +
            "AND TXN.ROUTINGTYPE = 'Incoming' " +
            "AND TXN.TRANSACTIONSTATUS = :status " +
            "AND TXN.CBSNAME = :cbsName " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY BRNTWO.ID ) T GROUP BY T.beneficiaryBranch ", nativeQuery = true)
    List<BranchList> getBeneficiaryBranchList(LocalDate fromDate, LocalDate toDate, String currency, String status, String cbsName);

    @Query(value = "SELECT COUNT(T.ID) AS txnNumber, T.beneficiaryBank AS bank " +
            "FROM " +
            "(SELECT TXN.ID as id, TXN.TRANSACTIONDATE AS txnDate, TXN.PAYER_ACC_NO AS payerAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBank, TXN.BEN_ACC_NO AS beneficiaryAccNo, TXN.BEN_NAME AS beneficiaryName, BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency, TXN.NARRATION AS narration, TXN.LCNUMBER AS lcNumber, TXN.ROUTINGTYPE AS routingType, " +
            "BRN.NAME AS payerBranch, BRNTWO.NAME AS beneficiaryBranch, TXN.VOUCHERNUMBER AS voucher, TXN.TRANSACTIONSTATUS AS txnStatus, DEPT.NAME AS deptName " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERCUSTFNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE " +
            "CUR.SHORTCODE like :currency " +
            "AND DEPT.NAME like :dept " +
            "AND TXN.ROUTINGTYPE = 'Outgoing' " +
            "AND TXN.TRANSACTIONSTATUS = :status " +
            "AND TXN.CBSNAME = :cbsName " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.BEN_BANK_ID DESC ) " +
            "T GROUP BY T.beneficiaryBank ", nativeQuery = true)
    List<BankList> getBeneficiaryBankList(LocalDate fromDate, LocalDate toDate, String currency, String status, String dept, String cbsName);

    @Query(value = "SELECT COUNT(T.ID) AS txnNumber, T.payerBank AS bank " +
            "FROM " +
            "(SELECT TXN.ID as id, TXN.TRANSACTIONDATE AS txnDate, BNK.NAME AS payerBank, BNKTWO.NAME AS beneficiaryBank, " +
            "BRN.NAME AS payerBranch, BRNTWO.NAME AS beneficiaryBranch " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERBANKFUNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE " +
            "CUR.SHORTCODE like :currency " +
            "AND DEPT.NAME like :dept " +
            "AND TXN.ROUTINGTYPE = 'Incoming' " +
            "AND TXN.TRANSACTIONSTATUS = :status " +
            "AND TXN.CBSNAME = :cbsName " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.PAYER_BANK_ID DESC ) T GROUP BY T.payerBank ", nativeQuery = true)
    List<BankList> getPayerBankList(LocalDate fromDate, LocalDate toDate, String currency, String status, String dept, String cbsName);

    @Query(value = "SELECT TXN.TRANSACTIONDATE AS txnDate, TXN.PAYER_ACC_NO AS payerAccNo, TXN.PAYER_NAME AS payerName, " +
            "BNK.NAME AS payerBank, TXN.BEN_ACC_NO AS beneficiaryAccNo, TXN.BEN_NAME AS beneficiaryName, BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN, RTGS_BANKS BNK, TBL_RTGS_CURRENCIES CUR, RTGS_BANKS BNKTWO " +
            "WHERE " +
            "TXN.PAYER_BANK_ID = BNK.ID " +
            "AND TXN.BEN_BANK_ID = BNKTWO.ID " +
            "AND TXN.CURRENCY_ID = CUR.ID " +
            "AND TXN.TRANSACTIONSTATUS ='Confirmed' " +
            "AND TXN.CBSNAME = 'ABABIL' " +
            "AND CUR.SHORTCODE like :currency " +
            "AND (TXN.ROUTINGTYPE ='Outgoing' AND TXN.PAYER_ACC_NO like '178%') " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.TRANSACTIONDATE DESC ", nativeQuery = true)
    List<RtgsTransactionReport> getAbabilReportForOutwardCustomerTxn(LocalDate fromDate, LocalDate toDate, String currency);

    @Query(value = "SELECT TXN.TRANSACTIONDATE AS txnDate, TXN.PAYER_ACC_NO AS payerAccNo, TXN.PAYER_NAME AS payerName, " +
            "BNK.NAME AS payerBank, TXN.BEN_ACC_NO AS beneficiaryAccNo, TXN.BEN_NAME AS beneficiaryName, " +
            "BNKTWO.NAME AS beneficiaryBank, TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN, RTGS_BANKS BNK, TBL_RTGS_CURRENCIES CUR, RTGS_BANKS BNKTWO " +
            "WHERE " +
            "TXN.PAYER_BANK_ID = BNK.ID " +
            "AND TXN.BEN_BANK_ID = BNKTWO.ID " +
            "AND TXN.CURRENCY_ID = CUR.ID " +
            "AND TXN.TRANSACTIONSTATUS ='Confirmed' " +
            "AND TXN.CBSNAME = 'ABABIL' " +
            "AND CUR.SHORTCODE like :currency " +
            "AND (TXN.ROUTINGTYPE ='Incoming' AND TXN.BEN_ACC_NO like '178%') " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.TRANSACTIONDATE DESC ", nativeQuery = true)
    List<RtgsTransactionReport> getAbabilReportForInwardCustomerTxn(LocalDate fromDate, LocalDate toDate, String currency);
    @Query(value = "SELECT TXN.TRANSACTIONDATE AS txnDate, TXN.PAYER_ACC_NO AS payerAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBank, " +
            "TXN.BEN_ACC_NO AS beneficiaryAccNo, TXN.BEN_NAME AS beneficiaryName, BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency, TXN.NARRATION AS narration, T" +
            "XN.LCNUMBER AS lcNumber, TXN.ROUTINGTYPE AS routingType,  " +
            "BRN.NAME AS payerBranch, BRNTWO.NAME AS beneficiaryBranch, TXN.VOUCHERNUMBER AS voucher, " +
            "TXN.TRANSACTIONSTATUS AS txnStatus, DEPT.NAME AS deptName " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERCUSTFNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE " +
            "TXN.ROUTINGTYPE = :routingType " +
            "AND TXN.REFERENCENUMBER like :reference " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.DEPARTMENT_ID DESC, TXN.TRANSACTIONSTATUS ", nativeQuery = true)
    List<RtgsTransactionReport> getCustomerTxnList(LocalDate fromDate, LocalDate toDate, String reference, String routingType);

    @Query(value = "SELECT * FROM TBL_INTERCUSTFNDTRANSFER " +
            "WHERE VERIFICATIONSTATUS = 2 " +
            "AND ROUTINGTYPE = 'Outgoing' AND CREATEDBY = :userId AND TO_CHAR(CREATEDAT, 'YYYY-MM-dd') = " +
            "(SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) ORDER BY ID ASC", nativeQuery = true)
    List<InterCustomerFundTransferEntity> getAllOutwardRejectedC2CTxn(Long userId);

    @Query(value = "SELECT * FROM (SELECT TXN.TRANSACTIONDATE AS txnDate, TXN.PAYER_ACC_NO AS debitorAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBank, TXN.BEN_ACC_NO AS creditorAccNo, TXN.BEN_NAME AS beneficiaryName, BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency, TXN.NARRATION AS narration, TXN.LCNUMBER AS lcNumber, TXN.ROUTINGTYPE AS routingType, " +
            "BRN.NAME AS payerBranch, BRNTWO.NAME AS beneficiaryBranch, TXN.VOUCHERNUMBER AS voucher, TXN.TRANSACTIONSTATUS AS txnStatus, DEPT.NAME AS deptName, PTXN.ENTRYUSER AS MAKER,  PTXN.APPROVER AS CHECKER " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERCUSTFNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE " +
            "CUR.SHORTCODE like :currency " +
            "AND TXN.ROUTINGTYPE = 'Outgoing' " +
            "AND TXN.TRANSACTIONSTATUS = :status " +
            "AND TXN.CBSNAME like :cbsName " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.PAYER_BRANCH_ID ASC ) WHERE payerBranch like :branch ", nativeQuery = true)
    List<BranchWiseTxnReport> getBranchWiseCustomerTxnReport(LocalDate fromDate, LocalDate toDate, String currency, String status, String cbsName, String branch);

    @Query(value = "SELECT TXN.TRANSACTIONDATE AS transactionDate, TXN.PAYER_ACC_NO AS payerAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBank, TXN.BEN_ACC_NO AS beneficiaryAccNo, TXN.BEN_NAME AS beneficiaryName, BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency, TXN.PARENTBATCHNUMBER AS batchNumber, TXN.RMT_REG_YEAR AS regYear, TXN.RMT_REG_NUM AS regNum, TXN.RMT_CUST_OFFICE_CODE AS custOfficeCode, TXN.RMT_DECLARANT_CODE AS declarantCode, " +
            "TXN.RMT_CUS_CELL_NO AS custCellNo, BRN.NAME AS payerBranch, BRNTWO.NAME AS beneficiaryBranch, TXN.CHARGE AS charge, TXN.VAT AS vat, TXN.VOUCHERNUMBER AS voucher, TXN.TRANSACTIONSTATUS AS txnStatus, DEPT.ID AS deptId, DEPT.NAME AS deptName " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERCUSTFNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE " +
            "TXN.DEPARTMENT_ID like :dept AND " +
            "CUR.SHORTCODE = 'BDT' " +
            "AND TXN.ROUTINGTYPE = 'Outgoing' " +
            "AND PTXN.TXNTYPECODE = '041' " +
            "AND TXN.CBSNAME like :cbsName " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY deptId ASC ", nativeQuery = true)
    List<CustomDutyReport> exportCustomDutyReport(LocalDate fromDate, LocalDate toDate, String dept, String cbsName);

    @Query(value = "SELECT * FROM (SELECT TXN.TRANSACTIONDATE AS txnDate, TXN.PAYER_ACC_NO AS debitorAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBank, TXN.BEN_ACC_NO AS creditorAccNo, TXN.BEN_NAME AS beneficiaryName, BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency, TXN.NARRATION AS narration, TXN.LCNUMBER AS lcNumber, TXN.ROUTINGTYPE AS routingType, " +
            "BRN.NAME AS payerBranch, BRNTWO.NAME AS beneficiaryBranch, TXN.VOUCHERNUMBER AS voucher, TXN.TRANSACTIONSTATUS AS txnStatus, DEPT.NAME AS deptName, PTXN.ENTRYUSER AS MAKER,  PTXN.APPROVER AS CHECKER " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERCUSTFNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE " +
            "CUR.SHORTCODE like :currency " +
            "AND TXN.ROUTINGTYPE = 'Outgoing' " +
            "AND TXN.TRANSACTIONSTATUS = :status " +
            "AND TXN.CBSNAME = :cbsName " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.PAYER_BRANCH_ID ASC ) WHERE payerBranch like :branch ", nativeQuery = true)
    List<BranchWiseTxnReport> exportBranchWiseReportForOutwardCustomerTxn(LocalDate fromDate, LocalDate toDate, String currency, String status, String cbsName, String branch);

    @Query(value = "SELECT * FROM (SELECT TXN.TRANSACTIONDATE AS txnDate, TXN.PAYER_ACC_NO AS debitorAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBank, TXN.BEN_ACC_NO AS creditorAccNo, TXN.BEN_NAME AS beneficiaryName, BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency, TXN.NARRATION AS narration, TXN.LCNUMBER AS lcNumber, TXN.ROUTINGTYPE AS routingType, " +
            "BRN.NAME AS payerBranch, BRNTWO.NAME AS beneficiaryBranch, TXN.VOUCHERNUMBER AS voucher, TXN.TRANSACTIONSTATUS AS txnStatus, DEPT.NAME AS deptName, PTXN.ENTRYUSER AS MAKER,  PTXN.APPROVER AS CHECKER " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERCUSTFNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE " +
            "CUR.SHORTCODE like :currency " +
            "AND TXN.ROUTINGTYPE = 'Incoming' " +
            "AND TXN.TRANSACTIONSTATUS = :status " +
            "AND TXN.CBSNAME = :cbsName " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.BEN_BRANCH_ID ASC ) WHERE beneficiaryBranch like :branch  ", nativeQuery = true)
    List<BranchWiseTxnReport> exportBranchWiseReportForInwardCustomerTxn(LocalDate fromDate, LocalDate toDate, String currency, String status, String cbsName, String branch);

    @Query(value = "SELECT T.id, T.parentBatchNumber, T.routingType, T.payerName, T.branchId,T.currency,T.entryUser,T.eventId,T.createdAt,T.referenceText,T.BatchChargeEnable, T.chargeAmount, T.vatAmount,T.eventName,T.fundTransferType,T.status, " +
            "SUM(T.amount) as amount " +
            "FROM (SELECT TXN.TRANSACTIONS AS id, TXN.PARENTBATCHNUMBER AS parentBatchNumber, TXN.ROUTINGTYPE AS routingType, TXN.AMOUNT AS amount, TXN.PAYER_NAME AS payerName, TXN.BEN_NAME AS benName, " +
            "TXN.PAYER_BRANCH_ID AS branchId, TXN.CREATEDAT AS createdAt, CUR.SHORTCODE AS currency, " +
            "CASE WHEN TXN.ROUTINGTYPE = 'Incoming' THEN 'SYSTEM' ELSE USR.USERNAME END entryUser, " +
            "CASE WHEN TXN.ROUTINGTYPE = 'Incoming' THEN 'IPACS008' ELSE 'OPACS008' END eventId, " +
            "CASE WHEN TXN.ROUTINGTYPE = 'Incoming' THEN 'Tr. Type : Customer To Customer  Fund Transfer Routing Type : Incoming' ELSE 'Tr. Type : Customer To Customer Fund Transfer Routing Type : Outgoing' END referenceText, " +
            "BATCHCHARGE AS BatchChargeEnable, " +
            "CHARGEAMOUNT AS chargeAmount, " +
            "VATAMOUNT AS vatAmount, " +
            "'Customer To Customer Fund Transfer' AS eventName, " +
            "'CustomerToCustomer' AS fundTransferType, " +
            "'Submitted' AS status " +
            "FROM TBL_INTERCUSTFNDTRANSFER ITXN " +
            "LEFT JOIN TBL_CUSTFNDTRANSFERTXN TXN ON TXN.TRANSACTIONS = ITXN.ID " +
            "LEFT JOIN TBL_USERINFO USR ON TXN.CREATEDBY = USR.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "WHERE ITXN.VERIFICATIONSTATUS IN (0,4,5) " +
            "AND ITXN.CREATEDBY != :userId AND ITXN.CREATEDBY != 0 " +
            "AND USR.BRANCH_ID = :branchId " +
            "AND USR.DEPT_ID = :deptId " +
            "AND IS_IB_TXN = 0 " +
            "AND TO_CHAR(TXN.CREATEDAT, 'YYYY-MM-dd') = " +
            "(SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL)) T GROUP BY T.id, T.parentBatchNumber, T.routingType,  T.payerName, T.branchId,T.currency,T.entryUser,T.eventId, T.createdAt, T.referenceText,T.BatchChargeEnable, " +
            "T.chargeAmount, T.vatAmount,T.eventName,T.fundTransferType,T.status ", nativeQuery = true)
    List<IApprovalEventResponse> getUnApprovedC2CTxns(Long userId, Long branchId, Long deptId);

    @Query(value = "SELECT T.id, T.parentBatchNumber, T.routingType, T.payerName, T.branchId,T.currency,T.entryUser,T.eventId,T.createdAt,T.referenceText,T.BatchChargeEnable, T.chargeAmount, T.vatAmount,T.eventName,T.fundTransferType,T.status, " +
            "SUM(T.amount) as amount " +
            "FROM (SELECT TXN.TRANSACTIONS AS id, TXN.PARENTBATCHNUMBER AS parentBatchNumber, TXN.ROUTINGTYPE AS routingType, TXN.AMOUNT AS amount, TXN.PAYER_NAME AS payerName, TXN.BEN_NAME AS benName, " +
            "TXN.PAYER_BRANCH_ID AS branchId, ITXN.CREATEDAT AS createdAt, CUR.SHORTCODE AS currency, " +
            "CASE WHEN TXN.ROUTINGTYPE = 'Incoming' THEN 'SYSTEM' ELSE USR.USERNAME END entryUser, " +
            "CASE WHEN TXN.ROUTINGTYPE = 'Incoming' THEN 'IPACS008' ELSE 'OPACS008' END eventId, " +
            "CASE WHEN TXN.ROUTINGTYPE = 'Incoming' THEN 'Tr. Type : Customer To Customer  Fund Transfer Routing Type : Incoming' ELSE 'Tr. Type : Customer To Customer Fund Transfer Routing Type : Outgoing' END referenceText, " +
            "ITXN.BATCHCHARGE AS BatchChargeEnable, " +
            "ITXN.CHARGEAMOUNT AS chargeAmount, " +
            "ITXN.VATAMOUNT AS vatAmount, " +
            "'Customer To Customer Fund Transfer' AS eventName, " +
            "'CustomerToCustomer' AS fundTransferType, " +
            "'Submitted' AS status " +
            "FROM TBL_INTERCUSTFNDTRANSFER ITXN " +
            "LEFT JOIN TBL_CUSTFNDTRANSFERTXN TXN ON TXN.TRANSACTIONS = ITXN.ID " +
            "LEFT JOIN TBL_USERINFO USR ON TXN.CREATEDBY = USR.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "WHERE ITXN.VERIFICATIONSTATUS IN (0,4,5) " +
            "AND ITXN.CREATEDBY != :userId AND ITXN.CREATEDBY != 0 " +
            "AND IS_IB_TXN = 0 " +
            "AND TO_CHAR(TXN.CREATEDAT, 'YYYY-MM-dd') = " +
            "(SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL)) T GROUP BY T.id, T.parentBatchNumber, T.routingType,  T.payerName, T.branchId,T.currency,T.entryUser,T.eventId, T.createdAt, T.referenceText,T.BatchChargeEnable, " +
            "T.chargeAmount, T.vatAmount,T.eventName,T.fundTransferType,T.status ", nativeQuery = true)
    List<IApprovalEventResponse> getUnApprovedC2CTxns(Long userId);

    @Query(value = "SELECT TXN.ID as id, TXN.FC_REC_BRANCH_ID AS fcRecBranchId, TXN.FCORGACCOUNTTYPE AS fcOrgAccountType, TXN.FCRECACCOUNTTYPE AS fcRecAccountType, TXN.LCNUMBER AS lcNumber, TXN.BILLNUMBER AS billNumber, PTXN.ENTRYUSER AS entryUser, PTXN.APPROVER AS approveBy, TXN.AMOUNT AS amount, TXN.BATCHTXN AS batchTxn, " +
            " TXN.BEN_ACC_NO AS benAccNo, TXN.BEN_BANK_ID AS benBankId, BNKTWO.NAME AS benBankName, BRNTWO.NAME AS benBranchName, TXN.BEN_BRANCH_ID AS benBranchId,TXN.BEN_NAME AS benName, TXN.PAYER_BANK_ID AS payerBankId, BNK.NAME AS payerBankName, " +
            "TXN.PAYER_BRANCH_ID AS payerBranchId, " +
            "BRN.NAME AS payerBranchName, TXN.REFERENCENUMBER AS referenceNumber,TXN.ROUTINGTYPE AS routingType, TXN.TRANSACTIONSTATUS AS transactionStatus, TXN.TXNGLACCOUNT AS txnGlAccount, TXN.DEPARTMENT_ID AS departmentId,DEPT.NAME AS departmentName, " +
            "TXN.DEPARTMENTACCOUNT_ID AS departmentAccountId, TXN.BATCHTXNCHARGEWAIVED AS batchTxnChargeWaived, TXN.VATGL AS vatGl, TXN.CHARGEGL AS chargeGl, TXN.VAT AS vat, TXN.CHARGE AS charge, TXN.CBSNAME AS cbsName,TXN.NARRATION AS narration, " +
            "TXN.FAILEDREASON AS failedReason, TXN.RMT_CUS_CELL_NO AS rmtCusCellNo,TXN.RMT_DECLARANT_CODE AS rmtDeclareCode, TXN.RMT_CUST_OFFICE_CODE AS rmtCustOfficeCode, TXN.RMT_REG_YEAR AS rmtRegYear,  TXN.RMT_REG_NUM AS rmtRegNum, TXN.TRANSACTIONDATE AS transactionDate, TXN.PAYER_ACC_NO AS payerAccNo, TXN.PAYER_NAME AS payerName, " +
            "BNKTWO.BIC AS benBankBic, BRNTWO.ROUTINGNUMBER AS benBranchRouting, CUR.SHORTCODE AS currency, TXN.PARENTBATCHNUMBER AS parentBatchNumber,TXN.RETURNREASON AS returnReason, TXN.VERIFICATIONSTATUS AS status, TXN.FCRECACCOUNTTYPE AS benAccType, TXN.FCORGACCOUNTTYPE AS payerAccType " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERCUSTFNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE CUR.SHORTCODE like :currency " +
            "AND DEPT.NAME like :dept " +
            "AND BNKTWO.BIC like :bank " +
            "AND TXN.ROUTINGTYPE = 'Outgoing' " +
            "AND TXN.TRANSACTIONSTATUS like :transactionStatus " +
            "AND TXN.TRANSACTIONSTATUS <> 'Submitted' " +
            "AND TXN.PAYER_ACC_NO like :payerAccount " +
            "AND TXN.BEN_ACC_NO like :benAccount " +
            "AND TXN.PARENTBATCHNUMBER like :batchNumber " +
            "AND NVL(TXN.VOUCHERNUMBER, ' ')  like :voucher " +
            "AND TXN.REFERENCENUMBER like :reference " +
            "AND TRUNC(TXN.CREATEDAT) >= :fromDate " +
            "AND TRUNC(TXN.CREATEDAT) <= :toDate " +
            "ORDER BY TXN.CREATEDAT DESC ", nativeQuery = true)
    List<C2CTxnTransactionResponse> getOutwardCustomerTxn(LocalDate fromDate, LocalDate toDate, String bank, String currency, String dept, String transactionStatus, String voucher, String reference, String batchNumber, String payerAccount, String benAccount);


    @Query(value = "SELECT TXN.ID as id, TXN.FC_REC_BRANCH_ID AS fcRecBranchId, TXN.FCORGACCOUNTTYPE AS fcOrgAccountType, TXN.FCRECACCOUNTTYPE AS fcRecAccountType, TXN.LCNUMBER AS lcNumber, TXN.BILLNUMBER AS billNumber, PTXN.ENTRYUSER AS entryUser, PTXN.APPROVER AS approveBy, TXN.AMOUNT AS amount, TXN.BATCHTXN AS batchTxn, " +
            " TXN.BEN_ACC_NO AS benAccNo, TXN.BEN_BANK_ID AS benBankId, BNKTWO.NAME AS benBankName, BRNTWO.NAME AS benBranchName, TXN.BEN_BRANCH_ID AS benBranchId,TXN.BEN_NAME AS benName, TXN.PAYER_BANK_ID AS payerBankId, BNK.NAME AS payerBankName, " +
            "TXN.PAYER_BRANCH_ID AS payerBranchId, " +
            "BRN.NAME AS payerBranchName, TXN.REFERENCENUMBER AS referenceNumber,TXN.ROUTINGTYPE AS routingType, TXN.TRANSACTIONSTATUS AS transactionStatus, TXN.TXNGLACCOUNT AS txnGlAccount, TXN.DEPARTMENT_ID AS departmentId,DEPT.NAME AS departmentName, " +
            "TXN.DEPARTMENTACCOUNT_ID AS departmentAccountId, TXN.BATCHTXNCHARGEWAIVED AS batchTxnChargeWaived, TXN.VATGL AS vatGl, TXN.CHARGEGL AS chargeGl, TXN.VAT AS vat, TXN.CHARGE AS charge, TXN.CBSNAME AS cbsName,TXN.NARRATION AS narration, " +
            "TXN.FAILEDREASON AS failedReason, TXN.RMT_CUS_CELL_NO AS rmtCusCellNo,TXN.RMT_DECLARANT_CODE AS rmtDeclareCode, TXN.RMT_CUST_OFFICE_CODE AS rmtCustOfficeCode, TXN.RMT_REG_YEAR AS rmtRegYear,  TXN.RMT_REG_NUM AS rmtRegNum, TXN.TRANSACTIONDATE AS transactionDate, TXN.PAYER_ACC_NO AS payerAccNo, TXN.PAYER_NAME AS payerName, " +
            "BNKTWO.BIC AS benBankBic, BRNTWO.ROUTINGNUMBER AS benBranchRouting, CUR.SHORTCODE AS currency, TXN.PARENTBATCHNUMBER AS parentBatchNumber,TXN.RETURNREASON AS returnReason, TXN.VERIFICATIONSTATUS AS status, TXN.FCRECACCOUNTTYPE AS benAccType, TXN.FCORGACCOUNTTYPE AS payerAccType " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERCUSTFNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE CUR.SHORTCODE like :currency " +
            "AND BNK.BIC like :bank " +
            "AND TXN.ROUTINGTYPE = 'Incoming' " +
            "AND TXN.TRANSACTIONSTATUS like :transactionStatus " +
            "AND TXN.TRANSACTIONSTATUS <> 'Submitted' " +
            "AND TXN.PAYER_ACC_NO like :payerAccount " +
            "AND TXN.BEN_ACC_NO like :benAccount " +
            "AND TXN.PARENTBATCHNUMBER like :batchNumber " +
            "AND NVL(TXN.VOUCHERNUMBER, ' ') like :voucher " +
            "AND TXN.REFERENCENUMBER like :reference " +
            "AND TRUNC(TXN.CREATEDAT) >= :fromDate " +
            "AND TRUNC(TXN.CREATEDAT) <= :toDate " +
            "ORDER BY TXN.CREATEDAT DESC ", nativeQuery = true)
    List<C2CTxnTransactionResponse> getInwardCustomerTxn(LocalDate fromDate, LocalDate toDate, String bank, String currency, String transactionStatus, String voucher, String reference, String batchNumber, String payerAccount, String benAccount);

}