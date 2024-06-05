package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.entitymodels.configuration.BranchEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepository
        extends JpaRepository<BranchEntity, Long>, JpaSpecificationExecutor<BranchEntity> {

    Page<BranchEntity> findAllByIsDeletedFalse(Pageable pageable);

    List<BranchEntity> findAllByIsDeletedFalse();

    Optional<BranchEntity> findByIdAndIsDeletedFalse(Long id);

    Boolean existsByName(String name);

    Optional<BranchEntity> findByRoutingNumberAndIsDeletedFalse(String routingNumber);

    List<BranchEntity> findAllByBankIdAndIsDeletedFalse(Long bankId);

    Boolean existsByBankIdAndRoutingNumber(Long id, String routingNumber);
}
