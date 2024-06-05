package com.cbl.cityrtgs.models.entitymodels.authentication;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import lombok.*;
import lombok.experimental.Accessors;
import javax.persistence.*;

@Entity
@Table(name = EntityConstant.SECURITY_CONFIG)
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecurityConfiguration {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "PCAPTION")
    private String pCaption;

    @Column(name = "PDATA_TYPE")
    private String pDataType;

    @Column(name = "PDESCRIPTION")
    private String pDescription;

    @Column(name = "PNAME", unique = true)
    private String pName;

    @Column(name = "PVALUE")
    private String pValue;

    @Column(name = "PVALUES")
    private String pValues;
}
