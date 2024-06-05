package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.exemptionsetup.ExemptionSetupRequest;
import com.cbl.cityrtgs.models.dto.configuration.exemptionsetup.ExemptionSetupResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.ExemptionChargeSetupEntity;
import org.springframework.stereotype.Component;

@Component
public class ExemptionChargeSetupMapper {
    public ExemptionSetupResponse entityToDomain(ExemptionChargeSetupEntity entity) {
        ExemptionSetupResponse response = new ExemptionSetupResponse();
        response
                .setAccountNumber(entity.getAccountCode())
                .setAccountName(entity.getAccountName())
                .setId(entity.getId());
        return response;
    }

    public ExemptionChargeSetupEntity domainToEntity(ExemptionSetupRequest domain) {
        ExemptionChargeSetupEntity entity = new ExemptionChargeSetupEntity();

        entity
                .setAccountCode(domain.getAccountNumber())
                .setAccountName(domain.getAccountName());
        return entity;
    }
}
