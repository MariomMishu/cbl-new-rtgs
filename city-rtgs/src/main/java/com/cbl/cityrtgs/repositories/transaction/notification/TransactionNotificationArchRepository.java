package com.cbl.cityrtgs.repositories.transaction.notification;

import com.cbl.cityrtgs.models.entitymodels.transaction.notification.TransactionNotificationArchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TransactionNotificationArchRepository extends JpaRepository<TransactionNotificationArchEntity, Long> {

}
