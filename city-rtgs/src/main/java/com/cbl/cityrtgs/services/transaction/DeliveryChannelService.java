package com.cbl.cityrtgs.services.transaction;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.mapper.configuration.DeliveryChannelMapper;
import com.cbl.cityrtgs.models.dto.transaction.DeliveryChannelRequest;
import com.cbl.cityrtgs.models.dto.transaction.DeliveryChannelResponse;
import com.cbl.cityrtgs.common.exception.ResourceAlreadyExistsException;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.DeliveryChannelEntity;
import com.cbl.cityrtgs.repositories.transaction.DeliveryChannelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
public class DeliveryChannelService {
    private final DeliveryChannelRepository repository;
    private final DeliveryChannelMapper mapper;

    public Page<DeliveryChannelResponse> getAll(Pageable pageable) {
        Page<DeliveryChannelEntity> entities = repository.findAll(pageable);
        return entities.map(mapper::entityToResponseDomain);
    }

    public void create(DeliveryChannelRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        this.isExistValidation(request, false, null);
        DeliveryChannelEntity entity = this.requestDomainToEntity(request);
        entity.setCreatedAt(new Date());
        entity.setCreatedBy(currentUser.getId());
        entity = repository.save(entity);
        log.info("New Delivery Channel {} is saved", entity.getChannelName());
    }

    public DeliveryChannelResponse getById(Long currencyId) {
        DeliveryChannelEntity entity = repository.findById(currencyId)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Delivery Channel not found"));
        return this.entityToResponseDomain(entity);
    }

    public void updateOne(Long id, DeliveryChannelRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        Optional<DeliveryChannelEntity> _entity = repository.findById(id);
        this.isExistValidation(request, true, id);
        if (_entity.isPresent()) {
            DeliveryChannelEntity entity;
            entity = this.requestDomainToEntity(request);
            entity.setId(_entity.get().getId());
            entity.setCreatedAt(_entity.get().getCreatedAt());
            entity.setCreatedBy(_entity.get().getCreatedBy());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(new Date());
            repository.save(entity);
        }
        log.info("Delivery Channel {} Updated", request.getChannelName());
    }

    public void deleteOne(Long id) {
        DeliveryChannelEntity entity = this.getEntityById(id);
        repository.delete(entity);
        log.info("Delivery Channel {} Deleted", id);
    }

    public DeliveryChannelEntity getEntityById(Long id) {
        return repository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Delivery Channel not found"));
    }

    public boolean existOne(Long id) {
        return repository.existsById(id);
    }

    public Boolean isExist(String deliveryChannel) {
        return repository.existsByChannelName(deliveryChannel);
    }

    public DeliveryChannelResponse getByChannelName(String channelName) {
        var optionalEntity = repository.findByChannelNameAndIsDeletedFalse(channelName);
        return optionalEntity.map(this::entityToResponseDomain).orElse(null);
    }

    public List<DeliveryChannelResponse> getList() {
        List<DeliveryChannelEntity> entities = repository.findAllByIsDeletedFalse();
        return entities.stream().map(this::entityToResponseDomain).collect(Collectors.toList());
    }

    private void isExistValidation(DeliveryChannelRequest request, boolean isUpdate, Long id) {
        List<DeliveryChannelEntity> entityList = repository.findAllByIsDeletedFalse();
        entityList.forEach(
                entity -> {
                    if (isUpdate) {
                        if (!entity.getId().equals(id)) {
                            if (entity.getChannelName().equals(request.getChannelName())) {
                                throw new ResourceAlreadyExistsException("Already exist with Delivery Channel : " + request.getChannelName());
                            }
                        }

                    } else {
                        if (entity.getChannelName().equals(request.getChannelName())) {
                            throw new ResourceAlreadyExistsException("Already exist with Delivery Channel : " + request.getChannelName());
                        }
                    }
                });
    }

    private DeliveryChannelEntity requestDomainToEntity(DeliveryChannelRequest request) {
        DeliveryChannelEntity entity = new DeliveryChannelEntity();
        BeanUtils.copyProperties(request, entity);
        return entity;
    }

    private DeliveryChannelResponse entityToResponseDomain(DeliveryChannelEntity entity) {
        DeliveryChannelResponse response = new DeliveryChannelResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
}
