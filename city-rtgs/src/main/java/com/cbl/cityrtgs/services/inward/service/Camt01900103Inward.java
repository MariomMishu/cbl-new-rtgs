package com.cbl.cityrtgs.services.inward.service;

import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.message.MessageDefinitionIdentifier;
import com.cbl.cityrtgs.models.dto.message.MessageProcessStatus;
import com.cbl.cityrtgs.models.entitymodels.businessday.BusinessDayEventEntity;
import com.cbl.cityrtgs.models.entitymodels.businessday.BusinessDayInformationEntity;
import com.cbl.cityrtgs.models.entitymodels.businessday.EventType;
import com.cbl.cityrtgs.models.entitymodels.messagelog.InOutMsgLogEntity;
import com.cbl.cityrtgs.repositories.businessday.BusinessDayInformationRepository;
import com.cbl.cityrtgs.repositories.message.InOutMsgLogRepository;
import com.cbl.cityrtgs.repositories.message.MsgLogRepository;
import com.cbl.cityrtgs.services.inward.factory.Inward;
import iso20022.swift.xsd.camt_019_001.CAMT019MessageHeader3CMA;
import iso20022.swift.xsd.camt_019_001.Document;
import iso20022.swift.xsd.camt_019_001.SystemEvent2CMA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class Camt01900103Inward implements Inward {

    private final BusinessDayInformationRepository businessDayInformationRepository;
    private final InOutMsgLogRepository inOutMsgLogRepository;
    private final MsgLogRepository msgLogRepository;

    @Autowired
    public Camt01900103Inward(BusinessDayInformationRepository businessDayInformationRepository,
                              InOutMsgLogRepository inOutMsgLogRepository,
                              MsgLogRepository msgLogRepository) {
        this.inOutMsgLogRepository = inOutMsgLogRepository;
        this.businessDayInformationRepository = businessDayInformationRepository;
        this.msgLogRepository = msgLogRepository;
    }

    @Override
    public String getServiceType() {
        return MessageDefinitionIdentifier.CAMT019.value();
    }

    @Override
    public void processInward(long id, Object doc) {

        log.info("----------- Camt01900103Inward --------------");

        Document document = (iso20022.swift.xsd.camt_019_001.Document) doc;

        try {
            BusinessDayInformationEntity businessDayInfo = new BusinessDayInformationEntity();
            CAMT019MessageHeader3CMA msgHdr = document.getRtrBizDayInf().getMsgHdr();
            List<SystemEvent2CMA> systemEvent2s = (document
                    .getRtrBizDayInf()
                    .getRptOrErr()
                    .getBizRpt()
                    .get(0))
                    .getBizDayOrErr()
                    .getBizDayInf()
                    .getSysInfPerCcy()
                    .getEvt();
            businessDayInfo.setMsgId(msgHdr.getMsgId());
            businessDayInfo.setCreateDate(msgHdr.getCreDtTm().toGregorianCalendar().getTime());
            businessDayInfo.setCreateTime(msgHdr.getCreDtTm().toGregorianCalendar().getTime());
            if (msgHdr.getOrgnlBizQry() != null) {
                businessDayInfo.setOriginalMsgId(msgHdr.getOrgnlBizQry().getMsgId());
                businessDayInfo.setOriginalCreateDate(msgHdr.getOrgnlBizQry().getCreDtTm().toGregorianCalendar().getTime());
                businessDayInfo.setOriginalCreateTime(msgHdr.getOrgnlBizQry().getCreDtTm().toGregorianCalendar().getTime());
            }

            businessDayInfo.setBizDayEvt(new ArrayList<>());
            Iterator<SystemEvent2CMA> systemEventList = systemEvent2s.iterator();

            while (true) {
                SystemEvent2CMA systemEvent2;
                do {
                    if (!systemEventList.hasNext()) {
                        InOutMsgLogEntity inOutMsgLog = new InOutMsgLogEntity();
                        inOutMsgLog.setMsgCreationDate(new Date());
                        if (msgHdr.getOrgnlBizQry() != null) {
                            inOutMsgLog.setMsgId(msgHdr.getOrgnlBizQry().getMsgId());
                        }

                        inOutMsgLog.setResponseMsgId(msgHdr.getMsgId());
                        inOutMsgLog.setMsgType(MessageDefinitionIdentifier.CAMT018.value());
                        inOutMsgLog.setResponseMsgType(MessageDefinitionIdentifier.CAMT019.value());
                        inOutMsgLog.setRouteType(RoutingType.Incoming.toString());
                        inOutMsgLog.setCreatedAt(new Date());
                        try {
                            inOutMsgLogRepository.save(inOutMsgLog);
                            businessDayInformationRepository.save(businessDayInfo);
                            msgLogRepository.updateMsgLogEntityStatus(id, String.valueOf(MessageProcessStatus.PROCESSED));
                        } catch (Exception e) {
                            log.error("{}", e.getMessage());
                            msgLogRepository.updateMsgLogEntityStatus(id, String.valueOf(MessageProcessStatus.UNPROCESSED));
                            throw new RuntimeException(e.getMessage());
                        }

                        return;
                    }

                    systemEvent2 = systemEventList.next();
                } while (!systemEvent2.getTp().getPrtry().getId().equals(EventType.SYSTEMSTART.value()) &&
                        !systemEvent2.getTp().getPrtry().getId().equals(EventType.EXPERIODMORNING.value()) &&
                        !systemEvent2.getTp().getPrtry().getId().equals(EventType.EXPERIODAFTERNOON.value()) &&
                        !systemEvent2.getTp().getPrtry().getId().equals(EventType.STMTRPRTPARTICIPANTS.value()) &&
                        !systemEvent2.getTp().getPrtry().getId().equals(EventType.SYSTEMSTOP.value()));

                BusinessDayEventEntity businessDayEvent = new BusinessDayEventEntity();
                businessDayEvent.setEventType(systemEvent2.getTp().getPrtry().getId());
                businessDayEvent.setBizDayInf(businessDayInfo);

                if (systemEvent2.getStartTm() != null) {
                    businessDayEvent.setStartDate(systemEvent2.getStartTm().toGregorianCalendar().getTime());
                    businessDayEvent.setStartTime(systemEvent2.getStartTm().toGregorianCalendar().getTime());
                }

                if (systemEvent2.getEndTm() != null) {
                    businessDayEvent.setEndDate(systemEvent2.getEndTm().toGregorianCalendar().getTime());
                    businessDayEvent.setEndTime(systemEvent2.getEndTm().toGregorianCalendar().getTime());
                }
                businessDayInfo.getBizDayEvt().add(businessDayEvent);
            }
        } catch (Exception e) {
            log.error("handle camt019 inward Message(): {}", e.getMessage());
        }
    }
}
