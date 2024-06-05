package com.cbl.cityrtgs.services.configuration;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.chargeaccountsetup.ChargeSetupRequest;
import com.cbl.cityrtgs.models.dto.configuration.chargeaccountsetup.ChargeSetupResponse;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.ChargeAccountSetupMapper;
import com.cbl.cityrtgs.models.entitymodels.configuration.ChargeSetupEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.ChargeSetupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChargeAccountSetupService {
    private final ChargeAccountSetupMapper mapper;
    private final ChargeSetupRepository repository;
    private final CurrencyService currencyService;


    public List<ChargeSetupResponse> getAll() {
        List<ChargeSetupEntity> entities = repository.findAllByStatus(true);
        List<ChargeSetupResponse> chargeSetupResponseList = new ArrayList<>();
        for (ChargeSetupEntity item : entities) {
            chargeSetupResponseList.add(mapper.entityToDomain(item));
        }
        return chargeSetupResponseList;
    }

    public void createChargeSetup(ChargeSetupRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        currencyService.getEntityById(request.getCurrencyId());
        ChargeSetupEntity entity = mapper.domainToEntity(request);
        entity.setCreatedAt(new Date());
        entity.setCreatedBy(currentUser.getId());
        repository.save(entity);
        log.info("New Charge Account Setup {} is saved");
    }

    public ChargeSetupResponse getById(Long id) {
        ChargeSetupEntity entity = repository.findById(id)
                .orElse(null);
        ChargeSetupResponse response = mapper.entityToDomain(entity);
        return response;
    }

    public void updateOne(Long id, ChargeSetupRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        currencyService.getEntityById(request.getCurrencyId());
        Optional<ChargeSetupEntity> _entity = repository.findById(id);
        if (_entity.isPresent()) {
            ChargeSetupEntity entity = _entity.get();
            entity = mapper.domainToEntity(request);
            entity.setId(_entity.get().getId());
            entity.setCreatedAt(_entity.get().getCreatedAt());
            entity.setCreatedBy(_entity.get().getCreatedBy());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(new Date());
            repository.save(entity);
        }
        log.info("Charge Setup {} Updated", _entity.isPresent());
    }

    public void deleteOne(Long id) {
        ChargeSetupEntity entity = this.getEntityById(id);
        repository.delete(entity);
        log.info("Charge Setup {} Deleted", id);
    }

    public ChargeSetupEntity getEntityById(Long id) {
        return repository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Charge Setup not found"));
    }

    public boolean existOne(Long id) {
        return repository.existsById(id);
    }

    public ChargeSetupResponse getChargeSetupResponseByCurrency(Long currencyId) {
        return repository
                .findByCurrencyIdAndIsDeletedFalse(currencyId).map(e ->
                        mapper.entityToDomainAfterCalculation(e)).orElseThrow(
                        () ->
                                new ResourceNotFoundException("Charge Setup not found"));
    }

    public BigDecimal calculateFromAmt(Long currencyId) {
        currencyService.getEntityById(currencyId);
        Optional<ChargeSetupEntity> entity = repository
                .findFirstByCurrencyIdAndIsDeletedFalseOrderByIdDesc(currencyId);
        if (entity.isPresent()) {
            return BigDecimal.valueOf(entity.get().getToAmount().add(new BigDecimal(0.01)).doubleValue());
        } else {
            return BigDecimal.valueOf(0.01);
        }

    }

    public ChargeSetupResponse getChargeSetupResponseByCurrency(Long currencyId, BigDecimal amount) {
        return repository
                .findByCurrencyIdAndFromAmountLessThanEqualAndToAmountGreaterThanEqualAndIsDeletedFalseAndStatusIsTrue(currencyId, amount, amount).map(e ->
                        mapper.entityToDomainAfterCalculation(e)).orElseThrow(
                        () ->
                                new ResourceNotFoundException("Charge Setup not found"));
    }

    public ChargeSetupResponse  getChargeVatByCurrency(Long currencyId, BigDecimal amount) {
        ChargeSetupResponse chargeSetupResponse = new ChargeSetupResponse();
        Optional<ChargeSetupEntity> entityOptional = repository
                .findByCurrencyIdAndFromAmountLessThanEqualAndToAmountGreaterThanEqualAndIsDeletedFalseAndStatusIsTrue(currencyId, amount, amount);
        if (entityOptional.isPresent()) {
            return mapper.entityToDomainAfterCalculation(entityOptional.get());
        } else {
            chargeSetupResponse.setChargeAmount(BigDecimal.ZERO.doubleValue());
            chargeSetupResponse.setVatAmount(BigDecimal.ZERO.doubleValue());

            return chargeSetupResponse;
        }
    }

}
