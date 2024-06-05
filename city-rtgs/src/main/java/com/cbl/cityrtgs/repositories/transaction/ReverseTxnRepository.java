package com.cbl.cityrtgs.repositories.transaction;

import com.cbl.cityrtgs.models.entitymodels.transaction.CbsTxnReversalLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReverseTxnRepository extends JpaRepository<CbsTxnReversalLogEntity, Long> {
    @Query(value = "SELECT * FROM TBL_CBS_TXN_REV_LOG " +
            "WHERE PENDING = '1' " +
            "AND REVERSAL_ATTEMPTED = '0' " +
            "AND TO_CHAR(TRANSACTION_DATE, 'YYYY-MM-dd') = (SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) ORDER BY ID ASC", nativeQuery = true)
    List<CbsTxnReversalLogEntity> findAllByPendindTrueAndDate();
}
