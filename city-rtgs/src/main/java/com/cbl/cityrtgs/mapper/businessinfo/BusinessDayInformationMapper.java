package com.cbl.cityrtgs.mapper.businessinfo;

import com.cbl.cityrtgs.models.dto.businessinfo.*;
import com.cbl.cityrtgs.models.entitymodels.businessday.BusinessDayEventEntity;
import com.cbl.cityrtgs.models.entitymodels.businessday.BusinessDayInformationEntity;
import com.cbl.cityrtgs.models.entitymodels.businessday.GetBusinessDayEntity;
import com.cbl.cityrtgs.repositories.businessday.BusinessDayEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class BusinessDayInformationMapper {
    private final BusinessDayEventRepository eventRepository;

    public GetBusinessDayEntity domainToEntity(BusinessDayInfoRequest request) {
        GetBusinessDayEntity entity = new GetBusinessDayEntity();
        BeanUtils.copyProperties(request, entity);
        return entity;
    }

    public BusinessDayInfoResponse entityToResponse(GetBusinessDayEntity entity) {
        BusinessDayInfoResponse response = new BusinessDayInfoResponse();
        BeanUtils.copyProperties(entity, response);
        response.setEventTypeCode(EventTypeCode.GETBUSINESSDAYPERIOD);
        return response;
    }

    public ReturnBusinessDayInfoResponse entityToReturnResponse(BusinessDayInformationEntity entity) {
        ReturnBusinessDayInfoResponse response = new ReturnBusinessDayInfoResponse();
        BeanUtils.copyProperties(entity, response);
        response.setBizDayEvt(this.entityToReturnEvtResponse(entity.getId()));
        return response;
    }

    public List<BusinessDayEventResponse> entityToReturnEvtResponse(Long bizDayInf) {
        List<BusinessDayEventEntity> entityList = eventRepository.findAllByBizDayInf_Id(bizDayInf);
        List<BusinessDayEventResponse> responses = new ArrayList<>();
        entityList.forEach(e -> {
            BusinessDayEventResponse response = new BusinessDayEventResponse();
            response.setEventType(e.getEventType());
            response.setStartDate(e.getStartDate());
            response.setEndDate(e.getEndDate());
            response.setStartTime(e.getStartTime());
            response.setEndTime(e.getEndTime());
            responses.add(response);
        });

        return responses;
    }
}
