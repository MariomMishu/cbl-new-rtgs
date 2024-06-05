package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.menu.request.MenuActionRequest;
import com.cbl.cityrtgs.models.dto.menu.response.MenuActionResponse;
import com.cbl.cityrtgs.models.entitymodels.menu.Action;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MenuActionMapper {

    public MenuActionResponse entityToDomain(Action entity) {
        MenuActionResponse response = new MenuActionResponse();
        response
                .setName(entity.getName())
                .setAction(entity.getAction())
                .setId(entity.getId());
        return response;
    }

    public Action domainToEntity(MenuActionRequest domain) {
        Action entity = new Action();
        entity.setName(domain.getName())
                .setAction(domain.getAction());
        return entity;
    }
}
