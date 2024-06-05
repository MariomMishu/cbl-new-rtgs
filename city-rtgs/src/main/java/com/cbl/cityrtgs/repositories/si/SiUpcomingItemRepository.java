package com.cbl.cityrtgs.repositories.si;

import com.cbl.cityrtgs.models.dto.projection.SIProjection;
import com.cbl.cityrtgs.models.entitymodels.si.SiUpcomingItem;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SiUpcomingItemRepository extends PagingAndSortingRepository<SiUpcomingItem, Long> {

    // @Modifying
    @Query(value = "UPDATE TBL_SI_UPCOMING_ITEM " +
            "   SET EXECUTION_STATE = 'APPROVED', " +
            "       APPROVED_TIME = SYSDATE, " +
            "       APPROVER_ID = :approverId " +
            " WHERE id = :id ", nativeQuery = true)
    void approveSi(Long id, Long approverId);

    @Modifying
    @Query("UPDATE SiUpcomingItem SI SET SI.executionState = 'REJECTED', " +
            "SI.rejectionTime = :rejectionTime, " +
            "SI.rejecter = :rejecter, SI.rejectReason = :reason " +
            "WHERE SI.id = :id")
    void rejectSi(Long id, LocalDate rejectionTime, UserInfoEntity rejecter, String reason);

    @Modifying
    @Query("UPDATE SiUpcomingItem SI SET SI.executionState = 'CANCEL', " +
            "SI.cancelTime = :cancelTime, SI.canceller = :canceller WHERE SI.id = :id")
    void cancelSi(Long id, LocalDate cancelTime, UserInfoEntity canceller);

    @Modifying
    @Query("UPDATE SiUpcomingItem SI SET SI.isActive = true WHERE SI.id = :id")
    void activate(Long id);

    @Modifying
    @Query("UPDATE SiUpcomingItem SI SET SI.isActive = false WHERE SI.id = :id")
    void deactivate(Long id);

//    @Query(value = "SELECT * FROM TBL_SI_UPCOMING_ITEM P INNER JOIN TBL_SI_CONFIGURATION Q ON P.SI_CONFIGURATION_ID = Q.ID " +
//            "WHERE P.EXECUTION_STATE = 'APPROVED' " +
//            "AND P.IS_ACTIVE = 1 " +
//            "AND P.IS_FIRED = 0 " +
//            "AND TRUNC(P.DEFERRED_DATE) = TRUNC(SYSDATE) " +
//            "AND TRUNC(P.DEFERRED_DATE) BETWEEN TRUNC(Q.START_DATE) AND TRUNC(Q.EXPIRY_DATE)", nativeQuery = true)
//    List<SiUpcomingItem> findAllCandidateSIsForToday();

    @Query(value = "SELECT P.ID AS siUpcomId, Q.ID AS siConfigId " +
            " FROM TBL_SI_UPCOMING_ITEM  P " +
            " INNER JOIN TBL_SI_CONFIGURATION Q ON P.SI_CONFIGURATION_ID = Q.ID " +
            " WHERE P.EXECUTION_STATE = 'APPROVED' " +
            " AND P.IS_ACTIVE = 1 " +
            " AND P.IS_FIRED = 0 " +
            " AND TRUNC(P.DEFERRED_DATE) = TRUNC(SYSDATE) " +
            " AND TRUNC(P.DEFERRED_DATE) BETWEEN TRUNC(Q.START_DATE) AND TRUNC(Q.EXPIRY_DATE)", nativeQuery = true)
    List<SIProjection> findAllCandidateSIsForToday();

    Page<SiUpcomingItem> findAll(Specification<SiUpcomingItem> specification, Pageable pageable);
}
