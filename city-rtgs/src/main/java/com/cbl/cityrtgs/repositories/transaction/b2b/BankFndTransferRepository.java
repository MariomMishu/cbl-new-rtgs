package com.cbl.cityrtgs.repositories.transaction.b2b;

import com.cbl.cityrtgs.models.dto.dashboard.DashboardB2BInwardTransaction;
import com.cbl.cityrtgs.models.dto.dashboard.DashboardB2BOutwardTransaction;
import com.cbl.cityrtgs.models.dto.projection.BankFundTransferOutwardProjection;
import com.cbl.cityrtgs.models.dto.projection.BankFundTransferProjection;
import com.cbl.cityrtgs.models.dto.transaction.PendingOutwardTransactions;
import com.cbl.cityrtgs.models.dto.transaction.b2b.B2BProjection;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.BankFndTransferEntity;
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
public interface BankFndTransferRepository extends JpaRepository<BankFndTransferEntity, Long>, JpaSpecificationExecutor<BankFndTransferEntity> {
    Boolean existsByReferenceNumber(String referenceNumber);

    Page<BankFndTransferEntity> findAllByIsDeletedFalse(Pageable pageable);

    Optional<BankFndTransferEntity> findByIdAndIsDeletedFalse(Long id);

    List<BankFndTransferEntity> findAllByIsDeletedFalse();

    Optional<BankFndTransferEntity> findByReferenceNumberAndIsDeletedFalse(String ref);

    Optional<BankFndTransferEntity> findByTransactionsAndIsDeletedFalse(Long transaction);

    Optional<BankFndTransferEntity> findByTransactionsAndParentBatchNumberAndIsDeletedFalse(Long transactions, String parentBatchNumber);

    @Query(value = "SELECT * FROM TBL_BANKFNDTRANSFERTXN " +
            "WHERE TRANSACTIONS IN :transactions " +
            "AND ROUTINGTYPE = 'Outgoing' " +
            "AND TO_CHAR(CREATEDAT, 'YYYY-MM-dd') = (SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) " +
            "ORDER BY ID ASC", nativeQuery = true)
    List<BankFndTransferEntity> getAllPendingTxn(List<Long> transactions);

    @Query(value = "SELECT Q.ID bankId,Q.NAME name, SUM(P.AMOUNT) amount FROM TBL_BANKFNDTRANSFERTXN P " +
            "LEFT JOIN RTGS_BANKS Q ON P.PAYER_BANK_ID = Q.ID " +
            "WHERE TRUNC(P.CREATEDAT) = :transactionDate " +
            "AND P.ROUTINGTYPE = 'Incoming' " +
            "AND P.CURRENCY_ID = (SELECT ID FROM TBL_RTGS_CURRENCIES WHERE SHORTCODE = :shortCode) GROUP BY Q.NAME, Q.ID", nativeQuery = true)
    List<B2BProjection> findAllInwardB2BTransactions(LocalDate transactionDate, String shortCode);

    @Query(value = "SELECT TXN.TRANSACTIONDATE AS time, " +
            "BNK.NAME AS payerBank, " +
            "TXN.BEN_NAME AS beneficiaryName, " +
            "BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, " +
            "TXN.REFERENCENUMBER AS referenceNo, " +
            "CUR.SHORTCODE AS currency, " +
            "TXN.NARRATION AS narration, " +
            "TXN.LCNUMBER AS lcNumber, " +
            "BRN.NAME AS payerBranch, " +
            "TXN.BILLNUMBER AS billNumber, " +
            "BRNTWO.NAME AS beneficiaryBranch, " +
            "TXN.FCRECACCOUNTTYPE AS partyName, " +
            "TXN.VOUCHERNUMBER AS voucher, " +
            "TXN.TRANSACTIONSTATUS AS status " +
            "FROM TBL_BANKFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERBANKFUNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "WHERE CUR.SHORTCODE LIKE :shortCode " +
            "AND TXN.ROUTINGTYPE = 'Incoming' " +
            "AND TRUNC(TXN.TRANSACTIONDATE) = :transactionDate " +
            "ORDER BY TXN.TRANSACTIONSTATUS", nativeQuery = true)
    List<DashboardB2BInwardTransaction> findAllInwardB2BTransactionDetails(LocalDate transactionDate, String shortCode);


