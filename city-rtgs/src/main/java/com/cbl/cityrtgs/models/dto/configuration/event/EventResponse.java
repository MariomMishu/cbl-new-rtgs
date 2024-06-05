package com.cbl.cityrtgs.models.dto.configuration.event;

import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EventResponse {

    private Long id;

    private String eventId;

    private String eventName;

    private String actionUrl;

    private BigDecimal directPostingLimit;

    private Boolean approvalProcessRequired;

    private Long workflowId;

    private String workflowName;

}
