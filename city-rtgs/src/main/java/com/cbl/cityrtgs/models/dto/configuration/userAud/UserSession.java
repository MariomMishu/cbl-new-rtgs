package com.cbl.cityrtgs.models.dto.configuration.userAud;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserSession {
    private Long id;

    private int destroyBy;

    private Date destryTime;

    private Date expiryDateTime;

    private String creationOsuserid;

    private Date sessionCreationTime;

    private String sessionDestroyType;

    private String sessionId;

    private String creationTerminal;

    private String userName;
    @Enumerated(EnumType.STRING)
    private String userSessionStatus;

}
