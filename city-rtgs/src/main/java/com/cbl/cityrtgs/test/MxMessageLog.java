package com.cbl.cityrtgs.test;

import com.cbl.cityrtgs.models.dto.message.MessageDirectionsType;
import com.cbl.cityrtgs.models.dto.message.MessageProcessStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "RTGS_MESSAGE_LOG")
@Getter
@Setter
@AllArgsConstructor
public class MxMessageLog implements Serializable {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "MSG_LOG_SEQ_GEN"
    )
    @SequenceGenerator(
            name = "MSG_LOG_SEQ_GEN",
            allocationSize = 1,
            initialValue = 1,
            sequenceName = "RTGS_MSG_LOG_SEQ"
    )
    private long id;
    private String uuid;
    @Column(name = "MESSAGERECEIVER")
    private String messageReceiver;
    @Column(name = "MESSAGESENDER")
    private String messageSender;
    @Column(name = "MESSAGETYPE")
    private String messageType;
    @Column(name = "BUSINESSMESSAGEID")
    private String businessMessageId;
    @Column(name = "MESSAGEFORMAT")
    private String messageFormat;
    @Column(name = "PROCESSSTATUS")
    @Enumerated(EnumType.STRING)
    private MessageProcessStatus processStatus;
    @Column(name = "MSGDATE")
    @Temporal(TemporalType.DATE)
    private Date msgDate;
    @Column(name = "MSGTIME")
    @Temporal(TemporalType.TIME)
    private Date msgTime;
    @Column(name = "MESSAGEDIRECTIONS")
    @Enumerated(EnumType.STRING)
    private MessageDirectionsType messageDirections;
    @Column(name = "MESSAGEUSERREFERENCE")
    private String messageUserReference;
    @Column(name = "MESSAGENETMIR")
    private String messageNetMir;
    @Column(name = "MXMESSAGE")
    @Lob
    private String mxMessage;
    @Column(name = "ERRORMESSAGE")
    @Lob
    private String errorMessage;

    public MxMessageLog() {
    }

}