    @Query(value = "SELECT TXN.TRANSACTIONDATE          AS transactionDate," +
            " BNK.NAME                     AS payerBankName," +
            " TXN.BEN_NAME                 AS beneficiaryName," +
            " BNKTWO.NAME                  AS benBankName," +
            " TXN.AMOUNT                   AS amount," +
            " TXN.BILLNUMBER               AS billNo," +
            " TXN.PAYER_ACC_NO             AS fromAccountNumber," +
            " TXN.REFERENCENUMBER          AS referenceNumber," +
            " TXN.PARENTBATCHNUMBER        AS parentBatchNumber," +
            " CUR.SHORTCODE                AS currency," +
            " TXN.NARRATION                AS narration," +
            " TXN.LCNUMBER                 AS lcNumber," +
            " BRN.NAME                     AS payerBranchName," +
            " TXN.BILLNUMBER               AS billNumber," +
            " BRNTHREE.NAME                 AS benBranchName," +
            " BRNTWO.NAME                  AS fcRecBranchName," +
            " TXN.FCRECACCOUNTTYPE         AS partyName," +
            " TRIM (TXN.VOUCHERNUMBER)     AS voucherNumber," +
            " TXN.TRANSACTIONSTATUS        AS status," +
            " TXN.RETURNREASON             AS returnReason," +
            " UI.FULLNAME                  AS entryBy," +
            " PTXN.APPROVER                AS approveBy" +
            " FROM TBL_BANKFNDTRANSFERTXN TXN " +
            " LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID" +
            " LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID" +
            " LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID" +
            " LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID" +
            " LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID" +
            " LEFT JOIN RTGS_BRANCHS BRNTHREE ON TXN.FC_REC_BRANCH_ID = BRNTHREE.ID" +
            " LEFT JOIN TBL_USERINFO UI ON TXN.CREATEDBY = UI.ID" +
            " LEFT JOIN TBL_INTERBANKFUNDTRANSFER PTXN" +
            " ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER" +
            " WHERE TXN.ROUTINGTYPE = :routingType " +
            " AND CUR.SHORTCODE LIKE :shortCode" +
            " AND TXN.PARENTBATCHNUMBER LIKE :batchNo" +
            " AND TXN.REFERENCENUMBER LIKE :refNo" +
            " AND NVL(TXN.VOUCHERNUMBER, ' ')  LIKE :voucherNo" +
            " AND TXN.TRANSACTIONSTATUS LIKE :trxStatus" +
            " AND TXN.TRANSACTIONSTATUS <> 'Submitted'" +
            " AND BNK.BIC LIKE :bankName" +
            " AND TRUNC (TXN.CREATEDAT) BETWEEN :fromDate AND :toDate" +
            " ORDER BY TXN.CREATEDAT DESC", nativeQuery = true)
    List<BankFundTransferProjection> getAllB2BInward(LocalDate fromDate,
                                                     LocalDate toDate,
                                                     String routingType,
                                                     String shortCode,
                                                     String batchNo,
                                                     String refNo,
                                                     String voucherNo,
                                                     String trxStatus,
                                                     String bankName);

    @Query(value = "SELECT TXN.TRANSACTIONDATE AS createdAt," +
            " BNK.NAME                     AS payerBankName," +
            " TXN.BEN_NAME                 AS beneficiaryName," +
            " BNKTWO.NAME                  AS benBankName," +
            " TXN.AMOUNT                   AS amount," +
            " TXN.BILLNUMBER               AS billNo," +
            " TXN.PAYER_ACC_NO             AS fromAccountNumber," +
            " TXN.REFERENCENUMBER          AS referenceNumber," +
            " TXN.PARENTBATCHNUMBER        AS parentBatchNumber," +
            " CUR.SHORTCODE                AS currency," +
            " TXN.NARRATION                AS narration," +
            " TXN.LCNUMBER                 AS lcNumber," +
            " BRN.NAME                     AS payerBranchName," +
            " TXN.BILLNUMBER               AS billNumber," +
            " BRNTHREE.NAME                AS benBranchName," +
            " BRNTWO.NAME                  AS beneficiaryReceiverBranch," +
            " TXN.FCRECACCOUNTTYPE         AS partyName," +
            " TRIM (TXN.VOUCHERNUMBER)     AS voucherNumber," +
            " TXN.TRANSACTIONSTATUS        AS status," +
            " TXN.RETURNREASON             AS returnReason," +
            " UI.FULLNAME                  AS entryUser," +
            " PTXN.APPROVER                AS approveBy" +
            " FROM TBL_BANKFNDTRANSFERTXN TXN " +
            " LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID" +
            " LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID" +
            " LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID" +
            " LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID" +
            " LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID" +
            " LEFT JOIN TBL_USERINFO UI ON TXN.CREATEDBY = UI.ID" +
            " LEFT JOIN RTGS_BRANCHS BRNTHREE ON TXN.FC_REC_BRANCH_ID = BRNTHREE.ID" +
            " LEFT JOIN TBL_DEPARTMENT DEP ON TXN.DEPARTMENT_ID = DEP.ID" +
            " LEFT JOIN TBL_INTERBANKFUNDTRANSFER PTXN" +
            " ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER" +
            " WHERE TXN.ROUTINGTYPE = :routingType " +
            " AND CUR.SHORTCODE LIKE :shortCode" +
            " AND TXN.PARENTBATCHNUMBER LIKE :batchNo" +
            " AND TXN.REFERENCENUMBER LIKE :refNo" +
            " AND NVL(TXN.VOUCHERNUMBER, ' ')  LIKE :voucherNo" +
            " AND TXN.TRANSACTIONSTATUS LIKE :trxStatus" +
            " AND TXN.TRANSACTIONSTATUS <> 'Submitted'" +
            " AND BNKTWO.BIC LIKE :bankName" +
            " AND DEP.NAME LIKE :dept" +
            " AND TRUNC (TXN.CREATEDAT) BETWEEN :fromDate AND :toDate" +
            " ORDER BY TXN.CREATEDAT DESC", nativeQuery = true)
    List<BankFundTransferOutwardProjection> getAllB2BOutWard(LocalDate fromDate,
                                                             LocalDate toDate,
                                                             String routingType,
                                                             String shortCode,
                                                             String batchNo,
                                                             String refNo,
                                                             String voucherNo,
                                                             String trxStatus,
                                                             String bankName,
                                                             String dept);

