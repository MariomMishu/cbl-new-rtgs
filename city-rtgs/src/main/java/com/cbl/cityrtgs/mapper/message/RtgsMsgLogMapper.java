package com.cbl.cityrtgs.mapper.message;

import com.cbl.cityrtgs.models.dto.message.MsgLogResponse;
import com.cbl.cityrtgs.models.entitymodels.messagelog.MsgLogEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * Responsibility:
 *
 * @author Mariom Mishu
 * @since 07/03/23
 */
@Component
@RequiredArgsConstructor
public class RtgsMsgLogMapper {

    public MsgLogResponse entityToResponse(MsgLogEntity entity) {
        MsgLogResponse response = new MsgLogResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

}
