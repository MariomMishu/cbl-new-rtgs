package com.cbl.cityrtgs.repositories.message;

import com.cbl.cityrtgs.models.dto.message.MessageProcessStatus;
import com.cbl.cityrtgs.models.entitymodels.messagelog.MsgLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MsgLogRepository extends JpaRepository<MsgLogEntity, Long>, JpaSpecificationExecutor<MsgLogEntity> {

    Page<MsgLogEntity> findAllByIsDeletedFalse(Pageable pageable);

    Optional<MsgLogEntity> findByIdAndIsDeletedFalse(Long id);
   Boolean existsByIdAndProcessStatus(Long id, String status);

    List<MsgLogEntity> findAllByIsDeletedFalse();

    Boolean existsByBusinessMessageId(String businessMsgId);

    @Query(value = "SELECT * FROM TBL_RTGS_MESSAGE_LOG " +
            "WHERE PROCESSSTATUS = 'QUEUED' " +
            "AND MESSAGEDIRECTIONS = 'INWARD' " +
            "AND MESSAGETYPE IS NOT NULL " +
            "AND MXMESSAGE IS NOT NULL " +
            "AND TO_CHAR(MSGDATE, 'YYYY-MM-dd') = (SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) ORDER BY ID ASC", nativeQuery = true)
    List<MsgLogEntity> findAllByProcessStatusQueued();
    @Query(value = "SELECT * FROM TBL_RTGS_MESSAGE_LOG " +
            "WHERE PROCESSSTATUS = 'ARRIVED' " +
            "AND MESSAGEDIRECTIONS = 'INWARD' " +
            "AND MXMESSAGE LIKE :txnReference " +
            "AND MESSAGETYPE = 'camt.054.001.04' " +
            "AND MESSAGETYPE IS NOT NULL " +
            "AND MXMESSAGE IS NOT NULL " +
            "AND TO_CHAR(MSGDATE, 'YYYY-MM-dd') = (SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) ORDER BY ID ASC", nativeQuery = true)
    Optional<MsgLogEntity>  findByTxnReferenceAndStatusArrived(String txnReference);
    //TRUNC(MSGDATE) = TO_DATE('04/21/2024', 'MM/DD/YYYY')

    @Modifying
    @Query("UPDATE MsgLogEntity M SET M.processStatus = :processStatus WHERE M.id = :id")
    void updateMsgLogEntityStatus(long id, String processStatus);

    @Query(value = "SELECT * FROM TBL_RTGS_MESSAGE_LOG " +
            "WHERE TO_CHAR(MSGDATE, 'YYYY-MM-dd') = (SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) " +
            "AND MESSAGETYPE = 'pacs.009.001.04'", nativeQuery = true)
    List<MsgLogEntity> getBangladeshBankOpeningForToday();
    @Query(value = "SELECT * FROM TBL_RTGS_MESSAGE_LOG " +
            "WHERE id = :id " +
            "AND TO_CHAR(MSGDATE, 'YYYY-MM-dd') = (SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) ORDER BY ID ASC", nativeQuery = true)
    Optional<MsgLogEntity> getLogById(long id);

    @Query(value = "SELECT COUNT(*) FROM TBL_RTGS_MESSAGE_LOG WHERE " +
            "PROCESSSTATUS = 'UNPROCESSED' " +
            "AND TRUNC(MSGDATE) = :date", nativeQuery = true)
    long countUnprocessed(LocalDate date);

    @Query(value = "SELECT * FROM TBL_RTGS_MESSAGE_LOG " +
            "WHERE PROCESSSTATUS = 'UNPROCESSED' " +
            "AND MESSAGEDIRECTIONS = 'OUTWARD' " +
            "AND MESSAGETYPE IS NOT NULL " +
            "AND MXMESSAGE IS NOT NULL " +
            "AND TO_CHAR(MSGDATE, 'YYYY-MM-dd') = (SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) ORDER BY ID ASC", nativeQuery = true)
    List<MsgLogEntity> findAllByProcessStatusUnProcessed();
}
