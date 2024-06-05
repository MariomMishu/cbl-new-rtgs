package com.cbl.cityrtgs.services.user;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.userAud.UserAud;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.UserAudMapper;
import com.cbl.cityrtgs.models.entitymodels.user.UserAudEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.UserAudRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAudService {
    private final UserAudRepository repository;
    private final UserAudMapper mapper;

    public Page<UserAud> getAll(Pageable pageable) {
        Page<UserAudEntity> entities = repository.findAllByIsDeletedFalse(pageable);
        return entities.map(mapper::entityToDomain);
    }

    public void createUserAud(UserAud request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        UserAudEntity entity = mapper.domainToEntity(request);
        entity.setUsername(request.getUsername());
        entity.setCreatedAt(new Date());
        entity.setCreatedBy(currentUser.getUsername());
        entity = repository.save(entity);
        log.info("New User Aud {} is saved", entity.getUsername());
    }

    public UserAud getById(Long id) {
        UserAudEntity entity = repository.findByIdAndIsDeletedFalse(id)
                .orElse(null);
        UserAud response = mapper.entityToDomain(entity);
        return response;
    }

    public void updateOne(Long id, UserAud request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        Optional<UserAudEntity> _entity = repository.findByIdAndIsDeletedFalse(id);

        if (_entity.isPresent()) {
            UserAudEntity entity = _entity.get();
            entity = mapper.domainToEntity(request);
            entity.setUsername(_entity.get().getUsername());
            entity.setId(_entity.get().getId());
            entity.setCreatedAt(_entity.get().getCreatedAt());
            entity.setCreatedBy(_entity.get().getCreatedBy());
            entity.setUpdatedBy(currentUser.getUsername());
            entity.setUpdatedAt(new Date());
            repository.save(entity);
        }
        log.info("User {} Updated", _entity.get().getUsername());
    }

    public void deleteOne(Long id) {
        UserAudEntity entity = this.getEntityById(id);
        entity.isDeleted = true;
        repository.save(entity);
        log.info("User Aud {} Deleted", id);
    }

    public UserAudEntity getEntityById(Long id) {
        return repository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("User Aud not found"));
    }

    public boolean existOne(Long id) {
        return repository.existsById(id);
    }

    public Boolean isExist(String username) {
        return repository.existsByUsername(username);
    }

    public void saveUserAud(UserInfoEntity entity) {

        UserAudEntity audEntity = mapper.entityToAudEntity(entity, 0);
        audEntity.setUsername(entity.getUsername());
        audEntity.setCreatedAt(new Date());
        audEntity.setCreatedBy(LoggedInUserDetails.getCreatorName());
        repository.save(audEntity);
        log.info("User Aud {} is saved", entity.getUsername());
    }
}
