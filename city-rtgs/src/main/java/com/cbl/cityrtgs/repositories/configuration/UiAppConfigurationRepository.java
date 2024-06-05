package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.entitymodels.configuration.UiAppConfigurationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UiAppConfigurationRepository extends JpaRepository<UiAppConfigurationEntity, Long> {
    Page<UiAppConfigurationEntity> findAllByIsDeletedFalse(Pageable pageable);

    Optional<UiAppConfigurationEntity> findByIdAndIsDeletedFalse(Long id);

    List<UiAppConfigurationEntity> findAllByIsDeletedFalse();
}
