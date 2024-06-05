package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.currency.CurrencyRequest;
import com.cbl.cityrtgs.models.dto.configuration.currency.CurrencyResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.CurrencyEntity;
import org.springframework.stereotype.Component;

@Component
public class CurrencyMapper {
    public CurrencyResponse entityToResponseDomain(CurrencyEntity entity) {
        CurrencyResponse response = new CurrencyResponse();
        response

                .setShortCode(entity.getShortCode())
                .setDescription(entity.getDescription())
                .setB2bMinAmount(entity.getB2bMinAmount())
                .setC2cMinAmount(entity.getC2cMinAmount())
                .setB2bManualTxn(entity.isB2bManualTxn())
                .setC2cManualTxn(entity.isC2cManualTxn())
                .setId(entity.getId());
        return response;
    }

    public CurrencyEntity requestDomainToEntity(CurrencyRequest domain) {
        CurrencyEntity entity = new CurrencyEntity();
        entity
                .setShortCode(domain.getShortCode().trim().toUpperCase())
                .setDescription(domain.getDescription().trim())
                .setB2bMinAmount(domain.getB2bMinAmount())
                .setC2cMinAmount(domain.getC2cMinAmount())
                .setB2bManualTxn(domain.isB2bManualTxn())
                .setC2cManualTxn(domain.isC2cManualTxn());
        return entity;
    }
}
