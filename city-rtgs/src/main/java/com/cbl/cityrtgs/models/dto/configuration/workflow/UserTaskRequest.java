package com.cbl.cityrtgs.models.dto.configuration.workflow;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserTaskRequest {

    @NotBlank(message = "Right for task can't be empty")
    private String rightsForTask;

    private Boolean allowMakerCheckerSame;

    @NotBlank(message = "Approver level can't be empty")
    private String approverLevelRequirement;

    private Boolean lastTask;

    private Boolean makerCheckerInSameBranch;

    private Boolean makerCheckerInSameDept;

    @NotBlank(message = "Task Name can't be empty")
    private String taskName;

    @NotBlank(message = "Task Status can't be empty")
    private String taskStatus;

    private Long approverBranch;

    private Long approverDept;
    //private Long workflowId;
}
