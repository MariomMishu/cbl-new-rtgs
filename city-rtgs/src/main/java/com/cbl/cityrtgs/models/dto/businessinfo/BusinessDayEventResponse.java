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
public class BusinessDayEventResponse {

    private Long id;

    private String eventType;

    private Date startDate;

    private Date startTime;

    private Date endDate;

    private Date endTime;

}
