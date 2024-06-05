package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.schemecode.SchemeCodeRequest;
import com.cbl.cityrtgs.models.dto.configuration.schemecode.SchemeCodeResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.SchemeCodeEntity;
import org.springframework.stereotype.Component;

@Component
public class SchemeCodeMapper {
    public SchemeCodeResponse entityToDomain(SchemeCodeEntity entity) {
        SchemeCodeResponse response = new SchemeCodeResponse();
        response
                .setSchemeCodeKey(entity.getConfigurationKey())
                .setSchemeCodeValue(entity.getConfigurationValue())
                .setId(entity.getId());
        return response;
    }

    public SchemeCodeEntity domainToEntity(SchemeCodeRequest domain) {
        SchemeCodeEntity entity = new SchemeCodeEntity();
        entity
                .setConfigurationValue(domain.getSchemeCodeValue());
        return entity;
    }
}