    @Query(value = "SELECT P.* FROM TBL_BANKFNDTRANSFERTXN P " +
            "LEFT JOIN RTGS_BANKS Q ON P.PAYER_BANK_ID = Q.ID " +
            "WHERE TRUNC(P.CREATEDAT) = :transactionDate " +
            "AND P.ROUTINGTYPE = 'Incoming' " +
            "AND P.PAYER_BANK_ID = :bankId " +
            "AND P.CURRENCY_ID = (SELECT ID FROM TBL_RTGS_CURRENCIES WHERE SHORTCODE = :shortCode)", nativeQuery = true)
    List<BankFndTransferEntity> findAllInwardB2BTransactionDetailsBankWise(LocalDate transactionDate, String shortCode, Long bankId);

    @Query(value = "SELECT Q.ID bankId, Q.NAME name, SUM(P.AMOUNT) amount FROM TBL_BANKFNDTRANSFERTXN P " +
            "LEFT JOIN RTGS_BANKS Q ON P.BEN_BANK_ID = Q.ID " +
            "WHERE TRUNC(P.CREATEDAT) = :transactionDate " +
            "AND P.ROUTINGTYPE = 'Outgoing' " +
            "AND P.VERIFICATIONSTATUS = 'Approved' " +
            "AND P.CURRENCY_ID = (SELECT ID FROM TBL_RTGS_CURRENCIES WHERE SHORTCODE = :shortCode) GROUP BY Q.NAME, Q.ID", nativeQuery = true)
    List<B2BProjection> findAllOutwardB2BTransactions(LocalDate transactionDate, String shortCode);

    @Query(value = "SELECT TXN.TRANSACTIONDATE AS time, " +
            "BNK.NAME AS payerBank, " +
            "TXN.BEN_NAME AS beneficiaryName, " +
            "BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, " +
            "TXN.REFERENCENUMBER AS referenceNumber, " +
            "CUR.SHORTCODE AS currency, " +
            "TXN.NARRATION AS narration, " +
            "TXN.LCNUMBER AS lcNumber, " +
            "BRN.NAME AS beneficiaryBranch, " +
            "BRNTWO.NAME AS beneficiaryReceiverBranch, " +
            "TXN.VOUCHERNUMBER AS voucher, " +
            "TXN.TRANSACTIONSTATUS AS status, " +
            "TXN.BILLNUMBER AS billNumber, " +
            "TXN.FCRECACCOUNTTYPE AS partyName, " +
            "DEPT.NAME AS department " +
            "FROM TBL_BANKFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERCUSTFNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE CUR.SHORTCODE LIKE :shortCode " +
            "AND TXN.ROUTINGTYPE = 'Outgoing' " +
            "AND TRUNC(TXN.TRANSACTIONDATE) = :transactionDate " +
            "ORDER BY TXN.DEPARTMENT_ID DESC, TXN.TRANSACTIONSTATUS ", nativeQuery = true)
    List<DashboardB2BOutwardTransaction> findAllOutwardB2BTransactionDetails(LocalDate transactionDate, String shortCode);

