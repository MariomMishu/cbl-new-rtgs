package com.cbl.cityrtgs.repositories.specification;

import com.cbl.cityrtgs.models.dto.configuration.branch.BranchFilter;
import com.cbl.cityrtgs.models.entitymodels.configuration.BankEntity_;
import com.cbl.cityrtgs.models.entitymodels.configuration.BranchEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.BranchEntity_;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class BranchSpecification {

    public static Specification<BranchEntity> all(BranchFilter filter) {

        Specification<BranchEntity> specification = SpecificationBuilder.conjunction();

        specification = specification.and(SpecificationBuilder.equal("isDeleted", false));

        if (StringUtils.isNotBlank(filter.getName())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(BranchEntity_.NAME, filter.getName()));
        }

        if (StringUtils.isNotBlank(filter.getAddress())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(BranchEntity_.ADDRESS1, filter.getAddress()));
        }

        if (StringUtils.isNotBlank(filter.getRoutingNumber())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(BranchEntity_.ROUTING_NUMBER, filter.getRoutingNumber()));
        }

        if (Objects.nonNull(filter.getBankId())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal(BranchEntity_.BANK, BankEntity_.ID, filter.getBankId()));
        }

        return specification;
    }
}