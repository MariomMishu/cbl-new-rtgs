package com.cbl.cityrtgs.services.configuration;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.limitprofile.LimitProfileRequest;
import com.cbl.cityrtgs.models.dto.configuration.limitprofile.LimitProfileResponse;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.LimitProfileMapper;
import com.cbl.cityrtgs.models.entitymodels.configuration.LimitProfileEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.LimitProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LimitProfileService {
    private final LimitProfileMapper mapper;
    private final LimitProfileRepository repository;

    public Page<LimitProfileResponse> getAll(Pageable pageable, Long profileId) {
        Page<LimitProfileEntity> entities;
        if (Objects.nonNull(profileId)) {
            entities = repository.findAllByProfileIdAndIsDeletedFalse(profileId, pageable);
        } else {
            entities = repository.findAllByIsDeletedFalse(pageable);
        }
        return entities.map(mapper::entityToDomain);
    }

    public LimitProfileResponse createLimitProfile(LimitProfileRequest request) {
        LimitProfileResponse response;
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        if (this.isExist(request.getCurrencyId(), request.getProfileId()))
            throw new RuntimeException("Already exist with Currency and profile");

        LimitProfileEntity entity = mapper.domainToEntity(request);
        entity.setCreatedAt(new Date());
        entity.setCreatedBy(currentUser.getId());
        entity = repository.save(entity);
        response = mapper.entityToDomain(entity);
        log.info("New Profile {} is saved", response.getProfileName());
        return response;
    }

    public LimitProfileResponse getById(Long id) {
        return repository
                .findByIdAndIsDeletedFalse(id)
                .map(e -> {
                    LimitProfileResponse response;
                    response = mapper.entityToDomain(e);
                    return response;
                })
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Profile Limit not found"));
    }

    public LimitProfileResponse updateOne(Long id, LimitProfileRequest request) {
        LimitProfileResponse response = new LimitProfileResponse();
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        Optional<LimitProfileEntity> _entity = repository.findByIdAndIsDeletedFalse(id);
        if (_entity.isPresent()) {
            LimitProfileEntity entity = _entity.get();
            entity = mapper.domainToEntity(request);
            entity.setId(_entity.get().getId());
            entity.setCreatedAt(_entity.get().getCreatedAt());
            entity.setCreatedBy(_entity.get().getCreatedBy());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(new Date());
            entity = repository.save(entity);
            response = mapper.entityToDomain(entity);
        }
        log.info("Profile Limit {} Updated", _entity.isPresent());
        return response;
    }

    public void deleteOne(Long id) {
        LimitProfileEntity entity = this.getEntityById(id);
        entity.isDeleted = true;
        repository.save(entity);
        log.info("Profile Limit {} Deleted", id);
    }

    public LimitProfileEntity getEntityById(Long id) {
        return repository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Profile Limit not found"));
    }

    public boolean existOne(Long id) {
        return repository.existsById(id);
    }

    public Boolean isExist(Long currency, Long profileId) {
        return repository.existsByCurrencyIdAndProfileId(currency, profileId);
    }

    public Boolean checkTxnLimitProfile(Long currencyId, BigDecimal amount, Long profileId) {
        Optional<LimitProfileEntity> _entity = repository.findByCurrencyIdAndProfileId(currencyId, profileId);
        if (_entity.isPresent()) {
            if (_entity.get().getDebitLimit().doubleValue() >= amount.doubleValue()) {
                log.info("Limit Profile Balance Validation successful!");
                return true;
            }
            this.log.info("Profile Limit Not Found!");
            return false;
        } else {
            this.log.info("Please Check profile limit!");
            return false;
        }
    }
}
