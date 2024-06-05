package com.cbl.cityrtgs.services.configuration;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.userAud.UserSession;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.UserSessionMapper;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.UserSessionEntity;
import com.cbl.cityrtgs.repositories.configuration.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserSessionService {
    private final UserSessionRepository repository;
    private final UserSessionMapper mapper;

    public Page<UserSession> getAll(Pageable pageable) {
        Page<UserSessionEntity> entities = repository.findAll(pageable);
        return entities.map(mapper::entityToDomain);
    }

    public void create(UserSession request) {
        //LoggedInUserDetails.getUserInfoDetails().toString();
        UserSessionEntity entity = mapper.domainToEntity(request);
        repository.save(entity);
        log.info("New User Session {} is saved");
    }

    public UserSession getById(Long id) {
        UserSessionEntity entity = repository.findById(id)
                .orElse(null);
        UserSession response = mapper.entityToDomain(entity);
        return response;
    }

    public void updateOne(Long id, UserSession request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        Optional<UserSessionEntity> _entity = repository.findById(id);

        if (_entity.isPresent()) {
            UserSessionEntity entity = _entity.get();
            entity = mapper.domainToEntity(request);
            entity.setId(_entity.get().getId());
            repository.save(entity);
        }
        log.info("User Session {} Updated");
    }

    public void deleteOne(Long id) {
        UserSessionEntity entity = this.getEntityById(id);
        repository.delete(entity);
        log.info("User Session {} Deleted", id);
    }

    public UserSessionEntity getEntityById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("User Session not found"));
    }

    public boolean existOne(Long id) {
        return repository.existsById(id);
    }

}
