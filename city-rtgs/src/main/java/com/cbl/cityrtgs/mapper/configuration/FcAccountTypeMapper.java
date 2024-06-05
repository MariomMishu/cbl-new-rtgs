package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.fcaccounttype.FcAccountTypeRequest;
import com.cbl.cityrtgs.models.dto.configuration.fcaccounttype.FcAccountTypeResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.FcAccountTypeEntity;
import org.springframework.stereotype.Component;

@Component
public class FcAccountTypeMapper {
    public FcAccountTypeResponse entityToDomain(FcAccountTypeEntity entity) {
        FcAccountTypeResponse response = new FcAccountTypeResponse();
        response
                .setName(entity.getName())
                .setActive(entity.isActive())
                .setId(entity.getId());
        return response;
    }

    public FcAccountTypeEntity domainToEntity(FcAccountTypeRequest domain) {
        FcAccountTypeEntity entity = new FcAccountTypeEntity();
        entity.setName(domain.getName());
        entity.setActive(domain.isActive());
        return entity;
    }
}
