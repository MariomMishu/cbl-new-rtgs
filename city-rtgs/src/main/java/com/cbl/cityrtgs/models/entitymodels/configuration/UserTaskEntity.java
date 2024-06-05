package com.cbl.cityrtgs.models.entitymodels.configuration;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.entitymodels.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = EntityConstant.USERTASK)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserTaskEntity extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "RIGHTSFORTASK")
    private String rightsForTask;

    @Column(name = "ALLOWMAKERCHECKERSAME")
    private Boolean allowMakerCheckerSame;

    @Column(name = "APPROVERLEVELREQUIREMENT")
    private String approverLevelRequirement;

    @Column(name = "LASTTASK")
    private Boolean lastTask;

    @Column(name = "MAKERCHECKERINSAMEBRANCH")
    private Boolean makerCheckerInSameBranch;

    @Column(name = "MAKERCHECKERINSAMEDEPT")
    private Boolean makerCheckerInSameDept;

    @Column(name = "TASKNAME")
    private String taskName;

    @Column(name = "TASKSTATUS")
    private String taskStatus;

    @Column(name = "APPOVERBRANCH")
    private Long approverBranch;

    @Column(name = "APPROVERDEPT")
    private Long approverDept;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "WORKFLOW_ID")
    private WorkflowEntity workflow;

}
