package com.cbl.cityrtgs.repositories.transaction.notification;

import com.cbl.cityrtgs.models.entitymodels.transaction.notification.StatusTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface StatusTransactionRepository extends JpaRepository<StatusTransactionEntity, Long> {

}
