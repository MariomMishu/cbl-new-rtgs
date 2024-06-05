package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.branch.BranchRequest;
import com.cbl.cityrtgs.models.dto.configuration.branch.BranchResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.BankEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.BranchEntity;
import org.springframework.stereotype.Component;

@Component
public class BranchMapper {
    public BranchResponse entityToDomain(BranchEntity entity) {
        BranchResponse response = new BranchResponse();
        response
                .setName(entity.getName())
                .setAddress1(entity.getAddress1())
                .setAddress2(entity.getAddress2())
                .setAddress3(entity.getAddress3())
                .setRoutingNumber(entity.getRoutingNumber())
                .setCbsBranchId(entity.getCbsBranchId())
                .setBankId(entity.getBank().getId())
                .setRtgsBranch(entity.getRtgsBranch())
                .setTreasuryBranch(entity.getTreasuryBranch())
                .setId(entity.getId());
        return response;
    }

    public BranchEntity domainToEntity(BranchRequest domain) {
        BranchEntity entity = new BranchEntity();
        BankEntity bankEntity = new BankEntity();
        bankEntity.setId(domain.getBankId());
        entity
                .setName(domain.getName())
                .setAddress1(domain.getAddress1())
                .setAddress2(domain.getAddress2())
                .setAddress3(domain.getAddress3())
                .setRoutingNumber(domain.getRoutingNumber())
                .setCbsBranchId(domain.getCbsBranchId())
                .setBank(bankEntity)
                .setRtgsBranch(domain.getRtgsBranch())
                .setTreasuryBranch(domain.getTreasuryBranch());
        return entity;
    }

}
