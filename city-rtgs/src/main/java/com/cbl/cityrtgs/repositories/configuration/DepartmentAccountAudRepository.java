package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentAccountAudEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentAccountAudRepository
        extends JpaRepository<DepartmentAccountAudEntity, Long>, JpaSpecificationExecutor<DepartmentAccountAudEntity> {
    Page<DepartmentAccountAudEntity> findAllByIsDeletedFalse(Pageable pageable);

    List<DepartmentAccountAudEntity> findAllByIsDeletedFalse();

    Optional<DepartmentAccountAudEntity> findByIdAndIsDeletedFalse(Long id);
}
