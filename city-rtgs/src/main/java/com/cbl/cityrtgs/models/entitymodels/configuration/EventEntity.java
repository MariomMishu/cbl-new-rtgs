package com.cbl.cityrtgs.models.entitymodels.configuration;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.entitymodels.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = EntityConstant.RTGS_EVENT)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "EVENTID")
    private String eventId;

    @Column(name = "EVENTNAME")
    private String eventName;

    @Column(name = "ACTIONURL")
    private String actionUrl;

    @Column(name = "DIRECTPOSTINGLIMIT")
    private BigDecimal directPostingLimit = BigDecimal.ZERO;

    @Column(name = "APPROVALPROCESSREQUIRED")
    private Boolean approvalProcessRequired;

    @ManyToOne(fetch = FetchType.LAZY)
    private WorkflowEntity workflow;

}
