package com.cbl.cityrtgs.services.transaction;

import com.cbl.cityrtgs.models.dto.message.ReturnReason;
import com.cbl.cityrtgs.models.entitymodels.transaction.ReturnReasonEntity;
import com.cbl.cityrtgs.repositories.transaction.ReturnReasonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReturnReasonService {
    private final ReturnReasonRepository repository;

    public List<ReturnReason> getAllList() {
        List<ReturnReasonEntity> entities = repository.findAllByIsDeletedFalse();
        return entities.stream().map(this::entityToDomain).collect(Collectors.toList());
    }

    public ReturnReason getReturnReasonByCode(String returnCode) {
        ReturnReason returnReason = null;
        Optional<ReturnReasonEntity> _entity = repository.findByCodeAndIsDeletedFalse(returnCode);
        if (_entity.isPresent()) {
            ReturnReasonEntity entity = _entity.get();
            returnReason = this.entityToDomain(entity);
            return returnReason;
        }
        return returnReason;

    }

    public ReturnReason entityToDomain(ReturnReasonEntity entity) {
        ReturnReason response = new ReturnReason();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

}
