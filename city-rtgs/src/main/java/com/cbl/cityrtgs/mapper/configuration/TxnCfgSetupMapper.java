package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.outwardTransactionConfiguration.TxnCfgSetupRequest;
import com.cbl.cityrtgs.models.dto.configuration.outwardTransactionConfiguration.TxnCfgSetupResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.CurrencyEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.TxnCfgSetupEntity;
import com.cbl.cityrtgs.repositories.configuration.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TxnCfgSetupMapper {
    private final CurrencyRepository currencyRepository;

    public TxnCfgSetupResponse entityToDomain(TxnCfgSetupEntity entity) {
        TxnCfgSetupResponse response = new TxnCfgSetupResponse();
        Optional<CurrencyEntity> optional = currencyRepository.findByIdAndIsDeletedFalse(entity.getCurrencyId());
        if(optional.isPresent()){
            response.setCurrencyCode(optional.get().getShortCode());
        }else{
            response.setCurrencyCode(null);
        }
        response
                .setStartTime(entity.getStartTime())
                .setEndTime(entity.getEndTime())
                .setTimeRestricted(entity.getTimeRestricted())
                .setTxnActive(entity.getTxnActive())
                .setCurrencyId(entity.getCurrencyId())
                .setId(entity.getId());
        return response;
    }

    public TxnCfgSetupEntity domainToEntity(TxnCfgSetupRequest domain) {
        TxnCfgSetupEntity entity = new TxnCfgSetupEntity();
        entity.setCurrencyId(domain.getCurrencyId())
                .setStartTime(domain.getStartTime())
                .setEndTime(domain.getEndTime())
                .setTimeRestricted(domain.getTimeRestricted())
                .setTxnActive(domain.getTxnActive());
        return entity;
    }
}
