package com.cbl.cityrtgs.repositories.transaction;

import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.projection.BalanceProjection;
import com.cbl.cityrtgs.models.dto.projection.CashLiquidityProjection;
import com.cbl.cityrtgs.models.dto.report.SettlementStatementReport;
import com.cbl.cityrtgs.models.entitymodels.transaction.AccountTransactionRegisterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountTransactionRegisterRepository extends JpaRepository<AccountTransactionRegisterEntity, Long> {
    Page<AccountTransactionRegisterEntity> findAllByIsDeletedFalse(Pageable pageable);
    Optional<AccountTransactionRegisterEntity> findByIdAndIsDeletedFalse(Long id);
    List<AccountTransactionRegisterEntity> findAllByIsDeletedFalse();
    Boolean existsByTransactionReferenceNumberAndRoutingType(String refNum, RoutingType routingType);
    Optional<AccountTransactionRegisterEntity> findByTransactionReferenceNumberAndRoutingType(String refNum, RoutingType routingType);
    @Query(value = "SELECT ID id, REGID regId, ACCOUNTNO accNo,CREDITAMOUNTCCY crAmountCcy, " +
            "          DEBITAMOUNTCCY dbAmountCcy, SETTLEMENTDDATE settlementDate, TRANSACTIONDATE transactionDate, " +
            "          TRANSACTIONREFERENCENUMBER refNo, " +
            "          IS_VALID isValid, ACCOUNT_ID accId, VOUCHERNUMBER voucherNo, " +
            "          DEBITOR_ID debitorId, CREDITOR_ID creditorId, " +
            "          DEBITORBRANCH_ID debitorBranchId, CREDITORBRANCH_ID creditorBranchId, " +
            "          COUNTERACCOUNTNO counterAccNo, NARRATION narration, POSTBALANCE postBalance, SHORTCODE currency " +
            "FROM TRANSCATIONREGISTER WHERE TRUNC(TRANSACTIONDATE) = :date", nativeQuery = true)
    List<CashLiquidityProjection> getCashLiquidity(LocalDate date);

    @Query(value = "SELECT N.POSTBALANCE balance, M.SHORTCODE shortcode FROM TBL_RTGS_CURRENCIES M INNER JOIN " +
            "(SELECT P.CURRENCY_ID, Q.* FROM TBL_RTGS_ACCOUNTS P INNER JOIN " +
            "(SELECT S.ACCOUNTNO, S.POSTBALANCE FROM TRANSCATIONREGISTER S, ( " +
            "    SELECT ACCOUNTNO, max(TRANSACTIONDATE) latest FROM TRANSCATIONREGISTER WHERE TRUNC(TRANSACTIONDATE) = :date GROUP BY ACCOUNTNO " +
            "    ) T WHERE T.ACCOUNTNO = S.ACCOUNTNO AND T.latest = S.TRANSACTIONDATE) Q ON P.CODE = Q.ACCOUNTNO) N ON M.ID = N.CURRENCY_ID", nativeQuery = true)
    List<BalanceProjection> getBalance(LocalDate date);
    @Query(value = "SELECT TXN.TRANSACTIONDATE AS txnDate, BNK.NAME AS debitorBank, BNKTWO.NAME AS creditorBank, SETTLEMENT.NAME AS settlementName, " +
            "TXN.TRANSACTIONREFERENCENUMBER AS reference, TXN.NARRATION AS narration, TXN.ACCOUNTNO AS settlementAcc, " +
            "BRN.NAME AS debitorBranch, BRNTWO.NAME AS creditorBranch, TXN.debitAmountCCY AS debitAmount, TXN.creditAmountCCY AS creditAmount, TXN.VOUCHERNUMBER AS voucher, " +
            "SUM (CASE WHEN CREDITAMOUNTCCY IS NOT NULL THEN NVL (DEBITCREDITSUM, 0) - NVL (CREDITAMOUNTCCY, 0) " +
            "ELSE NVL (DEBITCREDITSUM, 0) + NVL (DEBITAMOUNTCCY, 0) END) OVER ( " +
            "PARTITION BY TO_CHAR (TXN.TRANSACTIONDATE, 'dd/MM/yyyy'), TXN.ACCOUNTNO ORDER BY TXN.ID " +
            "ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) AS postBalance " +
            "FROM TBL_ACC_TXN_REGISTER TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.DEBITOR_ID = BNK.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.CREDITOR_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.DEBITORBRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.CREDITORBRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_RTGS_ACCOUNTS SETTLEMENT ON TXN.ACCOUNTNO = SETTLEMENT.CODE " +
            "WHERE " +
            "TO_CHAR(TXN.VOUCHERNUMBER) like :voucher AND " +
            "TO_CHAR(TXN.TRANSACTIONREFERENCENUMBER) like :reference AND " +
            "TO_CHAR(TXN.ACCOUNTNO) like :settlementAcc AND " +
            "TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.TRANSACTIONDATE DESC ", nativeQuery = true)
    List<SettlementStatementReport> getSettlementAccountRegisterReport(LocalDate fromDate, LocalDate toDate, String reference, String voucher, String settlementAcc);
    @Query(value = "SELECT TXN.TRANSACTIONDATE AS txnDate, BNK.NAME AS debitorBank, BNKTWO.NAME AS creditorBank, SETTLEMENT.NAME AS settlementName, " +
            "TXN.TRANSACTIONREFERENCENUMBER AS reference, TXN.NARRATION AS narration, TXN.ACCOUNTNO AS settlementAcc, " +
            "BRN.NAME AS debitorBranch, BRNTWO.NAME AS creditorBranch, TXN.debitAmountCCY AS debitAmount, TXN.creditAmountCCY AS creditAmount, TXN.VOUCHERNUMBER AS voucher, " +
            "SUM (CASE WHEN CREDITAMOUNTCCY IS NOT NULL THEN NVL (DEBITCREDITSUM, 0) - NVL (CREDITAMOUNTCCY, 0) " +
            "ELSE NVL (DEBITCREDITSUM, 0) + NVL (DEBITAMOUNTCCY, 0) END) OVER ( " +
            "PARTITION BY TO_CHAR (TXN.TRANSACTIONDATE, 'dd/MM/yyyy'), TXN.ACCOUNTNO ORDER BY TXN.ID " +
            "ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) AS postBalance " +
            "FROM TBL_ACC_TXN_REGISTER TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.DEBITOR_ID = BNK.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.CREDITOR_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.DEBITORBRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.CREDITORBRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_RTGS_ACCOUNTS SETTLEMENT ON TXN.ACCOUNTNO = SETTLEMENT.CODE " +
            "WHERE " +
            "TO_CHAR(TXN.ROUTINGTYPE) like :routingType AND " +
            "TO_CHAR(TXN.VOUCHERNUMBER) like :voucher AND " +
            "TO_CHAR(TXN.TRANSACTIONREFERENCENUMBER) like :reference AND " +
            "TO_CHAR(TXN.ACCOUNTNO) like :settlementAcc AND " +
            "TRUNC(TXN.TRANSACTIONDATE) >= :fromDate " +
            "AND TRUNC(TXN.TRANSACTIONDATE) <= :toDate " +
            "ORDER BY TXN.TRANSACTIONDATE DESC ", nativeQuery = true)
    List<SettlementStatementReport> getSettlementAccountRegisterReport2(LocalDate fromDate, LocalDate toDate, String reference, String voucher,String routingType, String settlementAcc);
}
