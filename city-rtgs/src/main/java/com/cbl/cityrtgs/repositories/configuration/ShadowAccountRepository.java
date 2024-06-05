package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.entitymodels.configuration.ShadowAccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ShadowAccountRepository
        extends JpaRepository<ShadowAccountEntity, Long>, JpaSpecificationExecutor<ShadowAccountEntity> {
    Page<ShadowAccountEntity> findAllByIsDeletedFalse(Pageable pageable);
    List<ShadowAccountEntity> findAllByIsDeletedFalse();

    Boolean existsByRtgsSettlementAccount(String code);

    Optional<ShadowAccountEntity> findByIdAndIsDeletedFalse(Long id);

    Optional<ShadowAccountEntity> findBybankIdAndCurrencyIdAndIsDeletedFalse(Long bankId, Long currencyId);
}
