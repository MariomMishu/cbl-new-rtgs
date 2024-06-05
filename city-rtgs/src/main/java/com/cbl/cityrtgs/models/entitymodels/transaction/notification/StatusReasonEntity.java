package com.cbl.cityrtgs.models.entitymodels.transaction.notification;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = EntityConstant.REASON)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class StatusReasonEntity{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String reason;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "TBL_ADDITIONINFOS", joinColumns = {@JoinColumn(name = "REASON_ID")})
    @Column(name = "INFO")
    private List<String> addtnalInfs;

    @ManyToOne
    @JoinColumn(name = "stsNotification_id")
    private ReportStatusNotificationEntity stsNotification;
}
