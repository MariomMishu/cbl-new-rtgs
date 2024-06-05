package com.cbl.cityrtgs.engine.api;


import com.cbl.cityrtgs.engine.constant.Constant;
import com.cbl.cityrtgs.engine.dto.stpa.SendResponse;
import com.cbl.cityrtgs.engine.dto.stpa.SendT;
import com.cbl.cityrtgs.engine.service.STPService;
import com.cbl.cityrtgs.test.LogDataEntity;
import com.cbl.cityrtgs.test.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Slf4j
@Endpoint
@RequiredArgsConstructor
public class STPAEndpoint {

    private final STPService stpService;
    private final TestService testService;

    @PayloadRoot(namespace = Constant.NAMESPACE_URI, localPart = "send")
    @ResponsePayload
    public SendResponse send(@RequestPayload SendT request) {
        LogDataEntity entity = new LogDataEntity();
        entity.setMsgid(request.getMessage().getMsgNetMir());
        entity.setDatalog(request.getMessage().getMsgNetMir());
        entity.setLogtype("STP");
        testService.dataLogSave(entity);
        SendResponse response = stpService.createResponse(request);
        log.info("Response: {}", response);

        return response;
    }
}
