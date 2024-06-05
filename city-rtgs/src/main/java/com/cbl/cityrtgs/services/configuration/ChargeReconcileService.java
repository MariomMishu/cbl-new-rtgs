package com.cbl.cityrtgs.services.configuration;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.chargereconcilesetup.ChargeReconcileFilter;
import com.cbl.cityrtgs.models.dto.configuration.chargereconcilesetup.ChargeReconcileRequest;
import com.cbl.cityrtgs.models.dto.configuration.chargereconcilesetup.ChargeReconcileResponse;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.ChargeReconcileMapper;
import com.cbl.cityrtgs.models.entitymodels.configuration.ChargeReconcileEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.ChargeReconcileRepository;
import com.cbl.cityrtgs.repositories.specification.ChargeReconcileSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChargeReconcileService {
    private final ChargeReconcileMapper mapper;
    private final ChargeReconcileRepository repository;
    private final CurrencyService currencyService;
    Date dateTimeNow = new Date();

    public Page<ChargeReconcileResponse> getAll(Pageable pageable, ChargeReconcileFilter filter) {
        Page<ChargeReconcileEntity> entities = repository.findAll(ChargeReconcileSpecification.all(filter), pageable);
        return entities.map(mapper::entityToDomain);
    }


    public void create(ChargeReconcileRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        ChargeReconcileEntity entity = mapper.domainToEntity(request);
        entity.setCurrencyName(currencyService.getById(request.getCurrencyId()).getShortCode());
        entity.setCreatedAt(dateTimeNow);
        entity.setCreatedBy(currentUser.getId());
        entity = repository.save(entity);
        log.info("New Charge Reconcile Setup {} is saved", entity.getAccountNo());
    }

    public ChargeReconcileResponse getById(Long id) {
        ChargeReconcileEntity entity = repository.findById(id)
                .orElse(null);
        ChargeReconcileResponse response = mapper.entityToDomain(entity);
        return response;
    }

    public void updateOne(Long id, ChargeReconcileRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        Optional<ChargeReconcileEntity> _entity = repository.findById(id);
        if (_entity.isPresent()) {
            ChargeReconcileEntity entity = _entity.get();
            entity = mapper.domainToEntity(request);
            entity.setCurrencyName(currencyService.getById(request.getCurrencyId()).getShortCode());
            entity.setId(_entity.get().getId());
            entity.setCreatedAt(_entity.get().getCreatedAt());
            entity.setCreatedBy(_entity.get().getCreatedBy());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(dateTimeNow);
            repository.save(entity);
        }
        log.info("Charge Reconcile {} Updated", _entity.isPresent());
    }

    public void deleteOne(Long id) {
        ChargeReconcileEntity entity = this.getEntityById(id);
        repository.delete(entity);
        log.info("Charge Reconcile {} Deleted", id);
    }

    public ChargeReconcileEntity getEntityById(Long id) {
        return repository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Charge Reconcile not found"));
    }

    public boolean existOne(Long id) {
        return repository.existsById(id);
    }

}
