package com.cbl.cityrtgs.repositories.transaction.b2b;


import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.InterBankTransferAudEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterBankFundTransferAudRepository extends JpaRepository<InterBankTransferAudEntity, Long> {
    Page<InterBankTransferAudEntity> findAllByIsDeletedFalse(Pageable pageable);
    Optional<InterBankTransferAudEntity> findByIdAndIsDeletedFalse(Long id);
    List<InterBankTransferAudEntity> findAllByIsDeletedFalse();
}
