package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.limitprofile.LimitProfileRequest;
import com.cbl.cityrtgs.models.dto.configuration.limitprofile.LimitProfileResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.CurrencyEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.LimitProfileEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.ProfileEntity;
import org.springframework.stereotype.Component;

@Component
public class LimitProfileMapper {
    public LimitProfileResponse entityToDomain(LimitProfileEntity entity) {
        LimitProfileResponse response = new LimitProfileResponse();
        response
                .setCreditLimit(entity.getCreditLimit())
                .setDebitLimit(entity.getDebitLimit())
                .setTxnVerificationLimit(entity.getTxnVerificationLimit())
                .setCurrencyId(entity.getCurrency().getId())
                .setCurrencyDescription(entity.getCurrency().getDescription())
                .setCurrencyCode(entity.getCurrency().getShortCode())
                .setProfileId(entity.getProfile().getId())
                .setProfileName(entity.getProfile().getName())
                .setId(entity.getId());
        return response;
    }

    public LimitProfileEntity domainToEntity(LimitProfileRequest domain) {
        LimitProfileEntity entity = new LimitProfileEntity();
        CurrencyEntity currency = new CurrencyEntity().setId(domain.getCurrencyId());
        ProfileEntity profile = new ProfileEntity().setId(domain.getProfileId());
        entity
                .setCreditLimit(domain.getCreditLimit())
                .setDebitLimit(domain.getDebitLimit())
                .setTxnVerificationLimit(domain.getTxnVerificationLimit())
                .setCurrency(currency)
                .setProfile(profile);
        return entity;
    }
}
