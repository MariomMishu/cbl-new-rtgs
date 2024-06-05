package com.cbl.cityrtgs.models.entitymodels.si;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = EntityConstant.SI_AMOUNT_TYPE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SiAmountType {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "TYPE")
    private String type;
}
