package com.cbl.cityrtgs.repositories.transaction;

import com.cbl.cityrtgs.models.entitymodels.transaction.CbsTxnLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface CbsTxnLogRepository extends JpaRepository<CbsTxnLogEntity, Long> {
    Page<CbsTxnLogEntity> findAllByIsDeletedFalse(Pageable pageable);

    Optional<CbsTxnLogEntity> findByIdAndIsDeletedFalse(Long id);

    List<CbsTxnLogEntity> findAllByIsDeletedFalse();

    @Query(value = "SELECT COUNT(*) FROM TBL_CBS_TXN_LOG " +
            "WHERE CBSRESPONSECODE = '100' " +
            "AND RTGSREFERENCENUMBER = :rtgsReferenceNumber " +
            "AND TO_CHAR(CREATEDAT, 'YYYY-MM-dd') = " +
            "(SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) ORDER BY ID DESC ", nativeQuery = true)
    Long cbsTransactionExists(String rtgsReferenceNumber);

}
