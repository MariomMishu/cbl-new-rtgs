package com.cbl.cityrtgs.models.entitymodels.configuration;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.entitymodels.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = EntityConstant.RTGSWORKFLOW)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowEntity extends BaseEntity {
    @ManyToMany
    @JoinTable(
            name = EntityConstant.WORKFLOW_USERTASK,
            joinColumns = @JoinColumn(name = "WORKFLOW_ID"),
            inverseJoinColumns = @JoinColumn(name = "USERTASK_ID"))
    Set<UserTaskEntity> userTasks;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "NAME")
    private String name;


}
