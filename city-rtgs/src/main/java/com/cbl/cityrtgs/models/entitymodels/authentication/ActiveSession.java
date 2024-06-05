package com.cbl.cityrtgs.models.entitymodels.authentication;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import lombok.*;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = EntityConstant.ACTIVE_SESSION)
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActiveSession {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private UserInfoEntity user;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "IP_ADDRESS")
    private String ipAddress;

    @Column(name = "TOKEN")
    private String token;

    @Column(name = "SESSION_START_TIME")
    private LocalDateTime sessionStartTime;

    @Column(name = "SESSION_END_TIME")
    private LocalDateTime sessionEndTime;

    @Column(name = "IS_ACTIVE")
    private boolean isActive;

    @Column(name = "IS_DELETED")
    private boolean isDeleted;
}
