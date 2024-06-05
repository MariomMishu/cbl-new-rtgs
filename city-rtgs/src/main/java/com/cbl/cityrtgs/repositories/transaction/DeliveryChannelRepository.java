package com.cbl.cityrtgs.repositories.transaction;

import com.cbl.cityrtgs.models.entitymodels.transaction.DeliveryChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryChannelRepository extends JpaRepository<DeliveryChannelEntity, Long> {
    List<DeliveryChannelEntity> findAllByIsDeletedFalse();
    Boolean existsByChannelName(String name);
    Optional<DeliveryChannelEntity> findByChannelNameAndIsDeletedFalse(String name);
    Optional<DeliveryChannelEntity> findByIdAndIsDeletedFalse(Long id);
}
