package com.cbl.cityrtgs.repositories.specification;

import com.cbl.cityrtgs.models.dto.message.RtgsMessageFilter;
import com.cbl.cityrtgs.models.entitymodels.messagelog.MsgLogEntity;
import com.cbl.cityrtgs.models.entitymodels.messagelog.MsgLogEntity_;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class RtgsMessageLogSpecification {

    public static Specification<MsgLogEntity> all(RtgsMessageFilter filter) {

        Specification<MsgLogEntity> specification = SpecificationBuilder.conjunction();
        //specification = specification.and(SpecificationBuilder.orderBy(MsgLogEntity_.MSG_DATE, OrderBy.DESC));

        specification = specification.and(SpecificationBuilder.equal("isDeleted", false));

        if (StringUtils.isNotBlank(filter.getMessageDirections())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal("messageDirections", filter.getMessageDirections()));
        }

        if (StringUtils.isNotBlank(filter.getMessageType())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal("messageType", filter.getMessageType()));
        }

        if (StringUtils.isNotBlank(filter.getMessageUserReference())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like("messageUserReference", filter.getMessageUserReference()));
        }

        if (StringUtils.isNotBlank(filter.getProcessStatus())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.equal("processStatus", filter.getProcessStatus()));
        }
        if (StringUtils.isNotBlank(filter.getAnyString())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.like("mxMessage", filter.getAnyString()));
        }

        if (!Objects.isNull(filter.getMsgDate())) {
            specification = ((Specification) Objects.requireNonNull(specification)).and(SpecificationBuilder.dateEqual(MsgLogEntity_.MSG_DATE, filter.getMsgDate()));
        }
        return specification;
    }
}