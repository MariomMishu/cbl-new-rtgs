package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.entitymodels.configuration.ExemptionChargeSetupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExemptionChargeRepository
        extends JpaRepository<ExemptionChargeSetupEntity, Long>, JpaSpecificationExecutor<ExemptionChargeSetupEntity> {

    Page<ExemptionChargeSetupEntity> findAllByIsDeletedFalse(Pageable pageable);

    List<ExemptionChargeSetupEntity> findAllByIsDeletedFalse();

    Optional<ExemptionChargeSetupEntity> findByIdAndIsDeletedFalse(Long id);

    Boolean existsByAccountCodeAndIsDeletedFalse(String accountCode);
}
