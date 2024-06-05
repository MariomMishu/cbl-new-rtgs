package com.cbl.cityrtgs.repositories.message;

import com.cbl.cityrtgs.models.entitymodels.messagelog.MsgLogArchEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MsgLogArchRepository extends JpaRepository<MsgLogArchEntity, Long> {
    Page<MsgLogArchEntity> findAllByIsDeletedFalse(Pageable pageable);

    Optional<MsgLogArchEntity> findByIdAndIsDeletedFalse(Long id);

    List<MsgLogArchEntity> findAllByIsDeletedFalse();
}
