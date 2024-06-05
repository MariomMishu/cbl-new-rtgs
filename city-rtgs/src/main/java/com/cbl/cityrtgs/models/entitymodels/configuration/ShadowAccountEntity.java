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
@Table(name = EntityConstant.SHADOWACCOUNT)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ShadowAccountEntity extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "ACCONUNTNUMBER")
    private String accountNumber;

    @Column(name = "CBSACCOUNTNUMBER")
    private String cbsAccountNumber;

    @Column(name = "RTGSSETTLEMENTACCOUNT")
    private String rtgsSettlementAccount;

    @Column(name = "INCOMINGGL")
    private String incomingGl;

    @Column(name = "OUTGOINGGL")
    private String outgoingGl;

    @OneToOne(fetch = FetchType.LAZY)
    private BankEntity bank;

    private Long branchId;

    private Long productId;

    @OneToOne(fetch = FetchType.LAZY)
    private CurrencyEntity currency;
}
