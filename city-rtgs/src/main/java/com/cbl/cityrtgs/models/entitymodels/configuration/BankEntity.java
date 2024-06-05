package com.cbl.cityrtgs.models.entitymodels.configuration;

import com.cbl.cityrtgs.models.entitymodels.base.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "RTGS_BANKS")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankEntity extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "NAME", length = 50)
    private String name;

    @Column(name = "ADDRESS1")
    private String address1;

    @Column(name = "ADDRESS2")
    private String address2;

    @Column(name = "ADDRESS3")
    private String address3;

    @Column(name = "BIC")
    private String bic;

    @Column(name = "BANKCODE")
    private String bankCode;

    @Column(name = "SETTLEMENTBANK", length = 1)
    private boolean settlementBank;

    @Column(name = "OWNERBANK", length = 1)
    private boolean isOwnerBank;

}
