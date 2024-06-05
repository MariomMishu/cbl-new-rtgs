package com.cbl.cityrtgs.services.transaction;

import com.cbl.cityrtgs.models.entitymodels.transaction.notification.TransactionNotificationEntity;
import com.cbl.cityrtgs.repositories.transaction.TransactionNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionNotificationService {

    private final TransactionNotificationRepository repository;

    public void save(TransactionNotificationEntity notification) {
        if (notification.getId() == 0L) {
            repository.save(notification);
        } else {
            repository.save(notification);
        }
    }

}
