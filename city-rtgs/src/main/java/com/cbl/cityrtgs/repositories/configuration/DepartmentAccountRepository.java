package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentAccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentAccountRepository
        extends JpaRepository<DepartmentAccountEntity, Long>, JpaSpecificationExecutor<DepartmentAccountEntity> {
    Page<DepartmentAccountEntity> findAllByIsDeletedFalse(Pageable pageable);
    List<DepartmentAccountEntity> findAllByIsDeletedFalse();
    Optional<DepartmentAccountEntity> findByIdAndIsDeletedFalse(Long id);
    Optional<DepartmentAccountEntity> findByDeptIdAndCurrencyIdAndRoutingTypeAndIsDeletedFalse(Long deptId, Long currencyId, RoutingType routingType);
    Boolean existsByCurrencyIdAndReconcileIsTrue(Long currencyId);

}
