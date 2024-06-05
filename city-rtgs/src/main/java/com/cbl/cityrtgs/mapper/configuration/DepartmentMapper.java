package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.department.DepartmentRequest;
import com.cbl.cityrtgs.models.dto.configuration.department.DepartmentResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentEntity;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {
    public DepartmentResponse entityToResponseDomain(DepartmentEntity entity) {
        DepartmentResponse response = new DepartmentResponse();
        response
                .setName(entity.getName())
                .setId(entity.getId());
        return response;
    }

    public DepartmentEntity requestDomainToEntity(DepartmentRequest domain) {
        DepartmentEntity entity = new DepartmentEntity();
        entity.setName(domain.getName());
        return entity;
    }
}
