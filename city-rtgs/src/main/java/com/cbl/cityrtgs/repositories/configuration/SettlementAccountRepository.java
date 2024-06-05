package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.entitymodels.configuration.SettlementAccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface SettlementAccountRepository
        extends JpaRepository<SettlementAccountEntity, Long>, JpaSpecificationExecutor<SettlementAccountEntity> {

    Page<SettlementAccountEntity> findAll(Pageable pageable);

    Boolean existsByCode(String code);

    Optional<SettlementAccountEntity> findByCurrencyIdAndIsDeletedFalse(Long currencyId);

    List<SettlementAccountEntity> findAllByIsDeletedFalse();

    Optional<SettlementAccountEntity> findByCodeAndIsDeletedFalse(String code);

}
