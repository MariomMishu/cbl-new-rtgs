package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.entitymodels.configuration.InwardTransactionConfigurationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InwardTransactionConfigurationRepository
        extends JpaRepository<InwardTransactionConfigurationEntity, Long>, JpaSpecificationExecutor<InwardTransactionConfigurationEntity> {
    Page<InwardTransactionConfigurationEntity> findAllByIsDeletedFalse(Pageable pageable);
    List<InwardTransactionConfigurationEntity> findAllByIsDeletedFalse();

    Optional<InwardTransactionConfigurationEntity> findByIdAndIsDeletedFalse(Long id);
}
