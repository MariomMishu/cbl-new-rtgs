package com.cbl.cityrtgs.models.entitymodels.configuration;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.dto.configuration.accounttype.AccountingType;
import com.cbl.cityrtgs.models.dto.configuration.accounttype.CbsName;
import com.cbl.cityrtgs.models.entitymodels.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = EntityConstant.RTGS_ACCOUNT_TYPES)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AccountTypeEntity extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "CBSNAME")
    @Enumerated(EnumType.STRING)
    private CbsName cbsName;

    @Column(name = "ACCOUNTINGTYPE")
    @Enumerated(EnumType.STRING)
    private AccountingType accountingType;

    @Column(name = "CBSACCOUNTNUMBER")
    private String cbsAccountNumber;

    @Column(name = "CBSACCOUNTTYPE")
    private Long cbsAccountType;

    @Column(name = "CBSMANAGED")
    private Boolean cbsManaged;

    @Column(name = "CONTRA_ACC_NUMBER")
    private String contraAccNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private SettlementAccountEntity rtgsAccount;

}
