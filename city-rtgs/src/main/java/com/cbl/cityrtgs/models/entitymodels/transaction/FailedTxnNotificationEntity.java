package com.cbl.cityrtgs.models.entitymodels.transaction;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = EntityConstant.FAILEDTRANSACTIONNOTIFICATION)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FailedTxnNotificationEntity{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ERRORCAUSE")
    private String errorCause;

    @Column(name = "MSGTYPE")
    private String msgType;

    @Column(name = "ORIGINALMSGID")
    private String originalMsgId;

    @Column(name = "ERRORDATE")
    private LocalDateTime errorDate;

    @Column(name = "PACS004MSGID")
    private String pacs004MsgId;

}
