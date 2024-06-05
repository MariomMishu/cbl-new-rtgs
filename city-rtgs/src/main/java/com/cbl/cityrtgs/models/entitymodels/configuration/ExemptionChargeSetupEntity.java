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
@Table(name = EntityConstant.EXEMPTIONCHARGESETUP)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ExemptionChargeSetupEntity extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "ACCCODE")
    private String accountCode;

    @Column(name = "ACCNAME")
    private String accountName;

    @Column(name = "ACCTYPE")
    private String accountType;

}
