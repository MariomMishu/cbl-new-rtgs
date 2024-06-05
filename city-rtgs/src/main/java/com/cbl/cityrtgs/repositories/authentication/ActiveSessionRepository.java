package com.cbl.cityrtgs.repositories.authentication;

import com.cbl.cityrtgs.models.entitymodels.authentication.ActiveSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface ActiveSessionRepository extends JpaRepository<ActiveSession, Long>, PagingAndSortingRepository<ActiveSession, Long> {

    @Query(value = "SELECT COUNT(*) FROM TBL_ACTIVE_SESSIONS WHERE " +
            "USER_ID = :userId " +
            "AND IS_ACTIVE = 1 " +
            "AND IS_DELETED = 0 " +
            "AND CAST(SESSION_END_TIME AS DATE) > SYSDATE", nativeQuery = true)
    long isUserSessionActive(Long userId);

    @Query(value = "SELECT * FROM TBL_ACTIVE_SESSIONS WHERE " +
            "USER_ID = :userId " +
            "AND IS_ACTIVE = 1 " +
            "AND IS_DELETED = 0 " +
            "AND CAST(SESSION_END_TIME AS DATE) > SYSDATE", nativeQuery = true)
    ActiveSession findActiveUser(Long userId);

    @Query(value = "SELECT COUNT(*) FROM TBL_ACTIVE_SESSIONS " +
            "WHERE TOKEN = :accessToken " +
            "AND CAST(SESSION_END_TIME AS DATE) > SYSDATE " +
            "AND IS_ACTIVE = 1", nativeQuery = true)
    long isSessionValid(String accessToken);

    @Query(value = "SELECT * FROM TBL_ACTIVE_SESSIONS WHERE TOKEN = :accessToken", nativeQuery = true)
    ActiveSession findByAccessToken(String accessToken);

    @Query(value = "SELECT * FROM TBL_ACTIVE_SESSIONS WHERE " +
            "IS_ACTIVE = 1 " +
            "AND IS_DELETED = 0 ORDER BY ID DESC", nativeQuery = true)
    Page<ActiveSession> findAllActiveSessions(Pageable pageable);
}
