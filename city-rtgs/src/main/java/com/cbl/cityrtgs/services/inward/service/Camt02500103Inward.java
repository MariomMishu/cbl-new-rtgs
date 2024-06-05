package com.cbl.cityrtgs.services.inward.service;

import com.cbl.cityrtgs.models.dto.message.MessageDefinitionIdentifier;
import com.cbl.cityrtgs.models.dto.message.MessageProcessStatus;
import com.cbl.cityrtgs.repositories.message.MsgLogRepository;
import com.cbl.cityrtgs.services.inward.factory.Inward;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Camt02500103Inward implements Inward {
    private final MsgLogRepository msgLogRepository;
    @Autowired
    public Camt02500103Inward(MsgLogRepository msgLogRepository){
       this.msgLogRepository=  msgLogRepository;
    }

    @Override
    public String getServiceType() {
        return MessageDefinitionIdentifier.CAMT025.value();
    }

    @Override
    public void processInward(long id, Object doc) {
        log.info("----------- Camt02500103Inward --------------");

        msgLogRepository.updateMsgLogEntityStatus(id, String.valueOf(MessageProcessStatus.PROCESSED));
    }
}
