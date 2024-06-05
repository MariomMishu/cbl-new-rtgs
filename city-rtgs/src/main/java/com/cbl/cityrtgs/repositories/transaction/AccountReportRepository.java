package com.cbl.cityrtgs.repositories.transaction;

import com.cbl.cityrtgs.models.dto.reconcile.IReconcileDto;
import com.cbl.cityrtgs.models.entitymodels.transaction.AccountReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountReportRepository extends JpaRepository<AccountReportEntity, Long> {
    @Query(value = "SELECT " +
            "ST.ACCOUNTNUMBER AS accountNumber, " +
            "ST.CURRENCYCODE AS currencyCode, " +
            "ST.CLOSINGBOOKEDBALANCE AS centralBankClosingBalance, " +
            "RS.TOTALBALANCE AS settlementAccountBalance, " +
            "ST.CREATEDATE AS createDate, " +
            "ST.CREATETIME AS createTime FROM TBL_RTGS_ACC_REPORT ST LEFT JOIN NEWSYSDATEREGISTERSUMMARY RS ON ST.ACCOUNTNUMBER = RS.ACCOUNTNO " +
            "WHERE ST.ACCOUNTNUMBER = :ACCOUNTNUMBER " +
            "AND ST.CURRENCYCODE = :CURRENCYCODE " +
            "AND TO_CHAR(ST.CREATEDATE, 'YYYY-MM-dd') = (SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL)", nativeQuery = true)
    IReconcileDto getSettlementAccountReconcileMismatchReport(@Param("ACCOUNTNUMBER") String ACCOUNTNUMBER,
                                                               @Param("CURRENCYCODE") String CURRENCYCODE);
    @Query(value = "SELECT " +
            "ST.ACCOUNTNUMBER AS accountNumber, " +
            "ST.CURRENCYCODE AS currencyCode, " +
            "ST.CLOSINGBOOKEDBALANCE AS centralBankClosingBalance, " +
            "RS.TOTALBALANCE AS settlementAccountBalance, " +
            "ST.CREATEDATE AS createDate, "+
            "ST.CREATETIME AS createTime FROM TBL_RTGS_ACC_REPORT ST LEFT JOIN NEWSYSDATEREGISTERSUMMARY RS ON ST.ACCOUNTNUMBER = RS.ACCOUNTNO " +
            "WHERE ST.ACCOUNTNUMBER = :ACCOUNTNUMBER " +
            "AND ST.CURRENCYCODE  = :CURRENCYCODE " +
            "AND TO_CHAR(ST.CREATEDATE, 'YYYY-MM-dd') = (SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL)", nativeQuery = true)
    List<IReconcileDto> getSettlementAccountReport(String ACCOUNTNUMBER,
                                                   String CURRENCYCODE);
}
