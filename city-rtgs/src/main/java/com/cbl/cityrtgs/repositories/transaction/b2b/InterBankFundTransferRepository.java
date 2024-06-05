package com.cbl.cityrtgs.repositories.transaction.b2b;

import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.report.*;
import com.cbl.cityrtgs.models.dto.transaction.IApprovalEventResponse;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.InterBankTransferEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface InterBankFundTransferRepository extends JpaRepository<InterBankTransferEntity, Long> {
    Page<InterBankTransferEntity> findAllByIsDeletedFalse(Pageable pageable);
    Optional<InterBankTransferEntity> findByIdAndIsDeletedFalse(Long id);
    List<InterBankTransferEntity> findAllByIsDeletedFalse();
    Optional<InterBankTransferEntity> findByBatchNumberAndIsDeletedFalse(String batchNumber);
    Optional<InterBankTransferEntity> findByBatchNumberAndRoutingTypeAndIsDeletedFalse(String batchNumber, RoutingType routingType);
    @Query(value = "SELECT * FROM TBL_INTERBANKFUNDTRANSFER " +
            "WHERE VERIFICATIONSTATUS IN (4,7) " +
            "AND ROUTINGTYPE = 'Incoming' AND TO_CHAR(CREATEDATE, 'YYYY-MM-dd') = " +
            "(SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) ORDER BY ID ASC", nativeQuery = true)
    List<InterBankTransferEntity> findAllByVerificationStatusInAndRoutingTypeAndIsDeletedFalse();
    @Query(value = "SELECT * FROM TBL_INTERBANKFUNDTRANSFER " +
            "WHERE VERIFICATIONSTATUS = 2 " +
            "AND ROUTINGTYPE = 'Incoming' AND TO_CHAR(CREATEDAT, 'YYYY-MM-dd') = " +
            "(SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) ORDER BY ID ASC", nativeQuery = true)
    List<InterBankTransferEntity> findAllByVerificationStatusAndRoutingType();

    @Query(value = "SELECT * FROM TBL_INTERBANKFUNDTRANSFER " +
            "WHERE VERIFICATIONSTATUS = 0 " +
            "AND ROUTINGTYPE = 'Outgoing' AND TO_CHAR(CREATEDAT, 'YYYY-MM-dd') = " +
            "(SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) ORDER BY ID ASC", nativeQuery = true)
    List<InterBankTransferEntity> getAllPendingTxn();
    Boolean existsByBatchNumber(String batchNum);

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
            "FROM TBL_BANKFNDTRANSFERTXN TXN, RTGS_BRANCHS BR " +
            "WHERE TXN.BEN_BRANCH_ID = BR.ID " +
            "AND TXN.ROUTINGTYPE = 'Incoming' " +
            "AND TXN.CURRENCY_ID =:currency " +
            "AND TRUNC(TRANSACTIONDATE) =:txnDate " +
            "UNION ALL " +
            "SELECT BR.NAME BRANCH_NAME, TRUNC(TXN.TRANSACTIONDATE) TRANSACTIONDATE, TXN.ROUTINGTYPE, TXN.TRANSACTIONSTATUS, TXN.AMOUNT " +
            "FROM TBL_BANKFNDTRANSFERTXN TXN, RTGS_BRANCHS BR " +
            "WHERE TXN.PAYER_BRANCH_ID = BR.ID " +
            "AND TXN.ROUTINGTYPE = 'Outgoing' " +
            "AND TXN.CURRENCY_ID =:currency " +
            "AND TRUNC(TRANSACTIONDATE) =:txnDate) TXN " +
            "GROUP BY TXN.TRANSACTIONDATE, TXN.BRANCH_NAME ", nativeQuery = true)
    List<TxnSummaryReport> getB2BFundTransferSummaryReport(LocalDate txnDate, String currency);

    @Query(value = "SELECT TXN.TRANSACTIONDATE AS txnDate, TXN.PAYER_ACC_NO AS payerAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBank, TXN.BEN_ACC_NO AS beneficiaryAccNo, TXN.BEN_NAME AS beneficiaryName, BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency, TXN.NARRATION AS narration, TXN.LCNUMBER AS lcNumber, TXN.ROUTINGTYPE AS routingType,  " +
            "BRN.NAME AS payerBranch, BRNTWO.NAME AS beneficiaryBranch, TXN.VOUCHERNUMBER AS voucher, TXN.TRANSACTIONSTATUS AS txnStatus, DEPT.NAME AS deptName " +
            "FROM TBL_BANKFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERBANKFUNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE CUR.SHORTCODE like :currency " +
            "AND DEPT.NAME like :dept " +
            "AND BNKTWO.NAME like :bank " +
            "AND TXN.ROUTINGTYPE = 'Outgoing' " +
            "AND TXN.TRANSACTIONSTATUS = :status " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.DEPARTMENT_ID DESC, TXN.TRANSACTIONSTATUS ", nativeQuery = true)
    List<RtgsTransactionReport> getReportForOutwardBankTxn(LocalDate fromDate, LocalDate toDate, String bank, String currency, String status, String dept);

    @Query(value = "SELECT TXN.TRANSACTIONDATE AS txnDate, TXN.PAYER_ACC_NO AS payerAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBank, TXN.BEN_ACC_NO AS beneficiaryAccNo, TXN.BEN_NAME AS beneficiaryName, BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency, TXN.NARRATION AS narration, TXN.LCNUMBER AS lcNumber, TXN.ROUTINGTYPE AS routingType, " +
            "BRN.NAME AS payerBranch, BRNTWO.NAME AS beneficiaryBranch, TXN.VOUCHERNUMBER AS voucher, TXN.TRANSACTIONSTATUS AS txnStatus, DEPT.NAME AS deptName " +
            "FROM TBL_BANKFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERBANKFUNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE CUR.SHORTCODE like :currency " +
            "AND DEPT.NAME like :dept " +
            "AND BNK.NAME like :bank " +
            "AND TXN.ROUTINGTYPE = 'Incoming' " +
            "AND TXN.TRANSACTIONSTATUS = :status " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.DEPARTMENT_ID DESC, TXN.TRANSACTIONSTATUS ", nativeQuery = true)
    List<RtgsTransactionReport> getReportForInwardBankTxn(LocalDate fromDate, LocalDate toDate, String bank, String currency, String status, String dept);
    @Query(value = "SELECT COUNT(T.ID) AS txnNumber, T.payerBranch AS branch " +
            "FROM " +
            "(SELECT TXN.ID as id, TXN.TRANSACTIONDATE AS txnDate, TXN.PAYER_ACC_NO AS debitorAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBank, TXN.BEN_ACC_NO AS creditorAccNo, TXN.BEN_NAME AS beneficiaryName, BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency, TXN.NARRATION AS narration, TXN.LCNUMBER AS lcNumber, TXN.ROUTINGTYPE AS routingType, " +
            "BRN.NAME AS payerBranch, BRNTWO.NAME AS beneficiaryBranch, TXN.VOUCHERNUMBER AS voucher, TXN.TRANSACTIONSTATUS AS txnStatus, DEPT.NAME AS deptName " +
            "FROM TBL_BANKFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERBANKFUNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE CUR.SHORTCODE like :currency " +
            "AND TXN.ROUTINGTYPE = 'Outgoing' " +
            "AND TXN.TRANSACTIONSTATUS = :status " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY BRN.ID ) T GROUP BY T.payerBranch ", nativeQuery = true)
    List<BranchList> getPayerBranchList(LocalDate fromDate, LocalDate toDate, String currency, String status);

    @Query(value = "SELECT COUNT(T.ID) AS txnNumber, T.beneficiaryBranch AS branch " +
            "FROM " +
            "(SELECT TXN.ID as id, TXN.TRANSACTIONDATE AS txnDate, TXN.PAYER_ACC_NO AS debitorAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBank, TXN.BEN_ACC_NO AS creditorAccNo, TXN.BEN_NAME AS beneficiaryName, BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency, TXN.NARRATION AS narration, TXN.LCNUMBER AS lcNumber, TXN.ROUTINGTYPE AS routingType, " +
            "BRN.NAME AS payerBranch, BRNTWO.NAME AS beneficiaryBranch, TXN.VOUCHERNUMBER AS voucher, TXN.TRANSACTIONSTATUS AS txnStatus, DEPT.NAME AS deptName " +
            "FROM TBL_BANKFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERBANKFUNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE CUR.SHORTCODE like :currency " +
            "AND TXN.ROUTINGTYPE = 'Incoming' " +
            "AND TXN.TRANSACTIONSTATUS = :status " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY BRNTWO.ID ) T GROUP BY T.beneficiaryBranch ", nativeQuery = true)
    List<BranchList> getBeneficiaryBranchList(LocalDate fromDate, LocalDate toDate, String currency, String status);

    @Query(value = "SELECT * FROM (SELECT TXN.TRANSACTIONDATE AS txnDate, TXN.PAYER_ACC_NO AS debitorAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBank, TXN.BEN_ACC_NO AS creditorAccNo, TXN.BEN_NAME AS beneficiaryName, BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency, TXN.NARRATION AS narration, TXN.LCNUMBER AS lcNumber, TXN.ROUTINGTYPE AS routingType, " +
            "BRN.NAME AS payerBranch, BRNTWO.NAME AS beneficiaryBranch, TXN.VOUCHERNUMBER AS voucher, TXN.TRANSACTIONSTATUS AS txnStatus, DEPT.NAME AS deptName, PTXN.ENTRYUSER AS MAKER,  PTXN.APPROVER AS CHECKER " +
            "FROM TBL_BANKFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERBANKFUNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE CUR.SHORTCODE like :currency " +
            "AND TXN.ROUTINGTYPE = 'Outgoing' " +
            "AND TXN.TRANSACTIONSTATUS = :status " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.PAYER_BRANCH_ID ASC ) WHERE payerBranch like :branch ", nativeQuery = true)
    List<BranchWiseTxnReport> getBranchWiseReportForOutwardBankTxn(LocalDate fromDate, LocalDate toDate, String currency, String status, String branch);

    @Query(value = "SELECT * FROM (SELECT TXN.TRANSACTIONDATE AS txnDate, TXN.PAYER_ACC_NO AS debitorAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBank, TXN.BEN_ACC_NO AS creditorAccNo, TXN.BEN_NAME AS beneficiaryName, BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency, TXN.NARRATION AS narration, TXN.LCNUMBER AS lcNumber, TXN.ROUTINGTYPE AS routingType, " +
            "BRN.NAME AS payerBranch, BRNTWO.NAME AS beneficiaryBranch, TXN.VOUCHERNUMBER AS voucher, TXN.TRANSACTIONSTATUS AS txnStatus, DEPT.NAME AS deptName, PTXN.ENTRYUSER AS MAKER,  PTXN.APPROVER AS CHECKER " +
            "FROM TBL_BANKFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERBANKFUNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE CUR.SHORTCODE like :currency " +
            "AND TXN.ROUTINGTYPE = 'Incoming' " +
            "AND TXN.TRANSACTIONSTATUS = :status " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.BEN_BRANCH_ID ASC ) WHERE beneficiaryBranch like :branch ", nativeQuery = true)
    List<BranchWiseTxnReport> getBranchWiseReportForInwardBankTxn(LocalDate fromDate, LocalDate toDate, String currency, String status, String branch);

    @Query(value = "SELECT COUNT(T.ID) AS txnNumber, T.beneficiaryBank AS bank " +
            "FROM " +
            "(SELECT TXN.ID as id, TXN.TRANSACTIONDATE AS txnDate, TXN.PAYER_ACC_NO AS debitorAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBank, TXN.BEN_ACC_NO AS creditorAccNo, TXN.BEN_NAME AS beneficiaryName, BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency, TXN.NARRATION AS narration, TXN.LCNUMBER AS lcNumber, TXN.ROUTINGTYPE AS routingType, " +
            "BRN.NAME AS payerBranch, BRNTWO.NAME AS beneficiaryBranch, TXN.VOUCHERNUMBER AS voucher, TXN.TRANSACTIONSTATUS AS txnStatus, DEPT.NAME AS deptName " +
            "FROM TBL_BANKFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERBANKFUNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE CUR.SHORTCODE like :currency " +
            "AND TXN.ROUTINGTYPE = 'Outgoing' " +
            "AND DEPT.NAME like :dept " +
            "AND TXN.TRANSACTIONSTATUS = :status " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.BEN_BANK_ID ) T GROUP BY T.beneficiaryBank ", nativeQuery = true)
    List<BankList> getBeneficiaryBankList(LocalDate fromDate, LocalDate toDate, String currency, String status, String dept);

    @Query(value = "SELECT COUNT(T.ID) AS txnNumber, T.payerBank AS bank " +
            "FROM " +
            "(SELECT TXN.ID as id, TXN.TRANSACTIONDATE AS txnDate, TXN.PAYER_ACC_NO AS debitorAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBank, TXN.BEN_ACC_NO AS creditorAccNo, TXN.BEN_NAME AS beneficiaryName, BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency, TXN.NARRATION AS narration, TXN.LCNUMBER AS lcNumber, TXN.ROUTINGTYPE AS routingType, " +
            "BRN.NAME AS payerBranch, BRNTWO.NAME AS beneficiaryBranch, TXN.VOUCHERNUMBER AS voucher, TXN.TRANSACTIONSTATUS AS txnStatus, DEPT.NAME AS deptName " +
            "FROM TBL_BANKFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERBANKFUNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE CUR.SHORTCODE like :currency " +
            "AND DEPT.NAME like :dept " +
            "AND TXN.ROUTINGTYPE = 'Incoming' " +
            "AND TXN.TRANSACTIONSTATUS = :status " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.PAYER_BANK_ID ) T GROUP BY T.payerBank ", nativeQuery = true)
    List<BankList> getPayerBankList(LocalDate fromDate, LocalDate toDate, String currency, String status, String dept);

    @Query(value = "SELECT TXN.TRANSACTIONDATE AS txnDate, TXN.PAYER_ACC_NO AS payerAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBank, TXN.BEN_ACC_NO AS beneficiaryAccNo, TXN.BEN_NAME AS beneficiaryName, BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency, TXN.NARRATION AS narration, TXN.LCNUMBER AS lcNumber, TXN.ROUTINGTYPE AS routingType, " +
            "BRN.NAME AS payerBranch, BRNTWO.NAME AS beneficiaryBranch, TXN.VOUCHERNUMBER AS voucher, TXN.TRANSACTIONSTATUS AS txnStatus, DEPT.NAME AS deptName " +
            "FROM TBL_BANKFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERBANKFUNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE TXN.ROUTINGTYPE = :routingType " +
            "AND TXN.REFERENCENUMBER like :reference " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.DEPARTMENT_ID DESC, TXN.TRANSACTIONSTATUS ", nativeQuery = true)
    List<RtgsTransactionReport> getBankTxnList(LocalDate fromDate, LocalDate toDate, String reference, String routingType);

    @Query(value = "SELECT * FROM TBL_INTERBANKFUNDTRANSFER " +
            "WHERE VERIFICATIONSTATUS = 2 " +
            "AND ROUTINGTYPE = 'Outgoing' AND CREATEDBY = :userId AND TO_CHAR(CREATEDAT, 'YYYY-MM-dd') = " +
            "TO_CHAR(sysdate, 'YYYY-MM-dd') ORDER BY ID ASC ", nativeQuery = true)
    List<InterBankTransferEntity> getAllOutwardRejectedB2BTxns(Long userId);

    @Query(value = "SELECT TXN.TRANSACTIONS AS id, TXN.PARENTBATCHNUMBER AS parentBatchNumber, TXN.ROUTINGTYPE AS routingType, TXN.AMOUNT AS amount, TXN.PAYER_NAME AS payerName, TXN.BEN_NAME AS benName, " +
            "TXN.PAYER_BRANCH_ID AS branchId, TXN.CREATEDAT AS createdAt, CUR.SHORTCODE AS currency, " +
            "CASE WHEN TXN.ROUTINGTYPE = 'Incoming' THEN 'SYSTEM' ELSE USR.USERNAME END entryUser, " +
            "CASE WHEN TXN.ROUTINGTYPE = 'Incoming' THEN 'IPACS009' ELSE 'OPACS009' END eventId, " +
            "CASE WHEN TXN.ROUTINGTYPE = 'Incoming' THEN 'Tr. Type : Bank To Bank Fund Transfer Routing Type : Incoming' ELSE 'Tr. Type : Bank To Bank Fund Transfer Routing Type : Outgoing' END referenceText, " +
            "'0' AS BatchChargeEnable, " +
            "'0.00' AS chargeAmount, " +
            "'0.00' AS vatAmount, " +
            "'Bank To Bank Fund Transfer' AS eventName, " +
            "'BankToBank' AS fundTransferType, " +
            "'Submitted' AS status " +
            "FROM TBL_INTERBANKFUNDTRANSFER ITXN " +
            "LEFT JOIN TBL_BANKFNDTRANSFERTXN TXN ON TXN.TRANSACTIONS = ITXN.ID " +
            "LEFT JOIN TBL_USERINFO USR ON TXN.CREATEDBY = USR.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "WHERE ITXN.VERIFICATIONSTATUS IN (0,4,5) " +
            "AND ITXN.CREATEDBY != :userId  AND ITXN.CREATEDBY != 0 " +
            "AND USR.BRANCH_ID = :branchId " +
            "AND USR.DEPT_ID = :deptId " +
            "AND TO_CHAR(TXN.CREATEDAT, 'YYYY-MM-dd') = " +
            "(SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) ", nativeQuery = true)
    List<IApprovalEventResponse> getUnApprovedB2BTxns(Long userId, Long branchId, Long deptId);

    @Query(value = "SELECT TXN.TRANSACTIONS AS id, TXN.PARENTBATCHNUMBER AS parentBatchNumber, TXN.ROUTINGTYPE AS routingType, TXN.AMOUNT AS amount, TXN.PAYER_NAME AS payerName, TXN.BEN_NAME AS benName, " +
            "TXN.PAYER_BRANCH_ID AS branchId, TXN.CREATEDAT AS createdAt, CUR.SHORTCODE AS currency, " +
            "CASE WHEN TXN.ROUTINGTYPE = 'Incoming' THEN 'SYSTEM' ELSE USR.USERNAME END entryUser, " +
            "CASE WHEN TXN.ROUTINGTYPE = 'Incoming' THEN 'IPACS009' ELSE 'OPACS009' END eventId, " +
            "CASE WHEN TXN.ROUTINGTYPE = 'Incoming' THEN 'Tr. Type : Bank To Bank Fund Transfer Routing Type : Incoming' ELSE 'Tr. Type : Bank To Bank Fund Transfer Routing Type : Outgoing' END referenceText, " +
            "'0' AS BatchChargeEnable, " +
            "'0.00' AS chargeAmount, " +
            "'0.00' AS vatAmount, " +
            "'Bank To Bank Fund Transfer' AS eventName, " +
            "'BankToBank' AS fundTransferType, " +
            "'Submitted' AS status " +
            "FROM TBL_INTERBANKFUNDTRANSFER ITXN " +
            "LEFT JOIN TBL_BANKFNDTRANSFERTXN TXN ON TXN.TRANSACTIONS = ITXN.ID " +
            "LEFT JOIN TBL_USERINFO USR ON TXN.CREATEDBY = USR.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "WHERE ITXN.VERIFICATIONSTATUS IN (0,4,5) " +
            "AND ITXN.CREATEDBY != :userId  AND ITXN.CREATEDBY != 0 " +
            "AND TO_CHAR(TXN.CREATEDAT, 'YYYY-MM-dd') = " +
            "(SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) ", nativeQuery = true)
    List<IApprovalEventResponse> getUnApprovedB2BTxns(Long userId);
}

