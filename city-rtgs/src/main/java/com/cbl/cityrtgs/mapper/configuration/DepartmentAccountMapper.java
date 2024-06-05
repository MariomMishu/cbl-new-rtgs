package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.DepartmentAccountRequest;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.DepartmentAccountResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.CurrencyEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentAccountAudEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentAccountEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class DepartmentAccountMapper {
    public DepartmentAccountResponse entityToResponseDomain(DepartmentAccountEntity entity) {
        DepartmentAccountResponse response = new DepartmentAccountResponse();
        response
                .setAccountNumber(entity.getAccountNumber())
                .setChargeAccNumber(entity.getChargeAccNumber())
                .setVatAccNumber(entity.getVatAccNumber())
                .setDeptId(entity.getDept().getId())
                .setDeptName(entity.getDept().getName())
                .setCurrencyId(entity.getCurrency().getId())
                .setCurrencyName(entity.getCurrency().getShortCode())
                .setRoutingType(entity.getRoutingType())
                .setId(entity.getId());
        return response;
    }

    public DepartmentAccountEntity requestDomainToEntity(DepartmentAccountRequest domain) {
        DepartmentAccountEntity entity = new DepartmentAccountEntity();
        DepartmentEntity departmentEntity = new DepartmentEntity().setId(domain.getDeptId());
        CurrencyEntity currencyEntity = new CurrencyEntity().setId(domain.getCurrencyId());
        entity.setAccountNumber(domain.getAccountNumber())
                .setChargeAccNumber(domain.getChargeAccNumber())
                .setVatAccNumber(domain.getVatAccNumber())
                .setDept(departmentEntity)
                .setCurrency(currencyEntity)
                .setRoutingType(domain.getRoutingType());
        return entity;
    }

    public DepartmentAccountAudEntity entityToAudEntity(DepartmentAccountEntity entity) {
        DepartmentAccountAudEntity audEntity = new DepartmentAccountAudEntity();
        BeanUtils.copyProperties(entity, audEntity);
        return audEntity;
    }
}
