package com.cbl.cityrtgs.repositories.specification;

import com.cbl.cityrtgs.models.dto.configuration.userInfo.UserInfoFilter;
import com.cbl.cityrtgs.models.entitymodels.configuration.BranchEntity_;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentEntity_;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity_;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class UserInfoSpecification {

    public static Specification<UserInfoEntity> all(UserInfoFilter filter) {

        Specification<UserInfoEntity> specification = SpecificationBuilder.conjunction();

        specification = specification.and(SpecificationBuilder.equal(UserInfoEntity_.IS_DELETED, false)).and(SpecificationBuilder.equal(UserInfoEntity_.ACTIVATED, true));

        if (StringUtils.isNotBlank(filter.getFullName())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(UserInfoEntity_.FULL_NAME, filter.getFullName()));
        }

        if (StringUtils.isNotBlank(filter.getUsername())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(UserInfoEntity_.USERNAME, filter.getUsername()));
        }

        if (Objects.nonNull(filter.getRecStatus())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal(UserInfoEntity_.REC_STATUS, filter.getRecStatus()));
        }

        if (StringUtils.isNotBlank(filter.getDeptName())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal(UserInfoEntity_.DEPT, DepartmentEntity_.NAME, filter.getDeptName()));
        }

        if (StringUtils.isNotBlank(filter.getBranchName())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal(UserInfoEntity_.BRANCH, BranchEntity_.NAME, filter.getBranchName()));
        }

        if (StringUtils.isNotBlank(filter.getCreatedBy())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(UserInfoEntity_.CREATED_BY, filter.getCreatedBy()));
        }

        if (StringUtils.isNotBlank(filter.getUpdatedBy())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like(UserInfoEntity_.UPDATED_BY, filter.getUpdatedBy()));
        }
        return specification;
    }
}