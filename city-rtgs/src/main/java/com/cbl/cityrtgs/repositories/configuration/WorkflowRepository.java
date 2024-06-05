package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.entitymodels.configuration.WorkflowEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface WorkflowRepository extends JpaRepository<WorkflowEntity, Long> {
    Page<WorkflowEntity> findAllByIsDeletedFalse(Pageable pageable);

    Boolean existsByName(String name);

    Optional<WorkflowEntity> findByIdAndIsDeletedFalse(Long id);
}
