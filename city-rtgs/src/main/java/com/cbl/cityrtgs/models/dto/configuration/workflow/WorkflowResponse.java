package com.cbl.cityrtgs.models.dto.configuration.workflow;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class WorkflowResponse {
    Set<UserTaskResponse> userTasks;
    private Long id;
    private String name;

}
