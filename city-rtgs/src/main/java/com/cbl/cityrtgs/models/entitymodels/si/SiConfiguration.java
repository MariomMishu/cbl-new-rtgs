package com.cbl.cityrtgs.models.entitymodels.si;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.entitymodels.configuration.CurrencyEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = EntityConstant.SI_CONFIGURATION)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SiConfiguration {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "CURRENCY_ID")
    private CurrencyEntity currency;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "FREQUENCY_ID")
    private SiFrequency siFrequency;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "AMOUNT_TYPE_ID")
    private SiAmountType amountType;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "EXPIRY_DATE")
    private LocalDate expiryDate;

    @Column(name = "FIRE_DAY")
    private Integer fireDay;

    @Column(name = "MONTHLY_FIRE_DAY")
    private Integer monthlyFireDay;

    @Column(name = "CREATED_ON")
    private Date createdOn;

    @Column(name = "UPDATED_ON")
    private Date updatedOn;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "CREATED_BY")
    private UserInfoEntity createdBy;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "DEPARTMENT_ID")
    private DepartmentEntity department;
}
