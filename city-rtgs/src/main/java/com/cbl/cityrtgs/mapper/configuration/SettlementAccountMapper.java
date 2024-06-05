package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.settlementaccount.SettlementAccountRequest;
import com.cbl.cityrtgs.models.dto.configuration.settlementaccount.SettlementAccountResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.BankEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.CurrencyEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.SettlementAccountEntity;
import org.springframework.stereotype.Component;

import java.time.ZoneId;


@Component
public class SettlementAccountMapper {
    public SettlementAccountResponse entityToDomain(SettlementAccountEntity entity) {
        SettlementAccountResponse response = new SettlementAccountResponse();
        response
                .setName(entity.getName())
                .setCode(entity.getCode())
                .setAccountStatus(entity.getAccountStatus())
                .setAccountType(entity.getAccountType())
                .setBalanceCCY(entity.getBalanceCCY())
                .setBalanceLCY(entity.getBalanceLCY())
                .setOpenDate(entity.getOpenDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate())
                .setCurrencyId(entity.getCurrency().getId())
                .setCurrencyDescription(entity.getCurrency().getDescription())
                .setCurrencyCode(entity.getCurrency().getShortCode())
                .setBankId(entity.getBank().getId())
                .setBankName(entity.getBank().getName())
                .setBranchId(entity.getBranchId())
                .setId(entity.getId());
        return response;
    }

    public SettlementAccountEntity domainToEntity(SettlementAccountRequest domain) {
        SettlementAccountEntity entity = new SettlementAccountEntity();
        CurrencyEntity currency = new CurrencyEntity().setId(domain.getCurrencyId());
        BankEntity bank = new BankEntity().setId(domain.getBankId());

        entity
                .setName(domain.getName())
                .setCode(domain.getCode())
                .setAccountStatus(domain.getAccountStatus())
                .setAccountType(domain.getAccountType())
                .setBalanceCCY(domain.getBalanceCCY())
                .setBalanceLCY(domain.getBalanceLCY())
                .setCurrency(currency)
                .setBank(bank)
                .setBranchId(domain.getBranchId());
        return entity;
    }
}
