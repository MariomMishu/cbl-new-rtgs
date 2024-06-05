package com.cbl.cityrtgs.repositories.specification;

import com.cbl.cityrtgs.models.dto.configuration.accounttype.AccountTypeFilter;
import com.cbl.cityrtgs.models.entitymodels.configuration.AccountTypeEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class AccountTypeSpecification {

    public static Specification<AccountTypeEntity> all(AccountTypeFilter filter) {

        Specification<AccountTypeEntity> specification = SpecificationBuilder.conjunction();

        specification = specification.and(SpecificationBuilder.equal("isDeleted", false));

        if (StringUtils.isNotBlank(filter.getCode())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like("code", filter.getCode()));
        }
        if (StringUtils.isNotBlank(filter.getCbsAccountNumber())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like("cbsAccountNumber", filter.getCbsAccountNumber()));
        }

        if (Objects.nonNull(filter.getCbsAccountType())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal("cbsAccountType", filter.getCbsAccountType()));
        }
        if (Objects.nonNull(filter.getCbsName())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal("cbsName", filter.getCbsName()));
        }
        if (Objects.nonNull(filter.getAccountingType())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal("accountingType", filter
                    .getAccountingType()));
        }
        return specification;
    }
}