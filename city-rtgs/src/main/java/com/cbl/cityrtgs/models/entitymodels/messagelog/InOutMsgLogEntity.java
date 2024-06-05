package com.cbl.cityrtgs.models.entitymodels.messagelog;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.entitymodels.base.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = EntityConstant.INOUTMSGLOG)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InOutMsgLogEntity extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BATCHNUMBER")
    private String batchNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MSGCREATIONDATE")
    private Date msgCreationDate;

    @Column(name = "MSGID")
    private String msgId;

    @Column(name = "MSGTYPE")
    private String msgType;

    @Column(name = "RESPONSEMESSAGE")
    private String responseMessage;

    @Column(name = "RESPONSEMSGID")
    private String responseMsgId;

    @Column(name = "RESPONSEMSGTYPE")
    private String responseMsgType;

    @Column(name = "RESPONSETXNNUMBER")
    private String responseTxnNumber;

    @Column(name = "ROUTETYPE", length = 10)
    private String routeType;

    @Column(name = "TXNREFERENCENUMBER")
    private String txnReferenceNumber;

    @Column(name = "ENDTOENDID")
    private String endToEndId;

    @Column(name = "INSTRID")
    private String instrId;

}
