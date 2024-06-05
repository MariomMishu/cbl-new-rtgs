package com.cbl.cityrtgs.models.dto.configuration.event;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EventRequest {

    @NotBlank(message = "Event id can't be empty.")
    private String eventId;

    @NotBlank(message = "Event name can't be empty.")
    private String eventName;

    @NotBlank(message = "Action url can't be empty.")
    private String actionUrl;

    @NotNull(message = "Direct Posting Limit Can't Be Empty.")
    private BigDecimal directPostingLimit;

    private Boolean approvalProcessRequired;

    @NotNull(message = "Workflow can't be empty.")
    private Long workflowId;

}
