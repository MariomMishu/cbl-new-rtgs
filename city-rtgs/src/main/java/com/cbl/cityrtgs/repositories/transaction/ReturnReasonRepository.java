package com.cbl.cityrtgs.repositories.transaction;

import com.cbl.cityrtgs.models.entitymodels.transaction.ReturnReasonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ReturnReasonRepository extends JpaRepository<ReturnReasonEntity, Long> {
    Page<ReturnReasonEntity> findAllByIsDeletedFalse(Pageable pageable);
    Optional<ReturnReasonEntity> findByIdAndIsDeletedFalse(Long id);
    List<ReturnReasonEntity> findAllByIsDeletedFalse();
    Optional<ReturnReasonEntity> findByCodeAndIsDeletedFalse(String code);
}
