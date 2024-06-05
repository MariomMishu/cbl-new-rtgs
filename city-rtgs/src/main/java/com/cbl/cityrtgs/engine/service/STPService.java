package com.cbl.cityrtgs.engine.service;


import com.cbl.cityrtgs.engine.dto.stpa.SendResponse;
import com.cbl.cityrtgs.engine.dto.stpa.SendT;
import com.cbl.cityrtgs.models.dto.message.MessageLogDTO;
import com.cbl.cityrtgs.engine.dto.response.STPResponse;

public interface STPService {

    STPResponse invokeSTPService(MessageLogDTO messageLogDTO);

    SendResponse createResponse(SendT request);
}