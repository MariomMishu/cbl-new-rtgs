package com.cbl.cityrtgs.repositories.transaction.c2c;

import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.dashboard.DashboardC2CInwardTransaction;
import com.cbl.cityrtgs.models.dto.dashboard.DashboardC2COutwardTransaction;
import com.cbl.cityrtgs.models.dto.projection.ApprovalEventLogProjection;
import com.cbl.cityrtgs.models.dto.reconcile.IDepartmentAccount;
import com.cbl.cityrtgs.models.dto.report.*;
import com.cbl.cityrtgs.models.dto.transaction.ConfirmedInwardTransactions;
import com.cbl.cityrtgs.models.dto.transaction.PendingOutwardTransactions;
import com.cbl.cityrtgs.models.dto.transaction.b2b.C2CProjection;
import com.cbl.cityrtgs.models.dto.transaction.c2c.ITxnAmountResponse;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface CustomerFndTransferRepository extends JpaRepository<CustomerFndTransferEntity, Long>, JpaSpecificationExecutor<CustomerFndTransferEntity> {

    Boolean existsByReferenceNumberAndSentMsgId(String referenceNumber,String sentMsgId);
    Boolean existsByReferenceNumber(String referenceNumber);
    List<CustomerFndTransferEntity> findByParentBatchNumber(String batchNumber);
    @Query(value = "SELECT COUNT(*) FROM TBL_CUSTFNDTRANSFERTXN " +
            "WHERE ROUTINGTYPE ='Outgoing' " +
            "AND TRANSACTIONSTATUS ='Rejected' " +
            "AND PARENTBATCHNUMBER = :batchNumber AND ID !=:id " +
            "AND TO_CHAR(CREATEDAT, 'YYYY-MM-dd') = " +
            "TO_CHAR(sysdate, 'YYYY-MM-dd') ", nativeQuery = true)
    long existsByBatchNumber(String batchNumber, Long id);
    @Query(value = "SELECT COUNT(*) FROM TBL_CUSTFNDTRANSFERTXN " +
            "WHERE ROUTINGTYPE ='Outgoing' " +
            "AND PARENTBATCHNUMBER = :batchNumber " +
            "AND TO_CHAR(CREATEDAT, 'YYYY-MM-dd') = " +
            "TO_CHAR(sysdate, 'YYYY-MM-dd') ", nativeQuery = true)
    long totalCountByBatch(String batchNumber);
    Optional<CustomerFndTransferEntity> findByReferenceNumberAndRoutingType(String txnReferenceNumber, RoutingType outgoing);
    Page<CustomerFndTransferEntity> findAllByIsDeletedFalse(Pageable pageable);
    Optional<CustomerFndTransferEntity> findByIdAndIsDeletedFalse(Long id);
    List<CustomerFndTransferEntity> findAllByIsDeletedFalse();
    Optional<CustomerFndTransferEntity> findByReferenceNumberAndIsDeletedFalse(String ref);
    Optional<CustomerFndTransferEntity> findByTransactionsAndReferenceNumber(Long transaction, String ref);
    @Query(value = "SELECT * FROM TBL_CUSTFNDTRANSFERTXN " +
            "WHERE TRANSACTIONS = :transaction " +
            "AND PARENTBATCHNUMBER = :parentBatchNumber " +
            "AND TO_CHAR(CREATEDAT, 'YYYY-MM-dd') = " +
            "TO_CHAR(sysdate, 'YYYY-MM-dd') ", nativeQuery = true)
    List<CustomerFndTransferEntity> getAllByTransactionsAndParentBatchNumber(Long transaction, String parentBatchNumber);
    List<CustomerFndTransferEntity> findAllByTransactionsAndIsDeletedFalse(Long transaction);
    @Query(value = "SELECT " +
            "DEPARTMENT_ID AS departmentId, " +
            "ROUTINGTYPE AS routingType, " +
            "SUM(AMOUNT) AS amount ,SUM(VAT) AS vat ,SUM(CHARGE) AS charge " +
            "FROM (SELECT DEPARTMENT_ID, " +
            "ROUTINGTYPE, SUM(AMOUNT) AS AMOUNT ,SUM(VAT) AS VAT ,SUM(CHARGE) AS CHARGE " +
            "FROM TBL_CUSTFNDTRANSFERTXN WHERE CURRENCY_ID =:CURRENCY_ID AND TO_CHAR(TRANSACTIONDATE, 'YYYY-MM-dd') = (SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) AND TRANSACTIONSTATUS='Confirmed' " +
            "GROUP BY DEPARTMENT_ID, " +
            "ROUTINGTYPE UNION ALL " +
            "SELECT DEPARTMENT_ID, " +
            "ROUTINGTYPE, " +
            "SUM(AMOUNT) AS AMOUNT ,SUM(VAT) AS VAT ,SUM(CHARGE) AS CHARGE " +
            "FROM TBL_BANKFNDTRANSFERTXN WHERE CURRENCY_ID =:CURRENCY_ID AND TO_CHAR(TRANSACTIONDATE, 'YYYY-MM-dd') = (SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) AND TRANSACTIONSTATUS='Confirmed' " +
            "GROUP BY DEPARTMENT_ID, ROUTINGTYPE) GROUP BY DEPARTMENT_ID, ROUTINGTYPE", nativeQuery = true)
    List<IDepartmentAccount> getDeptWiseTxnAmount(@Param("CURRENCY_ID") Long CURRENCY_ID);

    @Query(value = "SELECT SUM(TXN.AMOUNT) AS AMOUNT FROM TBL_CUSTFNDTRANSFERTXN TXN " +
            "WHERE TXN.TRANSACTIONS = :transaction " +
            "AND TO_CHAR(TXN.CREATEDAT, 'YYYY-MM-dd') = " +
            "(SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) ", nativeQuery = true)
    ITxnAmountResponse getTxnAmount(Long transaction);

    @Query(value = "SELECT " +
            "DEPARTMENT_ID AS departmentId, " +
            "ROUTINGTYPE AS routingType, " +
            "SUM(AMOUNT) AS amount ,SUM(VAT) AS vat ,SUM(CHARGE) AS charge " +
            "FROM (SELECT DEPARTMENT_ID, " +
            "ROUTINGTYPE, SUM(AMOUNT) AS AMOUNT ,SUM(VAT) AS VAT ,SUM(CHARGE) AS CHARGE " +
            "FROM TBL_CUSTFNDTRANSFERTXN WHERE CURRENCY_ID =:CURRENCY_ID AND DEPARTMENT_ID =:DEPARTMENT_ID AND TO_CHAR(TRANSACTIONDATE, 'YYYY-MM-dd') = (SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) AND TRANSACTIONSTATUS='Confirmed' " +
            "GROUP BY DEPARTMENT_ID, " +
            "ROUTINGTYPE UNION ALL " +
            "SELECT DEPARTMENT_ID, " +
            "ROUTINGTYPE, " +
            "SUM(AMOUNT) AS AMOUNT ,SUM(VAT) AS VAT ,SUM(CHARGE) AS CHARGE " +
            "FROM TBL_BANKFNDTRANSFERTXN WHERE CURRENCY_ID =:CURRENCY_ID AND DEPARTMENT_ID =:DEPARTMENT_ID AND TO_CHAR(TRANSACTIONDATE, 'YYYY-MM-dd') = (SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) AND TRANSACTIONSTATUS='Confirmed' " +
            "GROUP BY DEPARTMENT_ID, ROUTINGTYPE) GROUP BY DEPARTMENT_ID, ROUTINGTYPE", nativeQuery = true)
    List<IDepartmentAccount> getTxnAmountByDeptId(@Param("CURRENCY_ID") Long CURRENCY_ID,
                                                   @Param("DEPARTMENT_ID") Long DEPARTMENT_ID);
    List<CustomerFndTransferEntity> findByTransactionsAndIsDeletedFalse(Long id);
    @Query(value = "SELECT * FROM TBL_CUSTFNDTRANSFERTXN " +
            "WHERE PARENTBATCHNUMBER = :batchNumber " +
            "AND REFERENCENUMBER = :reference " +
            "AND ROUTINGTYPE = 'Outgoing' AND TO_CHAR(CREATEDAT, 'YYYY-MM-dd') = " +
            "TO_CHAR(sysdate, 'YYYY-MM-dd') ", nativeQuery = true)
    CustomerFndTransferEntity getByBatchNumberAndRoutingTypeAndIsDeletedFalse(String batchNumber, String reference);
    @Query(value = "SELECT * FROM TBL_CUSTFNDTRANSFERTXN " +
            "WHERE TRANSACTIONS = :transaction " +
            "AND DEPARTMENT_ID = :deptId " +
            "AND IS_IB_TXN = 0 " +
            "AND TO_CHAR(CREATEDAT, 'YYYY-MM-dd') = " +
            "TO_CHAR(sysdate, 'YYYY-MM-dd') ", nativeQuery = true)
    List<CustomerFndTransferEntity> getAllByTransactionsAndDeptIdIsDeletedFalse(Long transaction, Long deptId);

    @Query(value = "SELECT * FROM TBL_CUSTFNDTRANSFERTXN " +
            "WHERE TRANSACTIONS = :transaction " +
            "AND IS_IB_TXN = 0 " +
            "AND TO_CHAR(CREATEDAT, 'YYYY-MM-dd') = " +
            "TO_CHAR(sysdate, 'YYYY-MM-dd') ", nativeQuery = true)
    List<CustomerFndTransferEntity> getAllByTransactionsIsDeletedFalse(Long transaction);

    @Query(value = "SELECT * FROM TBL_CUSTFNDTRANSFERTXN " +
            "WHERE TRANSACTIONS = :transaction " +
            "AND IS_IB_TXN = 0 " +
            "AND TO_CHAR(CREATEDAT, 'YYYY-MM-dd') = " +
            "TO_CHAR(sysdate, 'YYYY-MM-dd') ", nativeQuery = true)
    List<CustomerFndTransferEntity> getAllByTransactions(Long transaction);

    @Query(value = "SELECT COUNT(*) FROM TBL_CUSTFNDTRANSFERTXN WHERE " +
            "TRANSACTIONSTATUS = 'Pending' " +
            "AND ROUTINGTYPE = 'Incoming' " +
            "AND TRUNC(TRANSACTIONDATE) = :date", nativeQuery = true)
    long countAllc2cPendingIncomingTransactions(LocalDate date);

    @Query(value = "SELECT COUNT(*) FROM TBL_CUSTFNDTRANSFERTXN WHERE " +
            "TRANSACTIONSTATUS = 'Pending' " +
            "AND ROUTINGTYPE = 'Outgoing' " +
            "AND TRUNC(TRANSACTIONDATE) = :date", nativeQuery = true)
    long countAllc2cPendingOutgoingTransactions(LocalDate date);
    @Query(value = "SELECT COUNT(*) FROM TBL_CUSTFNDTRANSFERTXN WHERE " +
            "TRANSACTIONSTATUS = 'Submitted' " +
            "AND ROUTINGTYPE = 'Outgoing' " +
            "AND TRUNC(CREATEDAT) = :date", nativeQuery = true)
    long countAllc2cSubmittedOutgoingTransactions(LocalDate date);
    @Query(value = "SELECT COUNT(*) FROM TBL_CUSTFNDTRANSFERTXN WHERE " +
            "TRANSACTIONSTATUS = 'Failed' " +
            "AND IS_IB_TXN = 0 " +
            "AND TRUNC(TRANSACTIONDATE) = :date", nativeQuery = true)
    long countAllFailedTransactions(LocalDate date);
    @Query(value = "SELECT COUNT(*) FROM TBL_CUSTFNDTRANSFERTXN WHERE " +
            "TRANSACTIONSTATUS = 'Reversed' " +
            "AND TRUNC(TRANSACTIONDATE) = :date", nativeQuery = true)
    long countAllReversedTransactions(LocalDate date);

    @Query(value = "SELECT COUNT(*) FROM TBL_CUSTFNDTRANSFERTXN WHERE " +
            "TRANSACTIONSTATUS = 'Rejected' " +
            "AND TRUNC(TRANSACTIONDATE) = :date", nativeQuery = true)
    long countAllRejectedTransactions(LocalDate date);

    @Query(value = "SELECT CTXN.ID as id, CTXN.NARRATION as narration, CTXN.PAYER_ACC_NO as payerAccNo, CTXN.PAYER_NAME as payerName, BRN.NAME as payerBranch, DEP.ID AS deptId, DEP.NAME AS deptName, " +
            "CTXN.BEN_BANK_ID as beneficiaryBankId, BNKTWO.NAME as beneficiaryBank, CTXN.BEN_BRANCH_ID as beneficiaryBranchId, BRNTWO.NAME as beneficiaryBranch, CTXN.BEN_ACC_NO as beneficiaryAccountNo, CTXN.BEN_NAME as beneficiaryName, " +
            "CTXN.REFERENCENUMBER as referenceNumber, CTXN.VOUCHERNUMBER as voucherNumber, CTXN.AMOUNT as amount, CTXN.CHARGE as charge, CTXN.VAT as vat, CTXN.TRANSACTIONDATE as transactionDate " +
            "FROM TBL_CUSTFNDTRANSFERTXN CTXN " +
            "LEFT JOIN  TBL_DEPARTMENT DEP ON (DEP.ID = CTXN.DEPARTMENT_ID)  " +
            "LEFT JOIN RTGS_BANKS BNK ON CTXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON CTXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON CTXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON CTXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON CTXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "WHERE (:status IS NULL OR CTXN.TRANSACTIONSTATUS = :status) " +
            "AND (:routing IS NULL OR CTXN.ROUTINGTYPE = :routing) " +
            "AND (:currency IS NULL OR CTXN.CURRENCY_ID = :currency) " +
            "AND (:dept IS NULL OR CTXN.DEPARTMENT_ID = :dept) " +
            "AND TO_CHAR(CTXN.CBSNAME) like :cbsName " +
            "AND TRUNC(CTXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(CTXN.TRANSACTIONDATE) <= :toDate " +
            "UNION ALL " +
            "SELECT BTXN.ID as id, BTXN.NARRATION as narration, BTXN.PAYER_ACC_NO as payerAccNo, BTXN.PAYER_NAME as payerName, BRN.NAME as payerBranch, DEP.ID AS deptId, DEP.NAME AS deptName, " +
            "BTXN.BEN_BANK_ID as beneficiaryBankId,BNKTWO.NAME as beneficiaryBank, BTXN.BEN_BRANCH_ID as beneficiaryBranchId, BRNTWO.NAME as beneficiaryBranch, BTXN.BEN_ACC_NO as beneficiaryAccountNo, BTXN.BEN_NAME as beneficiaryName, " +
            "BTXN.REFERENCENUMBER as referenceNumber, BTXN.VOUCHERNUMBER as voucherNumber, BTXN.AMOUNT as amount, BTXN.CHARGE as charge, BTXN.VAT as vat, BTXN.TRANSACTIONDATE as transactionDate " +
            "FROM TBL_BANKFNDTRANSFERTXN BTXN " +
            "LEFT JOIN TBL_DEPARTMENT DEP ON (DEP.ID = BTXN.DEPARTMENT_ID) " +
            "LEFT JOIN RTGS_BANKS BNK ON BTXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON BTXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON BTXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON BTXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON BTXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "WHERE " +
            "(:status IS NULL OR BTXN.TRANSACTIONSTATUS = :status) " +
            "AND (:routing IS NULL OR BTXN.ROUTINGTYPE = :routing) " +
            "AND (:currency IS NULL OR BTXN.CURRENCY_ID = :currency) " +
            "AND (:dept IS NULL OR BTXN.DEPARTMENT_ID = :dept) " +
            "AND TO_CHAR(BTXN.CBSNAME) like :cbsName " +
            "AND TRUNC(BTXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(BTXN.TRANSACTIONDATE) <= :toDate ", nativeQuery = true)
    List<DepartmentWiseReport> getDepartmentWiseReport(LocalDate fromDate, LocalDate toDate, String routing, String currency, String status, String dept, String cbsName);

    @Query(value = "SELECT COUNT(T.ID) AS txnNumber, T.deptId as deptId, T.deptName AS deptName FROM " +
            " (SELECT CTXN.ID as id , DEP.ID as deptId, DEP.NAME as deptName " +
            "FROM TBL_CUSTFNDTRANSFERTXN CTXN LEFT JOIN TBL_DEPARTMENT DEP ON (DEP.ID = CTXN.DEPARTMENT_ID) " +
            "WHERE " +
            "(:status IS NULL OR CTXN.TRANSACTIONSTATUS = :status) " +
            "AND (:routing IS NULL OR CTXN.ROUTINGTYPE = :routing) " +
            "AND (:currency IS NULL OR CTXN.CURRENCY_ID = :currency) " +
            "AND TO_CHAR(CTXN.CBSNAME) like :cbsName " +
            "AND TRUNC(CTXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(CTXN.TRANSACTIONDATE) <= :toDate " +
            "UNION ALL  " +
            "SELECT BTXN.ID as id, DEP.ID as deptId, DEP.NAME as deptName  " +
            "FROM TBL_BANKFNDTRANSFERTXN BTXN LEFT JOIN TBL_DEPARTMENT DEP ON (DEP.ID = BTXN.DEPARTMENT_ID) WHERE " +
            "(:status IS NULL OR BTXN.TRANSACTIONSTATUS = :status) " +
            "AND (:routing IS NULL OR BTXN.ROUTINGTYPE = :routing) " +
            "AND (:currency IS NULL OR BTXN.CURRENCY_ID = :currency) " +
            "AND TO_CHAR(BTXN.CBSNAME) like :cbsName " +
            "AND TRUNC(BTXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(BTXN.TRANSACTIONDATE) <= :toDate) T GROUP BY T.deptId, T.deptName ", nativeQuery = true)
    List<DepartmentList> getAllDepartmentList(LocalDate fromDate, LocalDate toDate, String routing, String currency, String status, String cbsName);

    //            "AND TRUNC(TRANSACTIONDATE) >= TO_DATE('2023-02-22', 'YYYY-MM-dd') " +
//            "AND TRUNC(TRANSACTIONDATE) <= TO_DATE('2023-07-17', 'YYYY-MM-dd') " +
    @Query(value = "SELECT TXN.TRANSACTIONDATE AS transactionDate,TXN.PAYER_ACC_NO AS payerAccNo, TXN.PAYER_NAME AS PayerName, BNK.NAME AS payerBankName,TXN.BEN_ACC_NO AS beneficiaryCardNo, TXN.BEN_NAME AS beneficiaryName, TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN, RTGS_BANKS BNK, TBL_RTGS_CURRENCIES CUR " +
            "WHERE " +
            "TXN.PAYER_BANK_ID = BNK.ID " +
            "AND TXN.CURRENCY_ID = CUR.ID " +
            "AND TXN.CBSNAME ='CARD' " +
            "AND TXN.ROUTINGTYPE ='Incoming' " +
            "AND TXN.TRANSACTIONSTATUS ='Confirmed' " +
            "AND TXN.CURRENCY_ID = :currency " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.TRANSACTIONDATE DESC ", nativeQuery = true)
    List<CardTxnReport> getCardTransactionReport(LocalDate fromDate, LocalDate toDate, String currency);

    @Query(value = "SELECT TXN.TRANSACTIONDATE, TXN.REFERENCENUMBER AS reference, TXN.PARENTBATCHNUMBER AS batchNumber, " +
            "TXN.PAYER_ACC_NO AS payerAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBankName, BRANCH.NAME AS payerBranchName, " +
            "TXN.BEN_ACC_NO AS beneficiaryAccNo, TXN.BEN_NAME AS beneficiaryName, BANK2.NAME AS beneficiaryBankName, BRANCH2.NAME AS beneficiaryBranchName, " +
            "TXN.AMOUNT AS amount, TXN.CHARGE AS charge, TXN.VAT AS vat, DEP.NAME AS deptName, " +
            "CUR.SHORTCODE AS currency " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN " +
            "LEFT JOIN TBL_DEPARTMENT DEP ON (DEP.ID = TXN.DEPARTMENT_ID) LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID LEFT JOIN RTGS_BANKS BANK2 ON TXN.BEN_BANK_ID = BANK2.ID LEFT JOIN RTGS_BRANCHS BRANCH ON TXN.PAYER_BRANCH_ID = BRANCH.ID LEFT JOIN RTGS_BRANCHS BRANCH2 ON TXN.BEN_BRANCH_ID = BRANCH2.ID " +
            "WHERE " +
            "TXN.PAYER_BRANCH_ID = BRANCH.ID " +
            "AND TXN.PAYER_BANK_ID = BNK.ID " +
            "AND TXN.BEN_BRANCH_ID = BRANCH2.ID " +
            "AND TXN.BEN_BANK_ID = BANK2.ID " +
            "AND BRANCH.BANK_ID = BNK.ID " +
            "AND TXN.CURRENCY_ID = CUR.ID " +
            "AND TXN.DEPARTMENT_ID = :dept " +
            "AND TXN.ROUTINGTYPE ='Outgoing' " +
            "AND TXN.TRANSACTIONSTATUS IN('Confirmed','Reversed') " +
            "AND TXN.CURRENCY_ID = :currency " +
            "AND TO_CHAR(TXN.CBSNAME) like :cbsName " +
            "AND TXN.CHARGE > 0 " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY deptName DESC ", nativeQuery = true)
    List<ChargeVatReport> getChargeVatReport(LocalDate fromDate, LocalDate toDate, String currency, String cbsName, String dept);

    @Query(value = "SELECT TXN.TRANSACTIONDATE, TXN.REFERENCENUMBER AS reference, TXN.PARENTBATCHNUMBER AS batchNumber, " +
            "TXN.PAYER_ACC_NO AS payerAccNo, TXN.PAYER_NAME AS PayerName, BNK.NAME AS payerBankName, BRANCH.NAME AS payerBranchName, " +
            "TXN.BEN_ACC_NO AS beneficiaryAccNo, TXN.BEN_NAME AS beneficiaryName, BANK2.NAME AS beneficiaryBankName, BRANCH2.NAME AS beneficiaryBranchName, " +
            "TXN.AMOUNT AS amount, TXN.CHARGE AS charge, TXN.VAT AS vat, " +
            "CUR.SHORTCODE AS currency " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN, RTGS_BRANCHS BRANCH, RTGS_BANKS BNK, RTGS_BRANCHS BRANCH2, RTGS_BANKS BANK2, TBL_RTGS_CURRENCIES CUR " +
            "WHERE " +
            "TXN.PAYER_BRANCH_ID = BRANCH.ID " +
            "AND TXN.PAYER_BANK_ID = BNK.ID " +
            "AND TXN.BEN_BRANCH_ID = BRANCH2.ID " +
            "AND TXN.BEN_BANK_ID = BANK2.ID " +
            "AND BRANCH.BANK_ID = BNK.ID " +
            "AND TXN.CURRENCY_ID = CUR.ID " +
            "AND TXN.ROUTINGTYPE ='Outgoing' " +
            "AND TXN.TRANSACTIONSTATUS ='Confirmed' " +
            "AND TXN.CHARGE > 0 " +
            "AND TO_CHAR(TXN.CBSNAME) like :cbsName " +
            "AND (:currency IS NULL OR TXN.CURRENCY_ID = :currency) " +
            "AND (:deliveryChannel IS NULL OR TXN.DELIVERYCHANNEL = :deliveryChannel) " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "UNION ALL " +
            "SELECT TXN.TRANSACTIONDATE, TXN.REFERENCENUMBER AS reference, TXN.PARENTBATCHNUMBER AS batchNumber, " +
            "TXN.PAYER_ACC_NO AS payerAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBankName, BRANCH.NAME AS payerBranchName," +
            "TXN.BEN_ACC_NO AS beneficiaryAccNo, TXN.BEN_NAME AS beneficiaryName, BANK2.NAME AS beneficiaryBankName, BRANCH2.NAME AS beneficiaryBranchName, " +
            "TXN.AMOUNT AS amount, TXN.CHARGE AS charge, TXN.VAT AS vat, " +
            "CUR.SHORTCODE AS currency " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN, RTGS_BRANCHS BRANCH, RTGS_BANKS BNK, RTGS_BRANCHS BRANCH2, RTGS_BANKS BANK2, TBL_RTGS_CURRENCIES CUR, TBL_CBS_TXN_REV_LOG REV " +
            "WHERE " +
            "TXN.PAYER_BRANCH_ID = BRANCH.ID " +
            "AND TXN.PAYER_BANK_ID = BNK.ID " +
            "AND TXN.BEN_BRANCH_ID = BRANCH2.ID " +
            "AND TXN.BEN_BANK_ID = BANK2.ID " +
            "AND BRANCH.BANK_ID = BNK.ID " +
            "AND TXN.CURRENCY_ID = CUR.ID " +
            "AND TXN.ROUTINGTYPE ='Outgoing' " +
            "AND TXN.TRANSACTIONSTATUS ='Reversed' " +
            "AND TXN.CHARGE > 0 " +
            "AND (:currency IS NULL OR TXN.CURRENCY_ID = :currency) " +
            "AND (:deliveryChannel IS NULL OR TXN.DELIVERYCHANNEL = :deliveryChannel) " +
            "AND TO_CHAR(TXN.CBSNAME) like :cbsName " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate ", nativeQuery = true)
    List<ChargeVatReport> getIBChargeVatReport(LocalDate fromDate, LocalDate toDate, String deliveryChannel, String currency, String cbsName);

    @Query(value = "SELECT TXN.TRANSACTIONDATE AS txnDate, TXN.PAYER_ACC_NO AS payerAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBank, TXN.BEN_ACC_NO AS beneficiaryAccNo, TXN.BEN_NAME AS beneficiaryName, BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, CUR.SHORTCODE AS currency, TXN.PARENTBATCHNUMBER AS batchNumber, TXN.NARRATION AS narration, TXN.RETURNREASON AS returnReason,  " +
            "BRN.NAME AS payerBranch, BRNTWO.NAME AS beneficiaryBranch, TXN.CHARGE AS charge, TXN.VAT AS vat, TXN.VOUCHERNUMBER AS voucher, TXN.TRANSACTIONSTATUS AS txnStatus, DEPT.ID AS deptId, DEPT.NAME AS deptName " +
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
            "TO_CHAR(TXN.ROUTINGTYPE) like :routing AND " +
            "TO_CHAR(TXN.CURRENCY_ID) like :currency AND " +
            "TO_CHAR(TXN.REFERENCENUMBER) like :reference AND " +
            "TO_CHAR(TXN.PAYER_ACC_NO) like :payerAcc AND " +
            "TO_CHAR(TXN.BEN_ACC_NO) like :benAcc AND " +
            "TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.DEPARTMENT_ID DESC, TXN.TRANSACTIONSTATUS ", nativeQuery = true)
    List<AcknowledgementSlip> getAcknowledgementSlip(LocalDate fromDate, LocalDate toDate, String reference, String payerAcc, String benAcc, String currency, String routing);

    @Query(value = "SELECT COUNT(T.ID) AS txnNumber, T.deptId as deptId, T.deptName AS deptName " +
            "FROM (SELECT TXN.ID, DEP.ID AS deptId, DEP.NAME AS deptName " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN " +
            "LEFT JOIN TBL_DEPARTMENT DEP ON (DEP.ID = TXN.DEPARTMENT_ID) " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BANK2 ON TXN.BEN_BANK_ID = BANK2.ID " +
            "LEFT JOIN RTGS_BRANCHS BRANCH ON TXN.PAYER_BRANCH_ID = BRANCH.ID " +
            "LEFT JOIN RTGS_BRANCHS BRANCH2 ON TXN.BEN_BRANCH_ID = BRANCH2.ID " +
            "WHERE " +
            "TXN.PAYER_BRANCH_ID = BRANCH.ID " +
            "AND TXN.PAYER_BANK_ID = BNK.ID " +
            "AND TXN.BEN_BRANCH_ID = BRANCH2.ID " +
            "AND TXN.BEN_BANK_ID = BANK2.ID " +
            "AND BRANCH.BANK_ID = BNK.ID " +
            "AND TXN.CURRENCY_ID = CUR.ID " +
            "AND TXN.DEPARTMENT_ID = DEP.ID " +
            "AND TXN.ROUTINGTYPE ='Outgoing' " +
            "AND TXN.TRANSACTIONSTATUS IN('Confirmed','Reversed') " +
            "AND TXN.CURRENCY_ID = :currency " +
            "AND TO_CHAR(TXN.CBSNAME) like :cbsName " +
            "AND TXN.CHARGE > 0 " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.TRANSACTIONDATE DESC ) T " +
            "GROUP BY T.deptId , T.deptName ", nativeQuery = true)
    List<DepartmentList> getDepartmentList(LocalDate fromDate, LocalDate toDate, String currency, String cbsName);

    @Query(value = "SELECT TXN.ID as id, TXN.TRANSACTIONSTATUS as status, TXN.TRANSACTIONDATE as txnDate, TXN.REFERENCENUMBER as refNumber, " +
            "TXN.ROUTINGTYPE as routingType, TXN.PRIORITYCODE as priorityCode, TXN.SETTLEMENTDATE as settlementDate, PTXN.BATCHNUMBER as batchNumber " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN " +
            "LEFT JOIN TBL_INTERCUSTFNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "WHERE TXN.TRANSACTIONSTATUS ='Confirmed' " +
            "AND TXN.ROUTINGTYPE = 'Incoming' " +
            "AND TO_CHAR(TXN.REFERENCENUMBER) like :reference " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.TRANSACTIONDATE DESC ", nativeQuery = true)
    List<ConfirmedInwardTransactions> getAllInwardConfirmedC2CTxn(LocalDate fromDate, LocalDate toDate, String reference);

    @Query(value = "SELECT TXN.ID as id, TXN.TRANSACTIONSTATUS as status,TXN.TRANSACTIONDATE as txnDate,TXN.REFERENCENUMBER as refNumber, TXN.ROUTINGTYPE as routingType,TXN.PRIORITYCODE as priorityCode, " +
            "TXN.AMOUNT amount, TXN.NARRATION as narration, PTXN.BATCHNUMBER as batchNumber, TXN.VOUCHERNUMBER as voucher, " +
            "'CustomerToCustomer' AS fundTransferType , CUR.SHORTCODE as currency " +
            " FROM TBL_CUSTFNDTRANSFERTXN TXN " +
            "LEFT JOIN TBL_INTERCUSTFNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER  LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "WHERE TXN.TRANSACTIONSTATUS = 'Pending' " +
            "AND TXN.ROUTINGTYPE = 'Outgoing' " +
            "AND TO_CHAR(TXN.REFERENCENUMBER) like :reference " +
            "AND TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.TRANSACTIONDATE DESC ", nativeQuery = true)
    List<PendingOutwardTransactions> getAllOutwardPendingC2CTxn(LocalDate fromDate, LocalDate toDate, String reference);

    @Query(value = "SELECT U.*, V.NAME benBank FROM " +
            "(SELECT J.*, K.NAME payerBranch FROM " +
            "(SELECT M.*, N.NAME payerBank FROM " +
            "    (SELECT R.*, S.SHORTCODE currency FROM " +
            "        (SELECT P.TRANSACTIONSTATUS status, " +
            "           Q.ENTRYUSER entryUser, " +
            "           P.PARENTBATCHNUMBER batchNumber, " +
            "           P.CURRENCY_ID currencyId, " +
            "           P.NARRATION referenceText, " +
            "           P.PAYER_BRANCH_ID payerBranchId, " +
            "           P.AMOUNT amount, " +
            "           P.PAYER_ACC_NO payerAccNo, " +
            "           P.PAYER_BANK_ID payerBankId, " +
            "           P.BEN_ACC_NO benAccNo, " +
            "           P.BEN_BANK_ID benBankId," +
            "           P.CREATEDAT entryTime " +
            "    FROM TBL_CUSTFNDTRANSFERTXN P INNER JOIN TBL_INTERCUSTFNDTRANSFER Q " +
            "        ON P.PARENTBATCHNUMBER = Q.BATCHNUMBER " +
            "        WHERE " +
            "            TRUNC(P.CREATEDAT) = :date" +
            "          AND P.TRANSACTIONSTATUS = 'Submitted') R " +
            "            LEFT JOIN TBL_RTGS_CURRENCIES S ON R.currencyId = S.ID) " +
            "        M LEFT JOIN RTGS_BANKS N ON M.payerBankId = N.ID) " +
            "    J LEFT JOIN RTGS_BRANCHS K ON J.payerBranchId = K.ID) U LEFT JOIN RTGS_BANKS V ON U.benBankId = V.ID " +
            "UNION ALL " +
            "SELECT U.*, V.NAME benBank FROM " +
            "(SELECT J.*, K.NAME payerBranch FROM " +
            "(SELECT M.*, N.NAME payerBank FROM " +
            "    (SELECT R.*, S.SHORTCODE currency FROM " +
            "        (SELECT P.TRANSACTIONSTATUS status, " +
            "           Q.ENTRYUSER entryUser, " +
            "           P.PARENTBATCHNUMBER batchNumber, " +
            "           P.CURRENCY_ID currencyId, " +
            "           P.NARRATION referenceText, " +
            "           P.PAYER_BRANCH_ID payerBranchId, " +
            "           P.AMOUNT amount, " +
            "           P.PAYER_ACC_NO payerAccNo, " +
            "           P.PAYER_BANK_ID payerBankId, " +
            "           P.BEN_ACC_NO benAccNo, " +
            "           P.BEN_BANK_ID benBankId," +
            "           P.CREATEDAT entryTime " +
            "    FROM TBL_BANKFNDTRANSFERTXN P INNER JOIN TBL_INTERBANKFUNDTRANSFER Q " +
            "        ON P.PARENTBATCHNUMBER = Q.BATCHNUMBER " +
            "        WHERE " +
            "            TRUNC(P.CREATEDAT) = :date " +
            "          AND P.TRANSACTIONSTATUS = 'Submitted') R LEFT JOIN TBL_RTGS_CURRENCIES S ON R.currencyId = S.ID) " +
            "        M LEFT JOIN RTGS_BANKS N ON M.payerBankId = N.ID) J LEFT JOIN RTGS_BRANCHS K ON J.payerBranchId = K.ID) U LEFT JOIN RTGS_BANKS V ON U.benBankId = V.ID", nativeQuery = true)
    List<ApprovalEventLogProjection> getApprovalEventLogs(LocalDate date);

    @Query(value = "SELECT C.PAYER_ACC_NO acNumber, C.PAYER_NAME acName, C.TRANSACTIONDATE dateTime, C.SOURCEREFERENCE referenceNumber, P.CREATEDBY maker," +
            "C.VOUCHERNUMBER voucher, C.AMOUNT amount, B.NAME bank, BR.NAME branch, C.BEN_ACC_NO receiverAccount, C.BEN_NAME receiverName, C.TRANSACTIONSTATUS status " +
            "FROM TBL_CUSTFNDTRANSFERTXN C " +
            "LEFT JOIN TBL_INTERCUSTFNDTRANSFER P ON P.BATCHNUMBER = c.PARENTBATCHNUMBER " +
            "LEFT JOIN RTGS_BANKS B ON C.BEN_BANK_ID = B.ID " +
            "LEFT JOIN RTGS_BRANCHS BR ON C.PAYER_BRANCH_ID = BR.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CR ON C.CURRENCY_ID = CR.ID " +
            "WHERE C.SOURCETYPE = 1 " +
            "AND C.TRANSACTIONSTATUS = 'Confirmed' " +
            "AND (:branch IS NULL OR C.BEN_BRANCH_ID = :branch) " +
            "AND (:fromDate IS NULL OR TRUNC(C.TRANSACTIONDATE) >= :fromDate) " +
            "AND (:toDate IS NULL OR TRUNC(C.TRANSACTIONDATE) <= :toDate) " +
            "AND (:currencyCode IS NULL OR CR.SHORTCODE = :currencyCode)", nativeQuery = true)
    List<SiReport> getSIReport(LocalDate fromDate, LocalDate toDate, String branch, String currencyCode);

    @Query(value = "SELECT COUNT(*) FROM TBL_CUSTFNDTRANSFERTXN WHERE " +
            "TRANSACTIONSTATUS = 'Confirmed' " +
            "AND TRUNC(TRANSACTIONDATE) = :date", nativeQuery = true)
    long countAllConfirmedTransactions(LocalDate date);


    @Query(value = "SELECT Q.ID bankId, Q.NAME name, SUM(P.AMOUNT) amount FROM TBL_CUSTFNDTRANSFERTXN P " +
            "LEFT JOIN RTGS_BANKS Q ON P.PAYER_BANK_ID = Q.ID " +
            "WHERE TRUNC(P.CREATEDAT) = :transactionDate " +
            "AND P.ROUTINGTYPE = 'Incoming' " +
            "AND P.CURRENCY_ID = (SELECT ID FROM TBL_RTGS_CURRENCIES WHERE SHORTCODE = :shortCode) GROUP BY Q.NAME, Q.ID", nativeQuery = true)
    List<C2CProjection> findAllInwardC2CTransactions(LocalDate transactionDate, String shortCode);

@Query(value ="SELECT TXN.TRANSACTIONDATE AS transactionDate, " +
        "TXN.PAYER_ACC_NO AS payerAccount, " +
        "TXN.PAYER_NAME AS payerName, " +
        "BNK.NAME AS payerBank, " +
        "TXN.BEN_ACC_NO AS beneficiaryAccount, " +
        "TXN.BEN_NAME AS beneficiaryName, " +
        "BNKTWO.NAME AS beneficiaryBank, " +
        "TXN.AMOUNT AS amount, " +
        "TXN.CHARGE AS charge, " +
        "TXN.VAT AS vat, " +
        "TXN.REFERENCENUMBER AS referenceNumber, " +
        "CUR.SHORTCODE AS currency, " +
        "TXN.NARRATION AS narration, " +
        "TXN.LCNUMBER AS lcNumber, " +
        "TXN.ROUTINGTYPE AS routingType, " +
        "TXN.DELIVERYCHANNEL AS deliveryChannel, " +
        "BRN.NAME AS payerBranch, " +
        "BRNTWO.NAME AS beneficiaryBranch, " +
        "TXN.VOUCHERNUMBER AS voucher, " +
        "TXN.TRANSACTIONSTATUS AS status, " +
        "DEPT.NAME AS department " +
        "FROM TBL_CUSTFNDTRANSFERTXN TXN " +
        "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
        "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
        "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
        "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
        "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
        "LEFT JOIN TBL_INTERCUSTFNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
        "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
        "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
        "WHERE CUR.SHORTCODE LIKE :shortCode " +
        "AND TXN.ROUTINGTYPE = 'Incoming' " +
        "AND TRUNC(TXN.TRANSACTIONDATE) = :transactionDate " +
        "ORDER BY TXN.DEPARTMENT_ID DESC, TXN.TRANSACTIONSTATUS", nativeQuery = true)
    List<DashboardC2CInwardTransaction> findAllInwardC2CTransactionsDetails(LocalDate transactionDate, String shortCode);

    @Query(value = "SELECT P.* FROM TBL_CUSTFNDTRANSFERTXN P " +
            "LEFT JOIN RTGS_BANKS Q ON P.PAYER_BANK_ID = Q.ID " +
            "WHERE TRUNC(P.CREATEDAT) = :transactionDate " +
            "AND P.ROUTINGTYPE = 'Incoming' " +
            "AND P.PAYER_BANK_ID = :bankId " +
            "AND P.CURRENCY_ID = (SELECT ID FROM TBL_RTGS_CURRENCIES WHERE SHORTCODE = :shortCode)", nativeQuery = true)
    List<CustomerFndTransferEntity> findAllInwardC2CTransactionsDetailsBankWise(LocalDate transactionDate, String shortCode, Long bankId);
    @Query(value = "SELECT Q.ID bankId, Q.NAME name, SUM(P.AMOUNT) amount FROM TBL_CUSTFNDTRANSFERTXN P " +
            "LEFT JOIN RTGS_BANKS Q ON P.BEN_BANK_ID = Q.ID " +
            "WHERE TRUNC(P.CREATEDAT) = :transactionDate " +
            "AND P.ROUTINGTYPE = 'Outgoing' " +
            "AND P.VERIFICATIONSTATUS = 'Approved' " +
            "AND P.CURRENCY_ID = (SELECT ID FROM TBL_RTGS_CURRENCIES WHERE SHORTCODE = :shortCode) GROUP BY Q.NAME, Q.ID", nativeQuery = true)
    List<C2CProjection> findAllOutwardC2CTransactions(LocalDate transactionDate, String shortCode);

    @Query(value ="SELECT TXN.TRANSACTIONDATE AS transactionTime, " +
            "TXN.PAYER_ACC_NO AS fromAccount, " +
            "TXN.PAYER_NAME AS payerName, " +
            "BNK.NAME AS payerBank, " +
            "TXN.BEN_ACC_NO AS beneficiaryAccount, " +
            "TXN.BEN_NAME AS beneficiaryName, " +
            "BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, " +
            "TXN.CHARGE AS charge, " +
            "TXN.VAT AS vat, " +
            "TXN.REFERENCENUMBER AS referenceNumber, " +
            "CUR.SHORTCODE AS currency, " +
            "TXN.NARRATION AS narration, " +
            "TXN.LCNUMBER AS lcNumber, " +
            "TXN.ROUTINGTYPE AS routingType, " +
            "TXN.DELIVERYCHANNEL AS deliveryChannel, " +
            "BRN.NAME AS payerBranch, " +
            "BRNTWO.NAME AS beneficiaryBranch, " +
            "TXN.VOUCHERNUMBER AS voucher, " +
            "TXN.TRANSACTIONSTATUS AS status, " +
            "DEPT.NAME AS department " +
            "FROM TBL_CUSTFNDTRANSFERTXN TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON TXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERCUSTFNDTRANSFER PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "LEFT JOIN TBL_DEPARTMENT DEPT ON TXN.DEPARTMENT_ID = DEPT.ID " +
            "WHERE CUR.SHORTCODE LIKE :shortCode " +
            "AND TXN.ROUTINGTYPE = 'Outgoing' " +
            "AND TRUNC(TXN.TRANSACTIONDATE) = :transactionDate " +
            "ORDER BY TXN.DEPARTMENT_ID DESC, TXN.TRANSACTIONSTATUS", nativeQuery = true)
    List<DashboardC2COutwardTransaction> findAllOutwardC2CTransactionsDetails(LocalDate transactionDate, String shortCode);

    @Query(value = "SELECT P.* FROM TBL_CUSTFNDTRANSFERTXN P " +
            "LEFT JOIN RTGS_BANKS Q ON P.BEN_BANK_ID = Q.ID " +
            "WHERE TRUNC(P.CREATEDAT) = :transactionDate " +
            "AND P.ROUTINGTYPE = 'Outgoing' " +
            "AND P.VERIFICATIONSTATUS = 'Approved' " +
            "AND P.BEN_BANK_ID = :bankId " +
            "AND P.CURRENCY_ID = (SELECT ID FROM TBL_RTGS_CURRENCIES WHERE SHORTCODE = :shortCode)", nativeQuery = true)
    List<CustomerFndTransferEntity> findAllOutwardC2CTransactionsDetailsBankWise(LocalDate transactionDate, String shortCode, Long bankId);

    @Query(value = "SELECT CTXN.ID as id, CTXN.NARRATION as narration, CTXN.PAYER_ACC_NO as payerAccNo, CTXN.PAYER_NAME as payerName, CTXN.PAYER_BRANCH_ID as payerBranchId, BRN.NAME as payerBranch, DEP.ID AS deptId, DEP.NAME AS deptName, " +
            "CTXN.BEN_BANK_ID as beneficiaryBankId, BNKTWO.NAME as beneficiaryBank, CTXN.BEN_BRANCH_ID as beneficiaryBranchId, BRNTWO.NAME as beneficiaryBranch, CTXN.BEN_ACC_NO as beneficiaryAccountNo, CTXN.BEN_NAME as beneficiaryName, " +
            "CTXN.REFERENCENUMBER as referenceNumber, CTXN.VOUCHERNUMBER as voucherNumber, CTXN.AMOUNT as amount, CTXN.CHARGE as charge, CTXN.VAT as vat, CTXN.TRANSACTIONDATE as transactionDate " +
            "FROM TBL_CUSTFNDTRANSFERTXN CTXN " +
            "LEFT JOIN  TBL_DEPARTMENT DEP ON (DEP.ID = CTXN.DEPARTMENT_ID)  " +
            "LEFT JOIN RTGS_BANKS BNK ON CTXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON CTXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON CTXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON CTXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON CTXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "WHERE (:status IS NULL OR CTXN.TRANSACTIONSTATUS = :status) " +
            "AND (:routing IS NULL OR CTXN.ROUTINGTYPE = :routing) " +
            "AND (:currency IS NULL OR CTXN.CURRENCY_ID = :currency) " +
            "AND TO_CHAR(CTXN.CBSNAME) like :cbsName " +
            "AND TRUNC(CTXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(CTXN.TRANSACTIONDATE) <= :toDate " +
            "UNION ALL " +
            "SELECT BTXN.ID as id, BTXN.NARRATION as narration, BTXN.PAYER_ACC_NO as payerAccNo, BTXN.PAYER_NAME as payerName, BTXN.PAYER_BRANCH_ID as payerBranchId, BRN.NAME as payerBranch, DEP.ID AS deptId, DEP.NAME AS deptName, " +
            "BTXN.BEN_BANK_ID as beneficiaryBankId, BNKTWO.NAME as beneficiaryBank, BTXN.BEN_BRANCH_ID as beneficiaryBranchId,BRNTWO.NAME as beneficiaryBranch, BTXN.BEN_ACC_NO as beneficiaryAccountNo, BTXN.BEN_NAME as beneficiaryName, " +
            "BTXN.REFERENCENUMBER as referenceNumber, BTXN.VOUCHERNUMBER as voucherNumber, BTXN.AMOUNT as amount, BTXN.CHARGE as charge, BTXN.VAT as vat, BTXN.TRANSACTIONDATE as transactionDate " +
            "FROM TBL_BANKFNDTRANSFERTXN BTXN " +
            "LEFT JOIN  TBL_DEPARTMENT DEP ON (DEP.ID = BTXN.DEPARTMENT_ID) " +
            "LEFT JOIN RTGS_BANKS BNK ON BTXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON BTXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON BTXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON BTXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON BTXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "WHERE (:status IS NULL OR BTXN.TRANSACTIONSTATUS = :status) " +
            "AND (:routing IS NULL OR BTXN.ROUTINGTYPE = :routing) " +
            "AND (:currency IS NULL OR BTXN.CURRENCY_ID = :currency) " +
           // "AND TO_CHAR(BTXN.CBSNAME) like :cbsName " +
            "AND TRUNC(BTXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(BTXN.TRANSACTIONDATE) <= :toDate ORDER BY deptId ASC ", nativeQuery = true)
    List<DepartmentWiseReport> getAllDepartmentWiseReport(LocalDate fromDate, LocalDate toDate, String routing, String currency, String status, String cbsName);

    @Query(value = "SELECT COUNT(T.ID) AS txnNumber, T.deptId as deptId, T.deptName AS deptName FROM " +
            " (SELECT CTXN.ID as id , DEP.ID as deptId, DEP.NAME as deptName " +
            "FROM TBL_CUSTFNDTRANSFERTXN CTXN " +
            "LEFT JOIN TBL_DEPARTMENT DEP ON (DEP.ID = CTXN.DEPARTMENT_ID) " +
            "LEFT JOIN TBL_RTGS_CURRENCIES CUR ON CTXN.CURRENCY_ID = CUR.ID " +
            "LEFT JOIN TBL_INTERCUSTFNDTRANSFER PTXN ON CTXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "WHERE " +
            "PTXN.TXNTYPECODE = '041' " +
            "AND CTXN.ROUTINGTYPE = 'Outgoing' " +
            "AND CUR.SHORTCODE = 'BDT' " +
            "AND TO_CHAR(CTXN.CBSNAME) like :cbsName " +
            "AND TRUNC(CTXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(CTXN.TRANSACTIONDATE) <= :toDate " +
            ") T GROUP BY T.deptId, T.deptName ", nativeQuery = true)
    List<DepartmentList> getAllDepartmentListForCustomduty(LocalDate fromDate, LocalDate toDate, String cbsName);
}
