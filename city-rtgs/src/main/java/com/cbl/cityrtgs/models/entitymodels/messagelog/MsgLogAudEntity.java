package com.cbl.cityrtgs.models.entitymodels.messagelog;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.entitymodels.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = EntityConstant.RTGS_MESSAGE_LOG_AUD)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class MsgLogAudEntity extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "RTGS_MESSAGE_LOG_ID")
    private Long msgId;

    @Column(name = "MESSAGEDIRECTIONS")
    private String messageDirections;

    @Column(name = "MESSAGEFORMAT")
    private String messageFormat;

    @Column(name = "MESSAGERECEIVER")
    private String messageReceiver;

    @Column(name = "MESSAGESENDER")
    private String messageSender;

    @Column(name = "MESSAGETYPE")
    private String messageType;

    @Column(name = "MESSAGEUSERREFERENCE")
    private String messageUserReference;

    @Lob
    @Column(name = "MXMESSAGE", length = 5000)
    private String mxMessage;

    @Column(name = "PROCESSED")
    private String processed;

    @Column(name = "UUID")
    private String uuid;

    @Column(name = "MSGDATE")
    private Date msgDate;

    @Column(name = "MSGTIME")
    private Date msgTime;

    @Column(name = "BUSINESSMESSAGEID")
    private String businessMessageId;

    @Column(name = "PROCESSSTATUS")
    private String processStatus;

    @Column(name = "MESSAGENETMIR")
    private String messageNetMir;

    @Lob
    @Column(name = "ERRORMESSAGE", length = 5000)
    private String errorMessage;


}
