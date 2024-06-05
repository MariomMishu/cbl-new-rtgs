package com.cbl.cityrtgs.repositories.transaction.notification;

import com.cbl.cityrtgs.models.dto.report.ErrorReport;
import com.cbl.cityrtgs.models.entitymodels.transaction.FailedTxnNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface FailedTxnNotificationRepository extends JpaRepository<FailedTxnNotificationEntity, Long> {

    @Query(value = "SELECT * " +
            "FROM TBL_FAILED_TXN_NOTIFICATION " +
            "WHERE " +
            "TRUNC(ERRORDATE) >= :fromDate " +
            "AND TRUNC(ERRORDATE) <= :toDate " +
            "ORDER BY ERRORDATE DESC ", nativeQuery = true)
    List<ErrorReport> getErrorReport(LocalDate fromDate, LocalDate toDate);

}
