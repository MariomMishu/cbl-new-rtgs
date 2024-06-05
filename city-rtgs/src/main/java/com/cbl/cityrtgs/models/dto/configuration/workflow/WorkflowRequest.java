package com.cbl.cityrtgs.models.dto.configuration.workflow;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WorkflowRequest {

    Set<UserTaskRequest> userTasks;
    @NotBlank(message = "Name can't be empty")
    private String name;

}
