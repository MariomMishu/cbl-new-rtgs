package com.cbl.cityrtgs.models.entitymodels.configuration;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = EntityConstant.USERSESSION)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserSessionEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "DESTROYBY")
    private int destroyBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DESTRYTIME")
    private Date destryTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIRYDATETIME")
    private Date expiryDateTime;

    @Column(name = "CREATION_OSUSERID")
    private String creationOsuserid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SESSIONCREATIONTIME")
    private Date sessionCreationTime;

    @Column(name = "SESSIONDESTROYTYPE")
    private String sessionDestroyType;

    @Column(name = "SESSIONID")
    private String sessionId;

    @Column(name = "CREATION_TERMINAL")
    private String creationTerminal;

    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "USERSESSIONSTATUS")
    private String userSessionStatus;

}
