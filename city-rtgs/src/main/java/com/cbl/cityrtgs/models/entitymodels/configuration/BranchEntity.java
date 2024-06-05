package com.cbl.cityrtgs.models.entitymodels.configuration;

import com.cbl.cityrtgs.models.entitymodels.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.persistence.*;

@Entity
@Table(name = "RTGS_BRANCHS")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class BranchEntity extends BaseEntity {
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

    @Column(name = "BRANCHCODE")
    private String branchCode;

    @Column(name = "ROUTINGNUMBER")
    private String routingNumber;

    @Column(name = "CBSBRANCHID")
    private String cbsBranchId;

    @Column(name = "RTGSBRANCH")
    private Boolean rtgsBranch;

    @Column(name = "ISDIVISION")
    private Boolean treasuryBranch;

    @ManyToOne(fetch = FetchType.EAGER)
    private BankEntity bank;
}
