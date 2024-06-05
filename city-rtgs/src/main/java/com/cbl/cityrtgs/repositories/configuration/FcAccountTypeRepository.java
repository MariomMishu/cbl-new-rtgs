package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.entitymodels.configuration.FcAccountTypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FcAccountTypeRepository
        extends JpaRepository<FcAccountTypeEntity, Long>, JpaSpecificationExecutor<FcAccountTypeEntity> {

    Page<FcAccountTypeEntity> findAllByIsDeletedFalse(Pageable pageable);

    Boolean existsByName(String name);

    Optional<FcAccountTypeEntity> findByIdAndIsDeletedFalse(Long id);

    List<FcAccountTypeEntity> findAllByIsDeletedFalse();
}
