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
@Table(name = EntityConstant.INWARDTRANSACTIONCONFIGURATION)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class InwardTransactionConfigurationEntity extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "AUTOGENERATERETURNONERROR")
    private Boolean autoGeneratereTurnOnError;

    @Column(name = "ISMANUALTXN")
    private Boolean isManualTxn;

    @Column(name = "MATCHBENIFICIARYNAME")
    private Boolean matchBenificiaryName;

    @Column(name = "MAXIMUMPROCESSINGTIME")
    private int maximumProcessingTime;

    @Column(name = "MATCHINGPERCENTAGE")
    private int matchingPercentage;

}
