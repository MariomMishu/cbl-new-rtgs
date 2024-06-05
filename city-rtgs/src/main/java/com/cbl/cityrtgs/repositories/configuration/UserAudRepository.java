package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.entitymodels.user.UserAudEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UserAudRepository extends JpaRepository<UserAudEntity, Long> {

    Page<UserAudEntity> findAllByIsDeletedFalse(Pageable pageable);

    Boolean existsByUsername(String username);

    Optional<UserAudEntity> findByIdAndIsDeletedFalse(Long id);
}
