package com.cbl.cityrtgs.repositories.transaction;

import com.cbl.cityrtgs.models.entitymodels.transaction.notification.TransactionNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionNotificationRepository extends JpaRepository<TransactionNotificationEntity, Long> {

}
