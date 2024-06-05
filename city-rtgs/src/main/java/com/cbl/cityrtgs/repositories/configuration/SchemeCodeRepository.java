package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.dto.configuration.schemecode.ConfigurationKey;
import com.cbl.cityrtgs.models.entitymodels.configuration.SchemeCodeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchemeCodeRepository

        extends JpaRepository<SchemeCodeEntity, Long> {

    Page<SchemeCodeEntity> findAllByIsDeletedFalse(Pageable pageable);

    List<SchemeCodeEntity> findAllByIsDeletedFalse();

    Optional<SchemeCodeEntity> findByIdAndIsDeletedFalse(Long id);

    Optional<SchemeCodeEntity> findByConfigurationKeyAndIsDeletedFalse(ConfigurationKey configurationKey);
}
