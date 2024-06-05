package com.cbl.cityrtgs.models.dto.configuration.workflow;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserTaskResponse {

    private Long id;

    private String rightsForTask;

    private Boolean allowMakerCheckerSame;

    private String approverLevelRequirement;

    private Boolean lastTask;

    private Boolean makerCheckerInSameBranch;

    private Boolean makerCheckerInSameDept;

    private String taskName;

    private String taskStatus;

    private Long approverBranch;

    private Long approverDept;

}
