package com.cbl.cityrtgs.repositories.message;

import com.cbl.cityrtgs.models.entitymodels.messagelog.MsgLogAudEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MsgLogAudRepository extends JpaRepository<MsgLogAudEntity, Long> {
    Page<MsgLogAudEntity> findAllByIsDeletedFalse(Pageable pageable);

    Optional<MsgLogAudEntity> findByIdAndIsDeletedFalse(Long id);

    List<MsgLogAudEntity> findAllByIsDeletedFalse();
}
