package com.cbl.cityrtgs.repositories.message;


import com.cbl.cityrtgs.models.entitymodels.messagelog.InOutMsgLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface InOutMsgLogRepository extends JpaRepository<InOutMsgLogEntity, Long> {

    Page<InOutMsgLogEntity> findAllByIsDeletedFalse(Pageable pageable);

    Optional<InOutMsgLogEntity> findByIdAndIsDeletedFalse(Long id);

    List<InOutMsgLogEntity> findAllByIsDeletedFalse();

    Optional<InOutMsgLogEntity> findByMsgIdAndRouteTypeAndIsDeletedFalse(String referenceNumber, String routingType);

    Boolean existsByTxnReferenceNumberAndRouteType(String ref, String routingType);

    @Query(value = "SELECT * FROM TBL_INOUTMSGLOG " +
            "WHERE ROUTETYPE = :routingType " +
            "AND TXNREFERENCENUMBER = :txnReference " +
            "AND TO_CHAR(MSGCREATIONDATE, 'YYYY-MM-dd') = (SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL)", nativeQuery = true)
    Optional<InOutMsgLogEntity> getInOutMsgLogByTxnRefAndDate(String txnReference, String routingType);

    @Query(value = "SELECT * FROM TBL_INOUTMSGLOG " +
            "WHERE ROUTETYPE = 'Outgoing' " +
            "AND TXNREFERENCENUMBER = :txnReference " +
            "AND TO_CHAR(MSGCREATIONDATE, 'YYYY-MM-dd') = (SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL)", nativeQuery = true)
    Optional<InOutMsgLogEntity> getInOutMsgLogByTxnRefAndDateAndRouTypeOutgoing(String txnReference);

    @Query(value = "SELECT * FROM TBL_INOUTMSGLOG " +
            "WHERE ROUTETYPE = 'Incoming' " +
            "AND TXNREFERENCENUMBER = :txnReference " +
            "AND TO_CHAR(MSGCREATIONDATE, 'YYYY-MM-dd') = (SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL)", nativeQuery = true)
    Optional<InOutMsgLogEntity> getInOutMsgLogByTxnRefAndDateAndRouTypeIncoming(String txnReference);

    @Query(value = "SELECT * FROM TBL_INOUTMSGLOG " +
            "WHERE ROUTETYPE = 'Incoming' " +
            "AND RESPONSEMSGID = :responseMsgId " +
            "AND TO_CHAR(MSGCREATIONDATE, 'YYYY-MM-dd') = (SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL)", nativeQuery = true)
    Optional<InOutMsgLogEntity> getInOutMsgLogByMsgIdAndDateAndRouTypeIncoming(String responseMsgId);
}
