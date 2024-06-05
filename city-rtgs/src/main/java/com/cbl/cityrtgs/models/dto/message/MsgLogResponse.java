package com.cbl.cityrtgs.models.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class MsgLogResponse {
    private Long id;

    private String messageDirections;

    private String messageFormat;

    private String messageReceiver;

    private String messageSender;

    private String messageType;

    private String messageUserReference;

    private String mxMessage;

    private String processed;

    private String uuid;

//    private LocalDateTime msgDate;
//
//    private LocalDateTime msgTime;

    private Date msgDate;

    private Date msgTime;

    private String businessMessageId;

    private String processStatus;

    private String messageNetMir;

    private String errorMessage;
}
