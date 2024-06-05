package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.rights.RightsRequest;
import com.cbl.cityrtgs.models.dto.configuration.rights.RightsResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.RightsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RightsMapper {

    public RightsResponse entityToDomain(RightsEntity entity) {
        RightsResponse response = new RightsResponse();

        response
                .setId(entity.getId())
                .setName(entity.getName());
        return response;
    }

    public RightsEntity domainToEntity(RightsRequest domain) {
        RightsEntity entity = new RightsEntity();
        entity.setName(domain.getName().toUpperCase());
        return entity;
    }

}
