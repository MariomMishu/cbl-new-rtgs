package com.cbl.cityrtgs.models.dto.message;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageLogDTO implements Serializable {

    private String messageFormat;

    private String messageReceiver;

    private String messageSender;

    private String messageType;

    private String messageUserReference;

    private String block4;

    private String msgPriority;

    private String messageNetMir;

    private String businessMessageId;

    private String uuid;

    private Long createdBy;
}
