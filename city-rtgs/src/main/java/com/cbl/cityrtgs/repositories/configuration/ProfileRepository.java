package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.entitymodels.configuration.ProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProfileRepository
        extends JpaRepository<ProfileEntity, Long>, JpaSpecificationExecutor<ProfileEntity> {
    Page<ProfileEntity> findAllByIsDeletedFalse(Pageable pageable);

    Boolean existsByNameIgnoreCase(String name);

    Optional<ProfileEntity> findByIdAndIsDeletedFalse(Long id);

    Page<ProfileEntity>  findByNameAndIsDeletedFalse(Pageable pageable,String name);

    List<ProfileEntity> findAllByIsDeletedFalse();
}
