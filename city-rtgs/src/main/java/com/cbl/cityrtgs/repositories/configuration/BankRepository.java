package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.entitymodels.configuration.BankEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<BankEntity, Long>, JpaSpecificationExecutor<BankEntity> {
    Boolean existsByName(String name);

    Boolean existsByNameOrBicOrBankCode(String name, String bic, String bankCode);

    Page<BankEntity> findAllByIsDeletedFalse(Pageable pageable);

    List<BankEntity> findAllByIsDeletedFalse();

    Optional<BankEntity> findByIdAndIsDeletedFalse(Long id);

    Optional<BankEntity> findByIsOwnerBankTrueAndIsDeletedFalse();

    Optional<BankEntity> findBySettlementBankTrueAndIsDeletedFalse();

    List<BankEntity> findAllByIsOwnerBankFalseAndIsDeletedFalse();

    Optional<BankEntity> findByBicAndIsDeletedFalse(String bic);

}
