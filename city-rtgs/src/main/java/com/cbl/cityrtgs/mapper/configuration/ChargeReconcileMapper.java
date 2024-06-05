package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.chargereconcilesetup.ChargeReconcileRequest;
import com.cbl.cityrtgs.models.dto.configuration.chargereconcilesetup.ChargeReconcileResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.ChargeReconcileEntity;
import org.springframework.stereotype.Component;

@Component
public class ChargeReconcileMapper {
    public ChargeReconcileResponse entityToDomain(ChargeReconcileEntity entity) {
        ChargeReconcileResponse response = new ChargeReconcileResponse();
        response
                .setAccountNumber(entity.getAccountNo())
                .setChargeModule(entity.getChargeModule())
                .setChargeType(entity.getChargeType())
                .setCurrencyId(entity.getCurrencyId())
                .setCurrencyName(entity.getCurrencyName())
                .setId(entity.getId());
        return response;
    }

    public ChargeReconcileEntity domainToEntity(ChargeReconcileRequest domain) {
        ChargeReconcileEntity entity = new ChargeReconcileEntity();
        entity
                .setAccountNo(domain.getAccountNumber())
                .setChargeModule(domain.getChargeModule())
                .setChargeType(domain.getChargeType())
                .setCurrencyId(domain.getCurrencyId());
        return entity;
    }
}
