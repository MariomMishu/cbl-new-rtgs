package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.rights.RightsResponse;
import com.cbl.cityrtgs.models.dto.configuration.role.RoleRequest;
import com.cbl.cityrtgs.models.dto.configuration.role.RoleResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.RightsEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.RoleEntity;
import com.cbl.cityrtgs.repositories.configuration.RightsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class RoleMapper {
    private final RightsRepository rightsRepository;

    public RoleResponse entityToDomain(RoleEntity entity) {
        RoleResponse response = new RoleResponse();
        Set<RightsResponse> rightsList = new HashSet<>();
        entity.getRights().forEach(
                right -> {
                    if(right != null){
                        RightsEntity rightsEntity = rightsRepository.findByIdAndIsDeletedFalse(right.getId()).get();
                        RightsResponse rightsResponse = new RightsResponse();
                        rightsResponse.setId(rightsEntity.getId());
                        rightsResponse.setName(rightsEntity.getName());
                        rightsList.add(rightsResponse);
                    }
                });
        response
                .setId(entity.getId())
                .setName(entity.getName())
                .setRightsResponses(rightsList);
        return response;
    }

    public RoleEntity domainToEntity(RoleRequest domain) {
        RoleEntity entity = new RoleEntity();
        entity.setName(domain.getName());
        return entity;
    }

    public RightsResponse rightsEntityToRightsDomain(RightsEntity entity) {
        RightsResponse response = new RightsResponse();
        response
                .setName(entity.getName())
                .setId(entity.getId());
        return response;
    }

    public RightsEntity rightsDomainToEntity(RightsResponse domain) {
        RightsEntity entity = new RightsEntity();
        entity.setName(domain.getName());
        return entity;
    }
}
