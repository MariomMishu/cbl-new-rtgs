package com.cbl.cityrtgs.models.dto.authentication;

import com.cbl.cityrtgs.models.entitymodels.authentication.ActiveSession;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.common.utility.IpUtility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ActiveSessionRequest {

    private UserInfoEntity user;
    private String ipAddress;
    private String accessToken;
    private LocalDateTime sessionStartTime;
    private LocalDateTime sessionEndTime;
    private Boolean isActive;
    private Boolean isDeleted;

    public static ActiveSession toMODEL(ActiveSessionRequest request){

        return ActiveSession.builder()
                .user(request.getUser())
                .username(request.getUser().getUsername())
                .ipAddress(request.getIpAddress())
                .token(request.getAccessToken())
                .sessionStartTime(request.getSessionStartTime())
                .sessionEndTime(request.getSessionEndTime())
                .isActive(request.getIsActive())
                .isDeleted(request.getIsDeleted())
                .build();
    }

    public static ActiveSessionRequest toDTO(UserInfoEntity user, Map<String, Object> map){

        String accessToken = (String) map.get("accessToken");

        return ActiveSessionRequest.builder()
                .user(user)
                .accessToken(accessToken)
                .sessionStartTime((LocalDateTime) map.get("start"))
                .sessionEndTime((LocalDateTime) map.get("end"))
                .ipAddress(IpUtility.getClientIpAddress())
                .isActive(true)
                .isDeleted(false)
                .build();
    }
}
