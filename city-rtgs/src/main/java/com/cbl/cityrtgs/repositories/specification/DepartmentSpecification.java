package com.cbl.cityrtgs.repositories.specification;

import com.cbl.cityrtgs.models.dto.configuration.department.DepartmentFilter;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class DepartmentSpecification {

    public static Specification<DepartmentEntity> all(DepartmentFilter filter) {

        Specification<DepartmentEntity> specification = SpecificationBuilder.conjunction();

        specification = specification.and(SpecificationBuilder.equal("isDeleted", false));

        if (StringUtils.isNotBlank(filter.getName())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like("name", filter.getName()));
        }

        return specification;
    }
}