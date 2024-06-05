package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.entitymodels.configuration.RoutingNumberConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoutingNumberConfigurationRepository extends JpaRepository<RoutingNumberConfigurationEntity, Long> {
    List<RoutingNumberConfigurationEntity> findByIsDeletedFalse();
}
