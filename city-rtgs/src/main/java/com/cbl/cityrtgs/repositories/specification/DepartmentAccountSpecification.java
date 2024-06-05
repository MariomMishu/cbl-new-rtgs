package com.cbl.cityrtgs.repositories.specification;

import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.DepartmentAccountFilter;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentAccountEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class DepartmentAccountSpecification {

    public static Specification<DepartmentAccountEntity> all(DepartmentAccountFilter filter) {

        Specification<DepartmentAccountEntity> specification = SpecificationBuilder.conjunction();

        specification = specification.and(SpecificationBuilder.equal("isDeleted", false));

        if (StringUtils.isNotBlank(filter.getAccountNumber())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like("accountNumber", filter.getAccountNumber()));
        }
        if (StringUtils.isNotBlank(filter.getChargeAccNumber())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like("chargeAccNumber", filter.getChargeAccNumber()));
        }
        if (StringUtils.isNotBlank(filter.getVatAccNumber())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like("vatAccNumber", filter.getVatAccNumber()));
        }
        if (Objects.nonNull(filter.getRoutingType())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal("routingType", filter.getRoutingType()));
        }
//        if (Objects.nonNull(filter.getDept())) {
//            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal(DepartmentAccountEntity_.DEPT, filter.getDept()));
//        }
//        if (StringUtils.isNotBlank(filter.getDept())) {
//            specification = ((Specification)Objects.requireNonNull(specification)
//                            .and(SpecificationBuilder.equal(
//                                            DepartmentAccountEntity_.DEPT,
//                                            DepartmentEntity_.name,
//                                            filter.getDept())));
//        }

//        if (Objects.nonNull(filter.getDeptId())) {
//            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal("dept", filter.getDeptId()));
//        }
//        if (Objects.nonNull(filter.getCurrencyId())) {
//            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal("currency", filter.getCurrencyId()));
//        }
        return specification;
    }
}