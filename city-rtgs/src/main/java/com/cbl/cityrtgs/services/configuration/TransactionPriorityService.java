package com.cbl.cityrtgs.services.configuration;

import com.cbl.cityrtgs.models.dto.configuration.transactionpriority.PriorityCodeResponse;
import com.cbl.cityrtgs.models.dto.configuration.transactionpriority.TransactionPriorityType;
import com.cbl.cityrtgs.models.dto.configuration.transactionpriority.TransactionTypeCodeResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.PriorityCodeEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.TransactionTypeCodeEntity;
import com.cbl.cityrtgs.repositories.configuration.PriorityCodeRepository;
import com.cbl.cityrtgs.repositories.configuration.TransactionTypeCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionPriorityService {
    private final PriorityCodeRepository repository;
    private final TransactionTypeCodeRepository typeCodeRepository;

    public List<PriorityCodeResponse> getPriorityCodeList(TransactionPriorityType type) {
        List<PriorityCodeEntity> priorityCodeList = repository.findAllByTypeAndIsDeletedFalse(type);
        return priorityCodeList.stream().map(this::entityToPriorityCodeResponse).collect(Collectors.toList());
    }

    public PriorityCodeResponse getPriorityTypeByCode(String code) {
        Optional<PriorityCodeEntity> priorityCode = repository.findByCodeAndIsDeletedFalse(code);
        PriorityCodeResponse response = new PriorityCodeResponse();
        if (priorityCode.isPresent()) {
            return this.entityToPriorityCodeResponse(priorityCode.get());
        } else {
            return response;
        }
    }

    public List<TransactionTypeCodeResponse> getTransactionTypeCodeList() {
        List<TransactionTypeCodeEntity> typeCodeEntityList = typeCodeRepository.findAllByStatusIsTrueAndApiActivitiesStatusIsTrue();
        return typeCodeEntityList.stream().map(this::entityToTransactionTypeResponse).collect(Collectors.toList());
    }

    public TransactionTypeCodeResponse getDetailsByTransactionTypeCode(String code) {
        TransactionTypeCodeResponse response = null;
        List<TransactionTypeCodeEntity> typeCodeEntity = typeCodeRepository.findByCodeAndIsDeletedFalse(code);
        if (!typeCodeEntity.isEmpty()) {
            TransactionTypeCodeEntity codeEntity = typeCodeEntity.get(0);
            response = this.entityToTransactionTypeResponse(codeEntity);
        }
        return response;
    }
    public TransactionTypeCodeResponse getDetailsByTransactionTypeCodeAndMessageType(String code, String messageType, String deliveryChannel) {
        TransactionTypeCodeResponse response = null;
        List<TransactionTypeCodeEntity> typeCodeEntity = null;
        if(Strings.isNotBlank(code)  && Strings.isNotBlank(messageType) && Strings.isNotBlank(deliveryChannel)){
            typeCodeEntity = typeCodeRepository.findByCodeAndMessageTypeAndDeliveryChannelAndIsDeletedFalse(code, messageType, deliveryChannel);
        }else if(Strings.isNotBlank(code)  && Strings.isNotBlank(messageType) && Strings.isBlank(deliveryChannel)){
            typeCodeEntity = typeCodeRepository.findByCodeAndMessageTypeAndIsDeletedFalse(code, messageType);
        }else if(Strings.isNotBlank(code) && Strings.isBlank(messageType) && Strings.isBlank(deliveryChannel)){
            typeCodeEntity = typeCodeRepository.findByCodeAndIsDeletedFalse(code);
        }
        if (!typeCodeEntity.isEmpty()) {
            TransactionTypeCodeEntity codeEntity = typeCodeEntity.get(0);
            response = this.entityToTransactionTypeResponse(codeEntity);
        }
        return response;
    }

    private PriorityCodeResponse entityToPriorityCodeResponse(PriorityCodeEntity entity) {
        return new PriorityCodeResponse()
                .setId(entity.getId())
                .setCode(entity.getCode())
                .setType(entity.getType());
    }

    private TransactionTypeCodeResponse entityToTransactionTypeResponse(TransactionTypeCodeEntity entity) {
        return new TransactionTypeCodeResponse()
                .setId(entity.getId())
                .setCode(entity.getCode())
                .setDescription(entity.getDescription())
                .setBatchTransaction(entity.getBatchTransaction())
                .setStatus(entity.getStatus())
                .setBalanceValidation(entity.getBalanceValidation())
                .setChargeApplicable(entity.getChargeApplicable())
                .setApiActivitiesStatus(entity.getApiActivitiesStatus())
                .setExtraEndTime(entity.getExtraEndTime())
                .setMessageType(entity.getMessageType())
                .setSiTransaction(entity.getSiTransaction());
    }

    public List<TransactionTypeCodeResponse> getSITransactionTypeCodeList() {
        List<TransactionTypeCodeEntity> typeCodeEntityList = typeCodeRepository.findAllByStatusIsTrueAndApiActivitiesStatusIsTrueAndSiTransactionIsTrue();
        return typeCodeEntityList.stream().map(this::entityToTransactionTypeResponse).collect(Collectors.toList());
    }
}
