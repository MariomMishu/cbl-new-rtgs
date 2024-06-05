package com.cbl.cityrtgs.repositories.transaction;

import com.cbl.cityrtgs.models.dto.report.BBSettlementReport;
import com.cbl.cityrtgs.models.entitymodels.transaction.SettlementAccountStatementDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SettlementAccountStatementDetailRepository extends JpaRepository<SettlementAccountStatementDetailEntity, Long> {
    @Query(value = "SELECT STATEMENT.CURRENCYCODE AS currencyCode, TXN.TRANSACTIONREF AS reference, " +
            "TXN.TRANSACTIONCODE AS txnCode, TXN.TRANSACTIONID AS txnId, TXN.TRANSACTIONSTATUS AS txnStatus, " +
            "TXN.DEBITAMOUNT AS debitAmount, TXN.CREDITAMOUNT AS creditAmount " +
            "FROM TBL_RTGS_ACC_ST_DTL TXN " +
            "LEFT JOIN TBL_RTGS_ACC_STATEMENT STATEMENT ON TXN.STATEMENT_ID = STATEMENT.ID " +
            "WHERE " +
            "TO_CHAR(STATEMENT.CURRENCYCODE) like :currency AND " +
            "TRUNC(TXN.VALUEDATE) = :valueDate " +
            "ORDER BY TXN.VALUEDATE DESC ", nativeQuery = true)
    List<BBSettlementReport> getBbSettlementReport(LocalDate valueDate, String currency);

}
