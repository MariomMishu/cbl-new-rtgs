package com.cbl.cityrtgs.models.entitymodels.transaction.notification;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = EntityConstant.STATUSNOTIFICATION)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ReportStatusNotificationEntity{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String msgId;
    private Date creationDate;
    private String originalMsgId;
    private String originalMsgNmId;
    private String originalCreationDate;
    private String groupSts;

    @OneToMany(mappedBy = "stsNotification", cascade = {CascadeType.ALL})
    private Set<StatusReasonEntity> stsReasons;

    @OneToMany(mappedBy = "stsNotification", cascade = {CascadeType.ALL})
    private Set<StatusTransactionEntity> stsTxn;
}
