package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository
        extends JpaRepository<DepartmentEntity, Long>, JpaSpecificationExecutor<DepartmentEntity> {
    Boolean existsByName(String name);

    Page<DepartmentEntity> findAllByIsDeletedFalse(Pageable pageable);

    List<DepartmentEntity> findAllByIsDeletedFalse();

    Optional<DepartmentEntity> findByIdAndIsDeletedFalse(Long id);

    Optional<DepartmentEntity> findByNameAndIsDeletedFalse(String name);

    @Query(value = "SELECT * FROM TBL_DEPARTMENT " +
            "WHERE ID = :id ", nativeQuery = true)
    DepartmentEntity getById(Long id);
}
