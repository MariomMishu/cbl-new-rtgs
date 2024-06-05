package com.cbl.cityrtgs.repositories.transaction;

import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.entitymodels.transaction.CbsTxnReversalLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TxnReversalLogRepository extends JpaRepository<CbsTxnReversalLogEntity, Long> {
    Boolean existsByReferenceNumberAndRoutingType(String refNumber, RoutingType routingType);
}
