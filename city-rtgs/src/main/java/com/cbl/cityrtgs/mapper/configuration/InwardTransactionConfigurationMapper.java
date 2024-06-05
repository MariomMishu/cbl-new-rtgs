package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.inwardtransactionConfiguration.InwardTransactionConfigurationRequest;
import com.cbl.cityrtgs.models.dto.configuration.inwardtransactionConfiguration.InwardTransactionConfigurationResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.InwardTransactionConfigurationEntity;
import org.springframework.stereotype.Component;

@Component
public class InwardTransactionConfigurationMapper {
    public InwardTransactionConfigurationResponse entityToDomain(InwardTransactionConfigurationEntity entity) {
        InwardTransactionConfigurationResponse response = new InwardTransactionConfigurationResponse();
        response
                .setAutoGeneratereTurnOnError(entity.getAutoGeneratereTurnOnError())
                .setIsManualTxn(entity.getIsManualTxn())
                .setMatchBenificiaryName(entity.getMatchBenificiaryName())
                .setMatchingPercentage(entity.getMatchingPercentage())
                .setMaximumProcessingTime(entity.getMaximumProcessingTime())
                .setId(entity.getId());
        return response;
    }

    public InwardTransactionConfigurationEntity domainToEntity(InwardTransactionConfigurationRequest domain) {
        InwardTransactionConfigurationEntity entity = new InwardTransactionConfigurationEntity();
        entity
                .setAutoGeneratereTurnOnError(domain.getAutoGeneratereTurnOnError())
                .setIsManualTxn(domain.getIsManualTxn())
                .setMatchBenificiaryName(domain.getMatchBenificiaryName())
                .setMatchingPercentage(domain.getMatchingPercentage())
                .setMaximumProcessingTime(domain.getMaximumProcessingTime());
        return entity;
    }
}
