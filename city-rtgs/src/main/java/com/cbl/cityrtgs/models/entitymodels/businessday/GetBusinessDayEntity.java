package com.cbl.cityrtgs.models.entitymodels.businessday;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.dto.businessinfo.EventTypeCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = EntityConstant.BIZ_DAY_INF)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class GetBusinessDayEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "MSGID")
    private String msgId;

    @Column(name = "CREATEDATE")
    private LocalDateTime createDate;

    @Column(name = "CREATETIME")
    private LocalDateTime createTime;

    @Column(name = "EVENTTYPECODE")
    @Enumerated(EnumType.STRING)
    private EventTypeCode eventTypeCode;
}
