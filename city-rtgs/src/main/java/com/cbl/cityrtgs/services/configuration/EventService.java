package com.cbl.cityrtgs.services.configuration;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.event.EventRequest;
import com.cbl.cityrtgs.models.dto.configuration.event.EventResponse;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.EventMapper;
import com.cbl.cityrtgs.models.entitymodels.configuration.EventEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.EventRepository;
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
public class EventService {
    private final EventMapper mapper;
    private final EventRepository repository;

    Date dateTimeNow = new Date();

    public Page<EventResponse> getAll(Pageable pageable) {
        Page<EventEntity> entities = repository.findAllByIsDeletedFalse(pageable);
        return entities.map(mapper::entityToDomain);
    }

    public void createEvent(EventRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        if (this.isExist(request.getEventId(),
                request.getEventName())) {
            log.error("Event Already Exists");
            throw new RuntimeException("Event Already Exists");
        }
        EventEntity entity = mapper.domainToEntity(request);
        entity.setCreatedAt(dateTimeNow);
        entity.setCreatedBy(currentUser.getId());
        entity = repository.save(entity);
        log.info("Event {} is saved", request.getEventName());
    }

    public EventResponse getById(Long id) {
        EventEntity entity = repository.findByIdAndIsDeletedFalse(id)
                .orElse(null);
        EventResponse response = mapper.entityToDomain(entity);
        return response;
    }

    public void updateOne(Long id, EventRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        Optional<EventEntity> _entity = repository.findByIdAndIsDeletedFalse(id);
        if (_entity.isPresent()) {
            EventEntity entity = _entity.get();
            entity = mapper.domainToEntity(request);
            entity.setId(_entity.get().getId());
            entity.setCreatedAt(_entity.get().getCreatedAt());
            entity.setCreatedBy(_entity.get().getCreatedBy());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(dateTimeNow);
            repository.save(entity);
        }
        log.info("Event {} Updated", request.getEventName());
    }

    public void deleteOne(Long id) {
        EventEntity entity = this.getEntityById(id);
        entity.isDeleted = true;
        repository.delete(entity);
        log.info("Event {} deleted", id);
    }

    public EventEntity getEntityById(Long id) {
        return repository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Event not found"));
    }

    public boolean existOne(Long id) {
        return repository.existsById(id);
    }

    public Boolean isExist(String eventId, String eventName) {
        return repository.existsByEventIdOrEventName(eventId, eventName);
    }
}
