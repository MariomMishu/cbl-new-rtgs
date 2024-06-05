package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.entitymodels.configuration.RightsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RightsRepository extends JpaRepository<RightsEntity, Long> {
    Page<RightsEntity> findAllByIsDeletedFalse(Pageable pageable);

    Boolean existsByName(String name);

    Optional<RightsEntity> findByIdAndIsDeletedFalse(Long id);

    List<RightsEntity> findAllByIsDeletedFalse();


}
