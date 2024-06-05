package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.dto.configuration.accounttype.CbsName;
import com.cbl.cityrtgs.models.entitymodels.configuration.AccountTypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountTypesRepository
        extends JpaRepository<AccountTypeEntity, Long>, JpaSpecificationExecutor<AccountTypeEntity> {
    Page<AccountTypeEntity> findAllByIsDeletedFalse(Pageable pageable);

    Boolean existsByCode(String code);

    Optional<AccountTypeEntity> findByIdAndIsDeletedFalse(Long id);

    Optional<AccountTypeEntity> findByRtgsAccountIdAndIsDeletedFalse(Long rtgsAccount);

    List<AccountTypeEntity> findAllByIsDeletedFalse();

    Optional<AccountTypeEntity> findByRtgsAccountIdAndCbsNameAndIsDeletedFalse(Long rtgsAccountId, CbsName cbsName);
}
