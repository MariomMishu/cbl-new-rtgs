package com.cbl.cityrtgs.repositories.transaction.c2c;


import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.InterCustomerFundTransferAudEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface InterCustomerFundTransferAudRepository extends JpaRepository<InterCustomerFundTransferAudEntity, Long> {
    Page<InterCustomerFundTransferAudEntity> findAllByIsDeletedFalse(Pageable pageable);
    Optional<InterCustomerFundTransferAudEntity> findByIdAndIsDeletedFalse(Long id);
    List<InterCustomerFundTransferAudEntity> findAllByIsDeletedFalse();
}
