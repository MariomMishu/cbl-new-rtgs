package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.entitymodels.configuration.ChargeReconcileEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.ChargeSetupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChargeSetupRepository
        extends JpaRepository<ChargeSetupEntity, Long>, JpaSpecificationExecutor<ChargeSetupEntity> {

    Page<ChargeSetupEntity> findAllByIsDeletedFalse(Pageable pageable);

    Optional<ChargeSetupEntity> findByIdAndIsDeletedFalse(Long id);

    List<ChargeReconcileEntity> findAllByIsDeletedFalse();

    Optional<ChargeSetupEntity> findByCurrencyIdAndIsDeletedFalse(Long currencyId);

    Optional<ChargeSetupEntity> findFirstByCurrencyIdAndIsDeletedFalseOrderByIdDesc(Long currencyId);

    Optional<ChargeSetupEntity> findByCurrencyIdAndFromAmountLessThanEqualAndToAmountGreaterThanEqualAndIsDeletedFalseAndStatusIsTrue(Long currencyId, BigDecimal amount1, BigDecimal amount2);

    List<ChargeSetupEntity> findAllByStatus(boolean status);


}
