package com.cbl.cityrtgs.repositories.transaction;

import com.cbl.cityrtgs.models.entitymodels.view.ISysDateRegisterSummary;
import com.cbl.cityrtgs.models.entitymodels.view.SysDateRegisterSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface SysDateRegisterSummaryRepository extends JpaRepository<SysDateRegisterSummary, Long> {
    @Query(value = "SELECT ACCOUNTNO as accountNo, TRANSACTIONDATE as transactionDate, TOTALBALANCE as totalBalance, ACCOUNT_ID as account  FROM NEWSYSDATEREGISTERSUMMARY " +
            "WHERE ACCOUNTNO =:ACCOUNTNO ", nativeQuery = true)
    ISysDateRegisterSummary getRegisterSummaryByAccNo(@Param("ACCOUNTNO") String ACCOUNTNO);
}

