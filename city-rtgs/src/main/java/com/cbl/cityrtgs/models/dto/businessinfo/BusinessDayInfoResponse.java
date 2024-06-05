package com.cbl.cityrtgs.models.dto.businessinfo;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class BusinessDayInfoResponse {
    private Long id;

    private String msgId;

    private Date createDate;

    private Date createTime;

    private EventTypeCode eventTypeCode;

    private Boolean isError;

    private String errorMessage;
}
