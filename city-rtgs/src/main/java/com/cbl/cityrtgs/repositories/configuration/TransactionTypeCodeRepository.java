package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.entitymodels.configuration.TransactionTypeCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionTypeCodeRepository extends JpaRepository<TransactionTypeCodeEntity, Long> {
    List<TransactionTypeCodeEntity> findAllByStatusIsTrueAndApiActivitiesStatusIsTrue();
    List<TransactionTypeCodeEntity> findAllByStatusIsTrueAndApiActivitiesStatusIsTrueAndSiTransactionIsTrue();

    Optional<TransactionTypeCodeEntity> findByIdAndIsDeletedFalse(Long id);

    List<TransactionTypeCodeEntity> findByCodeAndIsDeletedFalse(String code);
    List<TransactionTypeCodeEntity> findByCodeAndMessageTypeAndIsDeletedFalse(String code, String messageType);
    List<TransactionTypeCodeEntity> findByCodeAndMessageTypeAndDeliveryChannelAndIsDeletedFalse(String code, String messageType, String deliveryChannel);
}
