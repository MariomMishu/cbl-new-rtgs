package com.cbl.cityrtgs.models.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryChannelRequest  {

    private String channelName;

    private boolean chargeApplicable;

}
