package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.profile.ProfileRequest;
import com.cbl.cityrtgs.models.dto.configuration.profile.ProfileResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.ProfileEntity;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {
    public ProfileResponse entityToDomain(ProfileEntity entity) {
        ProfileResponse response = new ProfileResponse();
        response
                .setName(entity.getName())
                .setId(entity.getId());
        return response;
    }

    public ProfileEntity domainToEntity(ProfileRequest domain) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(domain.getName());
        return entity;
    }
}
