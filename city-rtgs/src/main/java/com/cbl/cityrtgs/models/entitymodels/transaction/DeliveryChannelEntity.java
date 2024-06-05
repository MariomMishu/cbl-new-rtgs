package com.cbl.cityrtgs.models.entitymodels.transaction;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.entitymodels.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.persistence.*;

@Entity
@Table(name = EntityConstant.DELIVERY_CHANNEL)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryChannelEntity extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "CHANNELNAME")
    private String channelName;

    @Column(name = "CHARGEAPPLICABLE")
    private boolean chargeApplicable;

}