    @Query(value = "SELECT P.* FROM TBL_BANKFNDTRANSFERTXN P " +
            "LEFT JOIN RTGS_BANKS Q ON P.BEN_BANK_ID = Q.ID " +
            "WHERE TRUNC(P.CREATEDAT) = :transactionDate " +
            "AND P.ROUTINGTYPE = 'Outgoing' " +
            "AND P.VERIFICATIONSTATUS = 'Approved' " +
            "AND P.BEN_BANK_ID = :bankId " +
            "AND P.CURRENCY_ID = (SELECT ID FROM TBL_RTGS_CURRENCIES WHERE SHORTCODE = :shortCode) ", nativeQuery = true)
    List<BankFndTransferEntity> findAllOutwardB2BTransactionDetailsBankWise(LocalDate transactionDate, String shortCode, Long bankId);

    @Query(value = "SELECT COUNT(*) FROM TBL_BANKFNDTRANSFERTXN WHERE " +
            "TRANSACTIONSTATUS = 'Pending' " +
            "AND ROUTINGTYPE = 'Incoming' " +
            "AND TRUNC(TRANSACTIONDATE) = :date", nativeQuery = true)
    long countAllb2bPendingIncomingTransactions(LocalDate date);

    @Query(value = "SELECT COUNT(*) FROM TBL_BANKFNDTRANSFERTXN WHERE " +
            "TRANSACTIONSTATUS = 'Pending' " +
            "AND ROUTINGTYPE = 'Outgoing' " +
            "AND TRUNC(TRANSACTIONDATE) = :date", nativeQuery = true)
    long countAllb2bPendingOutgoingTransactions(LocalDate date);

    @Query(value = "SELECT COUNT(*) FROM TBL_BANKFNDTRANSFERTXN WHERE " +
            "TRANSACTIONSTATUS = 'Submitted' " +
            "AND ROUTINGTYPE = 'Outgoing' " +
            "AND TRUNC(CREATEDAT) = :date", nativeQuery = true)
    long countAllb2bSubmittedOutgoingTransactions(LocalDate date);

    @Query(value = "SELECT COUNT(*) FROM TBL_BANKFNDTRANSFERTXN WHERE " +
            "TRANSACTIONSTATUS = 'Failed' " +
            "AND TRUNC(TRANSACTIONDATE) = :date", nativeQuery = true)
    long countAllFailedTransactions(LocalDate date);

    @Query(value = "SELECT COUNT(*) FROM TBL_BANKFNDTRANSFERTXN WHERE " +
            "TRANSACTIONSTATUS = 'Reversed' " +
            "AND TRUNC(TRANSACTIONDATE) = :date", nativeQuery = true)
    long countAllReversedTransactions(LocalDate date);

    @Query(value = "SELECT COUNT(*) FROM TBL_BANKFNDTRANSFERTXN WHERE " +
            "TRANSACTIONSTATUS = 'Rejected' " +
            "AND TRUNC(TRANSACTIONDATE) = :date ", nativeQuery = true)
    long countAllRejectedTransactions(LocalDate date);

    @Query(value = "SELECT TXN.ID as id, TXN.TRANSACTIONSTATUS as status,TXN.TRANSACTIONDATE as txnDate,TXN.REFERENCENUMBER as refNumber, TXN.ROUTINGTYPE as routingType,TXN.PRIORITYCODE as priorityCode, " +
            "TXN.AMOUNT amount, TXN.NARRATION as narration, PTXN.BATCHNUMBER as batchNumber, TXN.VOUCHERNUMBER as voucher, " +
            "'BankToBank' AS fundTransferType , CUR.SHORTCODE as currency " +
            " FROM TBL_BANKFNDTRANSFERTXN TXN " +
            "LEFT JOIN TBL_INTERBANKFUNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER  LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "WHERE TXN.TRANSACTIONSTATUS = 'Pending' " +
            "AND TXN.ROUTINGTYPE = 'Outgoing' " +
            "AND TO_CHAR(TXN.REFERENCENUMBER) like :reference " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.TRANSACTIONDATE DESC ", nativeQuery = true)
    List<PendingOutwardTransactions> getAllOutwardPendingB2BTxn(LocalDate fromDate, LocalDate toDate, String reference);

    @Query(value = "SELECT COUNT(*) FROM TBL_BANKFNDTRANSFERTXN WHERE " +
            "TRANSACTIONSTATUS = 'Confirmed' " +
            "AND TRUNC(TRANSACTIONDATE) = :date ", nativeQuery = true)
    long countAllConfirmedTransactions(LocalDate date);
}
