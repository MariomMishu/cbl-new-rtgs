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
@Table(name = EntityConstant.TXNCFGSETUP)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TxnCfgSetupEntity extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "STARTTIME")
    private String startTime;

    @Column(name = "ENDTIME")
    private String endTime;

    @Column(name = "EXTRAENDTIME")
    private String extraEndTime;

    @Column(name = "CLOSETIME")
    private String closeTime;

    @Column(name = "TIMERESTRICTED")
    private Boolean timeRestricted;

    @Column(name = "TXNACTIVE")
    private Boolean txnActive;

    @Column(name = "CURRENCY_ID")
    private Long currencyId;
}
