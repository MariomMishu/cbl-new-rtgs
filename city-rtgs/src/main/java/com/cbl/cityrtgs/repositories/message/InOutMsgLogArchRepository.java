package com.cbl.cityrtgs.repositories.message;


import com.cbl.cityrtgs.models.entitymodels.messagelog.InOutMsgLogArchEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface InOutMsgLogArchRepository extends JpaRepository<InOutMsgLogArchEntity, Long> {
    Page<InOutMsgLogArchEntity> findAllByIsDeletedFalse(Pageable pageable);

    Optional<InOutMsgLogArchEntity> findByIdAndIsDeletedFalse(Long id);

    List<InOutMsgLogArchEntity> findAllByIsDeletedFalse();
}
