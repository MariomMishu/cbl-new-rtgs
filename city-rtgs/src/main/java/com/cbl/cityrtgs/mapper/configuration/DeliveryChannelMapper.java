package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.transaction.DeliveryChannelResponse;
import com.cbl.cityrtgs.models.entitymodels.transaction.DeliveryChannelEntity;
import org.springframework.stereotype.Component;

@Component
public class DeliveryChannelMapper {
    public DeliveryChannelResponse entityToResponseDomain(DeliveryChannelEntity entity) {
        DeliveryChannelResponse response = new DeliveryChannelResponse();
        response
                .setId(entity.getId())
                .setChannelName(entity.getChannelName())
                .setChargeApplicable(entity.isChargeApplicable());
        return response;
    }

}
