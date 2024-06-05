package com.cbl.cityrtgs.services.user;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.profile.ProfileRequest;
import com.cbl.cityrtgs.models.dto.configuration.profile.ProfileResponse;
import com.cbl.cityrtgs.common.exception.ResourceAlreadyExistsException;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.ProfileMapper;
import com.cbl.cityrtgs.models.entitymodels.configuration.ProfileEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProfileService {
    private final ProfileMapper mapper;
    private final ProfileRepository repository;

    public Page<ProfileResponse> getAll(Pageable pageable, String name) {
        Page<ProfileEntity> entities;
        if (StringUtils.isNotBlank(name)) {
            entities = repository.findByNameAndIsDeletedFalse(pageable, name);
        } else {
            entities = repository.findAllByIsDeletedFalse(pageable);
        }
        return entities.map(mapper::entityToDomain);
    }

    public ProfileResponse createProfile(ProfileRequest request) {
        ProfileResponse response;
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        if (this.isExist(request.getName())) {
            throw new RuntimeException("Already exist with Name : " + request.getName());
        }
        ProfileEntity entity = mapper.domainToEntity(request);
        entity.setCreatedAt(new Date());
        entity.setCreatedBy(currentUser.getId());
        entity = repository.save(entity);
        response = mapper.entityToDomain(entity);
        log.info("New Profile {} is saved", entity.getName());
        return response;
    }

    public ProfileResponse getById(Long id) {
        return repository.findByIdAndIsDeletedFalse(id)
                .map(e -> {
                    ProfileResponse response;
                    response = mapper.entityToDomain(e);
                    return response;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));
    }

    public ProfileResponse updateProfile(Long id, ProfileRequest request) {
        ProfileResponse response = new ProfileResponse();
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        Optional<ProfileEntity> _entity = repository.findByIdAndIsDeletedFalse(id);
        if (_entity.isPresent()) {
            this.isExistValidation(request.getName(), true, id);
            ProfileEntity entity = _entity.get();
            entity = mapper.domainToEntity(request);
            entity.setId(_entity.get().getId());
            entity.setCreatedAt(_entity.get().getCreatedAt());
            entity.setCreatedBy(_entity.get().getCreatedBy());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(new Date());
            entity = repository.save(entity);
            response = mapper.entityToDomain(entity);

        }
        log.info("Profile {} Updated", _entity.get().getName());
        return response;
    }

    public void deleteOne(Long id) {
        ProfileEntity entity = this.getEntityById(id);
        entity.isDeleted = true;
        repository.save(entity);
        log.info("Profile {} Deleted", id);
    }

    public ProfileEntity getEntityById(Long id) {
        return repository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new ResourceNotFoundException("Profile not found"));
    }

    public boolean existOne(Long id) {
        return repository.existsById(id);
    }

    public Boolean isExist(String name) {
        return repository.existsByNameIgnoreCase(name);
    }

    private void isExistValidation(String name, boolean isUpdate, Long id) {
        List<ProfileEntity> entityList = repository.findAllByIsDeletedFalse();
        entityList.forEach(entity -> {
            if (isUpdate) {
                if (!entity.getId().equals(id)) {
                    if (entity.getName().equals(name)) {
                        throw new ResourceAlreadyExistsException("Already exist with Name : " + name);
                    }
                }
                return;

            } else {
                if (entity.getName().equals(name)) {
                    throw new ResourceAlreadyExistsException("Already exist with Name : " + name);
                }
            }
        });
    }

}
