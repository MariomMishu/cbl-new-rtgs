package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.userAud.UserSession;
import com.cbl.cityrtgs.models.entitymodels.configuration.UserSessionEntity;
import org.springframework.stereotype.Component;

@Component
public class UserSessionMapper {

    public UserSession entityToDomain(UserSessionEntity entity) {
        UserSession response = new UserSession();
        response
                .setCreationOsuserid(entity.getCreationOsuserid())
                .setCreationTerminal(entity.getCreationTerminal())
                .setDestroyBy(entity.getDestroyBy())
                .setDestryTime(entity.getDestryTime())
                /*  .setExpiryDateTime(entity.getExpiryDateTime())
                  .setSessionCreationTime(entity.getSessionCreationTime())*/
                .setSessionId(entity.getSessionId())
                .setUserName(entity.getUserName())
                .setSessionDestroyType(entity.getSessionDestroyType())
                .setUserSessionStatus(entity.getUserSessionStatus())
                .setId(entity.getId());
        return response;
    }

    public UserSessionEntity domainToEntity(UserSession domain) {
        UserSessionEntity entity = new UserSessionEntity();
        entity
                .setCreationOsuserid(domain.getCreationOsuserid())
                .setCreationTerminal(domain.getCreationTerminal())
                .setDestroyBy(domain.getDestroyBy())
                .setDestryTime(domain.getDestryTime())
                .setExpiryDateTime(domain.getExpiryDateTime())
                .setSessionCreationTime(domain.getSessionCreationTime())
                .setSessionId(domain.getSessionId())
                .setUserName(domain.getUserName())
                .setSessionDestroyType(domain.getSessionDestroyType())
                .setUserSessionStatus(domain.getUserSessionStatus());
        return entity;
    }
}
