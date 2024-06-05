package com.cbl.cityrtgs.services;

import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.businessinfo.BusinessDayInformationMapper;
import com.cbl.cityrtgs.models.dto.businessinfo.BusinessDayInfoRequest;
import com.cbl.cityrtgs.models.dto.businessinfo.BusinessDayInfoResponse;
import com.cbl.cityrtgs.models.dto.businessinfo.EventTypeCode;
import com.cbl.cityrtgs.models.dto.businessinfo.ReturnBusinessDayInfoResponse;
import com.cbl.cityrtgs.models.entitymodels.businessday.BusinessDayInformationEntity;
import com.cbl.cityrtgs.models.entitymodels.businessday.GetBusinessDayEntity;
import com.cbl.cityrtgs.repositories.businessday.BusinessDayInformationRepository;
import com.cbl.cityrtgs.repositories.businessday.GetBusinessDayRepository;
import com.cbl.cityrtgs.services.transaction.MessageGenerateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BusinessDayInformationService {

    private final BusinessDayInformationMapper mapper;
    private final BusinessDayInformationRepository repository;
    private final GetBusinessDayRepository getBusinessDayRepository;
    private final MessageGenerateService messageGenerateService;

    LocalDateTime dateTime = LocalDateTime.now();

    public BusinessDayInfoResponse sendRequestForBizDayInfo() {
        BusinessDayInfoRequest bizDayInf = new BusinessDayInfoRequest();
        bizDayInf.setCreateDate(dateTime);
        bizDayInf.setCreateTime(dateTime);
        bizDayInf.setEventTypeCode(EventTypeCode.GETBUSINESSDAYPERIOD);
        BusinessDayInfoResponse response = new BusinessDayInfoResponse();
        if (messageGenerateService.processCAMT018OutwardRequest(bizDayInf) != null) {
            GetBusinessDayEntity entity = mapper.domainToEntity(bizDayInf);
            try {
                entity = getBusinessDayRepository.save(entity);
                response = mapper.entityToResponse(entity);
                response.setIsError(false);
                return response;
            } catch (Exception e) {
                log.error("Error saving Business Day Info: {}", e.getMessage());
            }
        }
        response.setIsError(true);
        response.setErrorMessage("Business Day Info: Error");
        return response;
    }

    public ReturnBusinessDayInfoResponse getByOriginalMsgId(String originalMsgId) {
        Optional<BusinessDayInformationEntity> optional = repository.findByOriginalMsgId(originalMsgId);
        if (optional.isPresent()) {
            BusinessDayInformationEntity entity = optional.get();
            return mapper.entityToReturnResponse(entity);
        }
        return null;
    }

    public ReturnBusinessDayInfoResponse getLatestReturnBizDayInfo() {
        BusinessDayInformationEntity entity = repository.findByOrderByIdDesc()
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Request not found"));
        ReturnBusinessDayInfoResponse response = mapper.entityToReturnResponse(entity);
        return response;
    }

    public ReturnBusinessDayInfoResponse getSentBizDayInfo() {
        ReturnBusinessDayInfoResponse response = new ReturnBusinessDayInfoResponse();
        BusinessDayInformationEntity entity = repository.getByTodayDesc();
        if (entity != null) {
            response = mapper.entityToReturnResponse(entity);
        }
        return response;
    }

}

