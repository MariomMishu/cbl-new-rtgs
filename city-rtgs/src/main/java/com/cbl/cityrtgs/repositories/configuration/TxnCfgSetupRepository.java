package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.entitymodels.configuration.TxnCfgSetupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TxnCfgSetupRepository extends JpaRepository<TxnCfgSetupEntity, Long> {

    Page<TxnCfgSetupEntity> findAllByIsDeletedFalse(Pageable pageable);

    List<TxnCfgSetupEntity> findAll();

    Optional<TxnCfgSetupEntity> findByIdAndIsDeletedFalse(Long id);

    List<TxnCfgSetupEntity> findByCurrencyIdAndTxnActiveTrueAndIsDeletedFalse(Long currencyId);

    Optional<TxnCfgSetupEntity> findByCurrencyIdAndIsDeletedFalse(Long currencyId);

    List<TxnCfgSetupEntity> findAllByIsDeletedFalse();
}