package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.shadowaccount.ShadowAccountRequest;
import com.cbl.cityrtgs.models.dto.configuration.shadowaccount.ShadowAccountResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.BankEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.CurrencyEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.ShadowAccountEntity;
import org.springframework.stereotype.Component;


@Component
public class ShadowAccountMapper {
    public ShadowAccountResponse entityToDomain(ShadowAccountEntity entity) {
        ShadowAccountResponse response = new ShadowAccountResponse();
        response
                .setIncomingGl(entity.getIncomingGl())
                .setOutgoingGl(entity.getOutgoingGl())
                .setRtgsSettlementAccount(entity.getRtgsSettlementAccount())
                .setCurrencyId(entity.getCurrency().getId())
                .setCurrencyDescription(entity.getCurrency().getDescription())
                .setCurrencyCode(entity.getCurrency().getShortCode())
                .setBankId(entity.getBank().getId())
                .setBankName(entity.getBank().getName())
                .setId(entity.getId());
        return response;
    }

    public ShadowAccountEntity domainToEntity(ShadowAccountRequest domain) {
        ShadowAccountEntity entity = new ShadowAccountEntity();
        CurrencyEntity currency = new CurrencyEntity().setId(domain.getCurrencyId());
        BankEntity bank = new BankEntity().setId(domain.getBankId());

        entity
                .setIncomingGl(domain.getIncomingGl())
                .setOutgoingGl(domain.getOutgoingGl())
                .setRtgsSettlementAccount(domain.getRtgsSettlementAccount())
                .setCurrency(currency)
                .setBank(bank);
        return entity;
    }
}
