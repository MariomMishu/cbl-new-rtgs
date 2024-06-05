package com.cbl.cityrtgs.models.dto.authentication;

import com.cbl.cityrtgs.models.entitymodels.authentication.ActiveSession;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ActiveSessionResponse {

    private Long id;
    private String username;
    private String ipAddress;
    private String token;
    private LocalDateTime sessionStartTime;
    private LocalDateTime sessionEndTime;
    private Boolean isActive;
    private Boolean isDeleted;

    public static ActiveSessionResponse toDTO(ActiveSession activeSession){

        return ActiveSessionResponse.builder()
                .id(activeSession.getId())
                .username(activeSession.getUser().getUsername())
                .ipAddress(activeSession.getIpAddress())
                .token(activeSession.getToken())
                .sessionStartTime(activeSession.getSessionStartTime())
                .sessionEndTime(activeSession.getSessionEndTime())
                .isActive(activeSession.isActive())
                .isDeleted(activeSession.isDeleted())
                .build();
    }
}
