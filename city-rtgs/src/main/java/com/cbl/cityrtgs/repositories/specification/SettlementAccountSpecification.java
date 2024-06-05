package com.cbl.cityrtgs.repositories.specification;

import com.cbl.cityrtgs.models.dto.configuration.settlementaccount.SettlementAccountFilter;
import com.cbl.cityrtgs.models.entitymodels.configuration.SettlementAccountEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class SettlementAccountSpecification {

    public static Specification<SettlementAccountEntity> all(SettlementAccountFilter filter) {
        //  SettlementAccountEntity settlementAccountEntity = new SettlementAccountEntity();

        Specification<SettlementAccountEntity> specification = SpecificationBuilder.conjunction();
        specification = specification.and(SpecificationBuilder.equal("isDeleted", false));

        if (StringUtils.isNotBlank(filter.getName())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal("name", filter.getName()));
        }

        if (StringUtils.isNotBlank(filter.getCode())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal("code", filter.getCode()));
        }

//        if (StringUtils.isNotBlank(filter.getName())) {
//            specification =
//                    Objects.requireNonNull(specification)
//                            .and(
//                                    SpecificationBuilder.equal(
//                                            SettlementAccountEntity_., filter.getName()));
//        }
//
//        if (StringUtils.isNotBlank(filter.getCode())) {
//            specification =
//                    Objects.requireNonNull(specification)
//                            .and(SpecificationBuilder.equal(settlementAccountEntity.getCode(), filter.getCode()));
//        }

        return specification;
    }
}