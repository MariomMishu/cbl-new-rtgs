package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.dto.configuration.transactionpriority.TransactionPriorityType;
import com.cbl.cityrtgs.models.entitymodels.configuration.PriorityCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriorityCodeRepository extends JpaRepository<PriorityCodeEntity, Long> {
    List<PriorityCodeEntity> findAllByTypeAndIsDeletedFalse(TransactionPriorityType type);


    Optional<PriorityCodeEntity> findByCodeAndIsDeletedFalse(String code);

}
