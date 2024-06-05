package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.entitymodels.configuration.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
@Transactional
public interface RolesRepository extends JpaRepository<RoleEntity, Long>, JpaSpecificationExecutor<RoleEntity> {
    Page<RoleEntity> findAllByIsDeletedFalse(Pageable pageable);

    Boolean existsByName(String name);

    Optional<RoleEntity> findByIdAndIsDeletedFalse(Long id);

    List<RoleEntity> findAllByIdInAndIsDeletedFalse(
            Set<Long> id);

    List<RoleEntity> findAllByIsDeletedFalse();
}
