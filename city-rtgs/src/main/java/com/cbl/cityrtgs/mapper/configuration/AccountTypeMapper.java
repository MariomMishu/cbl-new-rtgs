package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.accounttype.AccountTypeRequest;
import com.cbl.cityrtgs.models.dto.configuration.accounttype.AccountTypeResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.AccountTypeEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.SettlementAccountEntity;
import com.cbl.cityrtgs.services.configuration.CBSAccountTypesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountTypeMapper {
    private final CBSAccountTypesService cbsAccountTypesService;

    public AccountTypeResponse entityToDomain(AccountTypeEntity entity) {
        AccountTypeResponse response = new AccountTypeResponse();

        response.setAccountingType(entity.getAccountingType())
                .setCode(entity.getCode())
                .setCbsAccountNumber(entity.getCbsAccountNumber())
                .setContraAccNumber(entity.getContraAccNumber())
                .setCbsName(entity.getCbsName())
                .setCbsAccountType(entity.getCbsAccountType())
                .setCbsAccTypeName(cbsAccountTypesService.getById(entity.getCbsAccountType()).getName())
                .setCbsManaged(entity.getCbsManaged())
                .setSettlementAccountId(entity.getRtgsAccount().getId())
                .setId(entity.getId());
        return response;
    }

    public AccountTypeEntity domainToEntity(AccountTypeRequest domain) {
        AccountTypeEntity entity = new AccountTypeEntity();
        SettlementAccountEntity settlementAccount = new SettlementAccountEntity().setId(domain.getSettlementAccountId());
        entity
                .setAccountingType(domain.getAccountingType())
                .setCode(domain.getCode())
                .setCbsAccountNumber(domain.getCbsAccountNumber())
                .setContraAccNumber(domain.getContraAccNumber())
                .setCbsName(domain.getCbsName())
                .setCbsAccountType(domain.getCbsAccountType())
                .setCbsManaged(domain.getCbsManaged())
                .setRtgsAccount(settlementAccount);
        return entity;
    }
}
