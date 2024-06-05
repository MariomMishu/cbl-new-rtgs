package com.cbl.cityrtgs.models.entitymodels.businessday;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = EntityConstant.RTR_BIZ_DAY_EVT)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDayEventEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "EVENTTYPE")
    private String eventType;

    @Column(name = "STARTDATE")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "STARTTIME")
    @Temporal(TemporalType.TIME)
    private Date startTime;

    @Column(name = "ENDDATE")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "ENDTIME")
    @Temporal(TemporalType.TIME)
    private Date endTime;

    @ManyToOne
            (fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "bizDayInf_id")
    private BusinessDayInformationEntity bizDayInf;
}
