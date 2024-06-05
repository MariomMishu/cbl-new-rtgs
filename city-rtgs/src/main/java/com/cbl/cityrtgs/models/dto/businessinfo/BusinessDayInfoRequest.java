package com.cbl.cityrtgs.models.dto.businessinfo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class BusinessDayInfoRequest {

    private String msgId;

    private LocalDateTime createDate;

    private LocalDateTime createTime;

    private EventTypeCode eventTypeCode;
}
