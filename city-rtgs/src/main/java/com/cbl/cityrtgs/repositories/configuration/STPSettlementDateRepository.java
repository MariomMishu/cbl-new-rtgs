package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.entitymodels.configuration.StpSettlementDateConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface STPSettlementDateRepository
        extends JpaRepository<StpSettlementDateConfig, Long>, JpaSpecificationExecutor<StpSettlementDateConfig> {

    Optional<StpSettlementDateConfig> findById(Long id);

    List<StpSettlementDateConfig> findAll();
}
