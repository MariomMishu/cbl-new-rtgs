package com.cbl.cityrtgs.models.entitymodels.businessday;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = EntityConstant.RTR_BIZ_DAY_INF)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDayInformationEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "MSGID")
    private String msgId;

    @Column(name = "CREATEDATE")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @Column(name = "CREATETIME")
    @Temporal(TemporalType.TIME)
    private Date createTime;

    @Column(name = "ORIGINALMSGID")
    private String originalMsgId;

    @Column(name = "ORIGINALCREATEDATE")
    @Temporal(TemporalType.DATE)
    private Date originalCreateDate;

    @Column(name = "ORIGINALCREATETIME")
    @Temporal(TemporalType.TIME)
    private Date originalCreateTime;

    @OneToMany(mappedBy = "bizDayInf",
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY
    )
    private List<BusinessDayEventEntity> bizDayEvt;

}
