package com.cbl.cityrtgs.services.configuration;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.branch.BranchFilter;
import com.cbl.cityrtgs.models.dto.configuration.branch.BranchRequest;
import com.cbl.cityrtgs.models.dto.configuration.branch.BranchResponse;
import com.cbl.cityrtgs.common.exception.ResourceAlreadyExistsException;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.BranchMapper;
import com.cbl.cityrtgs.models.entitymodels.configuration.BranchEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.BranchRepository;
import com.cbl.cityrtgs.repositories.specification.BranchSpecification;
import com.cbl.cityrtgs.services.user.UserInfoService;
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
public class BranchService {
    private final BranchMapper mapper;
    private final BranchRepository repository;
    private final RoutingNumberConfigService routingNumberConfigService;
    private final UserInfoService userInfoService;

    public Page<BranchResponse> getAll(Pageable pageable, BranchFilter filter) {
        Page<BranchEntity> entities = repository.findAll(BranchSpecification.all(filter), pageable);
        return entities.map(mapper::entityToDomain);
    }

    public void createBranch(BranchRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        this.isExistValidation(request, false, null);

        BranchEntity entity = mapper.domainToEntity(request);
        entity.setCreatedAt(new Date());
        entity.setCreatedBy(currentUser.getId());
        entity = repository.save(entity);
        log.info("New Branch {} is saved", entity.getName());
    }

    public BranchResponse getBranchById(Long branchId) {
        Optional<BranchEntity> entityOptional = repository.findByIdAndIsDeletedFalse(branchId);
        if (entityOptional.isPresent()) {
            BranchEntity entity = entityOptional.get();
            return mapper.entityToDomain(entity);
        } else {
            return null;
        }
    }

    public void updateOne(Long id, BranchRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        this.isExistValidation(request, true, id);
        Optional<BranchEntity> _entity = repository.findByIdAndIsDeletedFalse(id);
        BranchEntity entity = mapper.domainToEntity(request);
        entity.setUpdatedBy(currentUser.getId());
        entity.setUpdatedAt(new Date());
        entity.setId(id);
        _entity.ifPresent(e -> repository.save(entity));
        log.info("Branch {} Updated", entity.getName());
    }

    public void deleteOne(Long id) {
        BranchEntity entity = this.getEntityById(id);
        entity.isDeleted = true;
        repository.save(entity);
        log.info("Branch {} is Deleted", id);
    }

    public BranchEntity getEntityById(Long id) {
        return repository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Branch not found"));
    }

    public boolean existOne(Long branchId) {
        return repository.existsById(branchId);
    }

    public Boolean isExist(String name) {
        return repository.existsByName(name);
    }

    private void isExistValidation(BranchRequest request, boolean isUpdate, Long id) {
        List<BranchEntity> entityList = repository.findAllByIsDeletedFalse();
        entityList.forEach(
                entity -> {
                    if (isUpdate) {
                        if (!entity.getId().equals(id)) {
                            if (entity.getName().equals(request.getName()) && entity.getBank().getId().equals(request.getBankId())) {
                                throw new ResourceAlreadyExistsException("Already exist with Branch Name : " + request.getName());
                            }
                            if (entity.getRoutingNumber().equals(request.getRoutingNumber()) && entity.getBank().getId().equals(request.getBankId())) {
                                throw new ResourceAlreadyExistsException("Already exist with Routing Number : " + request.getRoutingNumber());
                            }
                        }
                        return;

                    } else {
                        if (entity.getName().equals(request.getName()) && entity.getBank().getId().equals(request.getBankId())) {
                            throw new ResourceAlreadyExistsException("Already exist with Branch Name : " + request.getName());
                        }
                        if (entity.getRoutingNumber().equals(request.getRoutingNumber()) && entity.getBank().getId().equals(request.getBankId())) {
                            throw new ResourceAlreadyExistsException("Already exist with Routing Number : " + request.getRoutingNumber());
                        }
                    }
                });
    }

    public BranchResponse getBranchByRouting(String routing) {
        BranchEntity entity = repository.findByRoutingNumberAndIsDeletedFalse(routing)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Branch not found"));
        BranchResponse response = mapper.entityToDomain(entity);
        return response;
    }

    public List<BranchResponse> getBranchListByBankId(Long bankId) {
        List<BranchEntity> entities = repository.findAllByBankIdAndIsDeletedFalse(bankId);
        return entities.stream().map(mapper::entityToDomain).collect(Collectors.toList());
    }

    public Boolean isValidBranch(Long bankId, String routing) {
        return repository.existsByBankIdAndRoutingNumber(bankId, routing);
    }

    public Long getBranchIdByRouting(String routingNumberString) {

        String[] payerBranchArray = routingNumberString.split("-");
        String routing = payerBranchArray[0];

        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        UserInfoEntity userInfoDetails = userInfoService.getEntityById(currentUser.getId());
        Long responseId= userInfoDetails.getBranch().getId();
        Optional<BranchEntity> optionalEntity = repository.findByRoutingNumberAndIsDeletedFalse(routing);

        if (optionalEntity.isPresent()) {
            BranchEntity entity = optionalEntity.get();
            if(routingNumberConfigService.getRoutingNumberSetup().getIsOutwardTxn()){
                responseId= entity.getId();
            }
            return responseId;
        }
        return responseId;
    }
    public BranchResponse getBranchByRoutingNumber(String routing) {
        Optional<BranchEntity> entity = repository.findByRoutingNumberAndIsDeletedFalse(routing);
        if(entity.isPresent()) return mapper.entityToDomain(entity.get());
        return null;
    }

    public Long getBenBranchIdByRouting(String routingNumberString) {

        String[] payerBranchArray = routingNumberString.split("-");
        String routing = payerBranchArray[0];

        Long benBranchId = 0L;
        Optional<BranchEntity> optionalEntity = repository.findByRoutingNumberAndIsDeletedFalse(routing);

        if (optionalEntity.isPresent()) {
            //BranchEntity entity = optionalEntity.get();
            /*if(routingNumberConfigService.getRoutingNumberSetup().getIsInwardTxn()){
                benBranchId = entity.getId();
            }*/
            return optionalEntity.get().getId();
        }
        return benBranchId;
    }

}
