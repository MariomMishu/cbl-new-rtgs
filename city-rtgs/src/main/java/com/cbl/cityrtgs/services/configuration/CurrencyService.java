package com.cbl.cityrtgs.services.configuration;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.currency.CurrencyRequest;
import com.cbl.cityrtgs.models.dto.configuration.currency.CurrencyResponse;
import com.cbl.cityrtgs.common.exception.ResourceAlreadyExistsException;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.CurrencyMapper;
import com.cbl.cityrtgs.models.entitymodels.configuration.CurrencyEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CurrencyService {

    private final CurrencyMapper mapper;
    private final CurrencyRepository repository;

    public Page<CurrencyResponse> getAll(Pageable pageable) {
        Page<CurrencyEntity> entities = repository.findAllByIsDeletedFalse(pageable);
        return entities.map(mapper::entityToResponseDomain);
    }

    public void createCurrency(CurrencyRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        this.isExistValidation(request, false, null);
        CurrencyEntity entity = mapper.requestDomainToEntity(request);
        entity.setCreatedAt(new Date());
        entity.setCreatedBy(currentUser.getId());
        entity = repository.save(entity);
        log.info("New Currency {} is saved", entity.getShortCode());
    }

    public CurrencyResponse getById(Long currencyId) {

        Optional<CurrencyEntity> _entity = repository.findByIdAndIsDeletedFalse(currencyId);
        if (_entity.isPresent()) {
            CurrencyEntity entity = _entity.get();
            return mapper.entityToResponseDomain(entity);
        } else {
            return null;
        }
    }

    public void updateOne(Long id, CurrencyRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        Optional<CurrencyEntity> _entity = repository.findByIdAndIsDeletedFalse(id);
        this.isExistValidation(request, true, id);
        if (_entity.isPresent()) {
            CurrencyEntity entity = _entity.get();
            entity = mapper.requestDomainToEntity(request);
            entity.setId(_entity.get().getId());
            entity.setCreatedAt(_entity.get().getCreatedAt());
            entity.setCreatedBy(_entity.get().getCreatedBy());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(new Date());
            repository.save(entity);
        }
        log.info("Currency {} Updated", request.getShortCode());
    }

    public void deleteOne(Long id) {
        CurrencyEntity entity = this.getEntityById(id);
        repository.delete(entity);
        log.info("Currency {} Deleted", id);
    }

    public CurrencyEntity getEntityById(Long id) {
        return repository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Currency not found"));
    }

    public boolean existOne(Long currencyId) {
        return repository.existsById(currencyId);
    }

    public Boolean isExist(String shortCode) {
        return repository.existsByShortCode(shortCode);
    }

    public CurrencyResponse getByCurrencyCode(String currency) {

        var entity = repository.findByShortCodeAndIsDeletedFalse(currency);

        if (entity.isEmpty()) {
            throw new ResourceNotFoundException("Currency not found");
        }

        return mapper.entityToResponseDomain(entity.get());

    }

    public List<CurrencyResponse> getCurrencyList() {
        List<CurrencyEntity> entities = repository.findAllByIsDeletedFalse();
        return entities.stream().map(mapper::entityToResponseDomain).collect(Collectors.toList());
    }

    private void isExistValidation(CurrencyRequest request, boolean isUpdate, Long id) {
        List<CurrencyEntity> entityList = repository.findAllByIsDeletedFalse();
        entityList.forEach(
                entity -> {
                    if (isUpdate) {
                        if (!entity.getId().equals(id)) {
                            if (entity.getShortCode().trim().equalsIgnoreCase(request.getShortCode().trim())) {
                                throw new ResourceAlreadyExistsException("Already exist with Currency : " + request.getShortCode());
                            }
                            if (entity.getDescription().trim().equalsIgnoreCase(request.getDescription().trim())) {
                                throw new ResourceAlreadyExistsException("Already exist with Description: " + request.getDescription());
                            }
                        }

                    } else {
                        if (entity.getShortCode().trim().equalsIgnoreCase(request.getShortCode().trim())) {
                            throw new ResourceAlreadyExistsException("Already exist with Currency : " + request.getShortCode());
                        }
                        if (entity.getDescription().trim().equalsIgnoreCase(request.getDescription().trim())) {
                            throw new ResourceAlreadyExistsException("Already exist with Description: " + request.getDescription());
                        }
                    }
                });
    }

    public CurrencyResponse getByCurrencyShortCode(String currency) {

        var entity = repository.findByShortCodeAndIsDeletedFalse(currency);

        if (entity.isPresent()) {
            return mapper.entityToResponseDomain(entity.get());
        }
        return null;
    }
}
