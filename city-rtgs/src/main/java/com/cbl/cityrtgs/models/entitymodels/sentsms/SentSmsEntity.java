package com.cbl.cityrtgs.models.entitymodels.sentsms;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.entitymodels.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = EntityConstant.TBL_RTGS_SENT_SMS_LOG)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SentSmsEntity extends BaseEntity implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MOBILENO")
    private String mobileNo;

    @Lob
    @Column(name = "SMSDATA", length = 5000)
    private String smsData;

    @Column(name = "BUSINESSMESSAGEID")
    private String businessMessageId;

    @Column(name = "PROCESSSTATUS")
    private String processStatus;

    @Column(name = "MESSAGENETMIR")
    private String messageNetMir;

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

    @Column(name = "RESPONSECODE")
    private String responseCode;

    @Column(name = "RESPONSEMESSAGE")
    private String responseMessage;

}
