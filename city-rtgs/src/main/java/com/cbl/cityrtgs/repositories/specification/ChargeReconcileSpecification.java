package com.cbl.cityrtgs.repositories.specification;

import com.cbl.cityrtgs.models.dto.configuration.chargereconcilesetup.ChargeReconcileFilter;
import com.cbl.cityrtgs.models.entitymodels.configuration.ChargeReconcileEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class ChargeReconcileSpecification {

    public static Specification<ChargeReconcileEntity> all(ChargeReconcileFilter filter) {

        Specification<ChargeReconcileEntity> specification = SpecificationBuilder.conjunction();

        specification = specification.and(SpecificationBuilder.equal("isDeleted", false));

        if (StringUtils.isNotBlank(filter.getCurrencyName())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like("currencyName", filter.getCurrencyName()));
        }
        if (StringUtils.isNotBlank(filter.getAccountNumber())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like("accountNo", filter.getAccountNumber()));
        }
        if (Objects.nonNull(filter.getChargeType())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal("chargeType", filter.getChargeType()));
        }
        if (Objects.nonNull(filter.getChargeModule())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal("chargeModule", filter.getChargeModule()));
        }

        if (Objects.nonNull(filter.getCurrencyId())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal("currencyId", filter.getCurrencyId()));
        }
        return specification;
    }
}