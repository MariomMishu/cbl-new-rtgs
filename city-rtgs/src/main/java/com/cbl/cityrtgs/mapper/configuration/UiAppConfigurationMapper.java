package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.uiappconfiguration.UiAppConfigurationRequest;
import com.cbl.cityrtgs.models.dto.configuration.uiappconfiguration.UiAppConfigurationResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.UiAppConfigurationEntity;
import org.springframework.stereotype.Component;

@Component
public class UiAppConfigurationMapper {
    public UiAppConfigurationResponse entityToDomain(UiAppConfigurationEntity entity) {
        UiAppConfigurationResponse response = new UiAppConfigurationResponse();
        response
                .setAccNumberMinLength(entity.getAccNumberMinLength())
                .setAccNumberMaxLength(entity.getAccNumberMaxLength())
                .setCsvFileMaxItem(entity.getCsvFileMaxItem())
                .setBatchOutEnabled(entity.getBatchOutEnabled())
                .setCrossCurrencySupport(entity.getCrossCurrencySupport())
                .setDisableUi(entity.getDisableUi())
                .setRtgsBalanceValidate(entity.getRtgsBalanceValidate())
                .setValidateBalance(entity.getValidateBalance())
                .setId(entity.getId());
        return response;
    }

    public UiAppConfigurationEntity domainToEntity(UiAppConfigurationRequest domain) {
        UiAppConfigurationEntity entity = new UiAppConfigurationEntity();
        entity
                .setAccNumberMinLength(domain.getAccNumberMinLength())
                .setAccNumberMaxLength(domain.getAccNumberMaxLength())
                .setCsvFileMaxItem(domain.getCsvFileMaxItem())
                .setBatchOutEnabled(domain.getBatchOutEnabled())
                .setCrossCurrencySupport(domain.getCrossCurrencySupport())
                .setDisableUi(domain.getDisableUi())
                .setRtgsBalanceValidate(domain.getRtgsBalanceValidate())
                .setValidateBalance(domain.getValidateBalance());
        return entity;
    }
}
