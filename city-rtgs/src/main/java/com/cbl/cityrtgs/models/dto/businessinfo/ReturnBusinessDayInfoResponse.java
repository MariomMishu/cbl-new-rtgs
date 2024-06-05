package com.cbl.cityrtgs.models.dto.businessinfo;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class ReturnBusinessDayInfoResponse {

    private Long id;

    private String msgId;

    private Date createDate;

    private Date createTime;

    private String originalMsgId;

    private Date originalCreateDate;

    private Date originalCreateTime;

    private List<BusinessDayEventResponse> bizDayEvt;
}
