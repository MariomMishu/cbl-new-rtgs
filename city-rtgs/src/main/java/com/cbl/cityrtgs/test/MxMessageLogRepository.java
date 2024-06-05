package com.cbl.cityrtgs.test;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MxMessageLogRepository extends JpaRepository<MxMessageLog, Long>, JpaSpecificationExecutor<MxMessageLog> {

    @Query(value = "SELECT * FROM RTGS_MESSAGE_LOG " +
            "WHERE PROCESSSTATUS IN ('UNPROCESSED') " +
            "AND MESSAGEDIRECTIONS = 'INWARD' " +
            //   "AND MESSAGETYPE IS NOT NULL " +
            //    "AND MXMESSAGE IS NOT NULL " +
            "AND TRUNC(MSGDATE, 'YYYY-MM-dd') = (SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) ORDER BY ID ASC", nativeQuery = true)
        // "AND TRUNC (MSGDATE) = TO_DATE ('5/5/2024', 'MM/DD/YYYY') ORDER BY ID ASC", nativeQuery = true)
    List<MxMessageLog> findAllByProcessingStatusQueued();

    @Query(value = "SELECT * FROM RTGS_MESSAGE_LOG " +
            "WHERE PROCESSSTATUS = 'QUEUED' " +
            "AND MESSAGEDIRECTIONS = 'INWARD' " +
            //   "AND MESSAGETYPE IS NOT NULL " +
            //    "AND MXMESSAGE IS NOT NULL " +
            //  "AND TO_CHAR(MSGDATE, 'YYYY-MM-dd') = (SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) ORDER BY ID ASC", nativeQuery = true)
            "AND TRUNC (MSGDATE) = TO_DATE ('5/21/2024', 'MM/DD/YYYY') ORDER BY ID ASC", nativeQuery = true)
    List<MxMessageLog> findAllByProcessStatusQueued();


    @Query(value = "UPDATE RTGS_MESSAGE_LOG " +
            "SET PROCESSSTATUS = 'PROCESSED' " +
            "WHERE ID =:id " +
            "AND MESSAGEDIRECTIONS = 'INWARD' " +
            "AND TO_CHAR (MSGDATE, 'YYYY-MM-dd') = " +
            "(SELECT TO_CHAR (SYSDATE, 'YYYY-MM-dd') FROM DUAL) ", nativeQuery = true)
    void updateStatus(Long id);
}
