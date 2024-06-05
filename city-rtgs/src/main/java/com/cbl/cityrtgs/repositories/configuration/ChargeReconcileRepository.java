package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.entitymodels.configuration.ChargeReconcileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChargeReconcileRepository
        extends JpaRepository<ChargeReconcileEntity, Long>, JpaSpecificationExecutor<ChargeReconcileEntity> {
    Page<ChargeReconcileEntity> findAllByIsDeletedFalse(Pageable pageable);

    Optional<ChargeReconcileEntity> findByIdAndIsDeletedFalse(Long id);

    List<ChargeReconcileEntity> findAllByIsDeletedFalse();
}
