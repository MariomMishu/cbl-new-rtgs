package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.bank.BankRequest;
import com.cbl.cityrtgs.models.dto.configuration.bank.BankResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.BankEntity;
import org.springframework.stereotype.Component;

@Component
public class BankMapper {
    public BankResponse entityToDomain(BankEntity entity) {
        BankResponse response = new BankResponse();
        response
                .setName(entity.getName())
                .setBic(entity.getBic())
                .setBankCode(entity.getBankCode())
                .setAddress1(entity.getAddress1())
                .setAddress2(entity.getAddress2())
                .setAddress3(entity.getAddress3())
                .setOwnerBank(entity.isOwnerBank())
                .setSattlementBank(entity.isSettlementBank())
                .setId(entity.getId());
        return response;
    }

    public BankEntity domainToEntity(BankRequest domain) {
        BankEntity entity = new BankEntity();
        entity
                .setName(domain.getName())
                .setBic(domain.getBic())
                .setBankCode(domain.getBankCode())
                .setAddress1(domain.getAddress1())
                .setAddress2(domain.getAddress2())
                .setAddress3(domain.getAddress3())
                .setOwnerBank(domain.isOwnerBank())
                .setSettlementBank(domain.isSattlementBank());
        return entity;
    }

    public BankEntity updateDomainToEntity(BankRequest domain) {
        BankEntity entity = new BankEntity();
        entity
                .setName(domain.getName())
                .setBic(domain.getBic())
                .setBankCode(domain.getBankCode())
                .setAddress1(domain.getAddress1())
                .setAddress2(domain.getAddress2())
                .setAddress3(domain.getAddress3());
        return entity;
    }
}
