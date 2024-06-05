package com.cbl.cityrtgs.repositories.specification;

import com.cbl.cityrtgs.models.dto.configuration.exemptionsetup.ExemptionSetupFilter;
import com.cbl.cityrtgs.models.entitymodels.configuration.ExemptionChargeSetupEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class ExemptionSetupSpecification {

    public static Specification<ExemptionChargeSetupEntity> all(ExemptionSetupFilter filter) {

        Specification<ExemptionChargeSetupEntity> specification = SpecificationBuilder.conjunction();

        specification = specification.and(SpecificationBuilder.equal("isDeleted", false));

        if (StringUtils.isNotBlank(filter.getAccountName())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like("accountName", filter.getAccountName()));
        }
        if (StringUtils.isNotBlank(filter.getAccountNumber())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like("accountCode", filter.getAccountNumber()));
        }

        return specification;
    }
}