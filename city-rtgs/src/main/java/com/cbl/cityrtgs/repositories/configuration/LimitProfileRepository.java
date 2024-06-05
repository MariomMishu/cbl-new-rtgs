package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.entitymodels.configuration.LimitProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LimitProfileRepository
        extends JpaRepository<LimitProfileEntity, Long>, JpaSpecificationExecutor<LimitProfileEntity> {

    Page<LimitProfileEntity> findAllByIsDeletedFalse(Pageable pageable);

    Boolean existsByCurrencyIdAndProfileId(Long currency, Long profileId);

    Optional<LimitProfileEntity> findByIdAndIsDeletedFalse(Long id);

    Optional<LimitProfileEntity> findByProfileIdAndIsDeletedFalse(Long profileId);

    Optional<LimitProfileEntity> findByCurrencyIdAndProfileId(Long currency, Long profileId);

    Page<LimitProfileEntity> findAllByProfileIdAndIsDeletedFalse(Long profileId, Pageable pageable);
}
