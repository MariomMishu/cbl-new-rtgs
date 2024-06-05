package com.cbl.cityrtgs.repositories.specification;

import com.cbl.cityrtgs.models.dto.configuration.bank.BankFilter;
import com.cbl.cityrtgs.models.entitymodels.configuration.BankEntity;

import com.cbl.cityrtgs.models.entitymodels.configuration.BankEntity_;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class BankSpecification {

    public static Specification<BankEntity> all(BankFilter filter) {

        Specification<BankEntity> specification = SpecificationBuilder.conjunction();

        specification = specification.and(SpecificationBuilder.equal("isDeleted", false));

        if (StringUtils.isNotBlank(filter.getName())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(BankEntity_.NAME, filter.getName()));
        }
        if (StringUtils.isNotBlank(filter.getBic())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(BankEntity_.BIC, filter.getBic()));
        }
        if (StringUtils.isNotBlank(filter.getBankCode())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(BankEntity_.BANK_CODE, filter.getBankCode()));
        }
        if (StringUtils.isNotBlank(filter.getAddress())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(BankEntity_.ADDRESS1, filter.getAddress()));
        }
        return specification;
    }
}