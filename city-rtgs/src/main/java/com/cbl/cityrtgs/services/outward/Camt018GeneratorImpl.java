package com.cbl.cityrtgs.services.outward;

import com.cbl.cityrtgs.models.dto.businessinfo.BusinessDayInfoRequest;
import com.cbl.cityrtgs.common.utility.DateUtility;
import iso20022.swift.xsd.camt_018_001.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class Camt018GeneratorImpl implements Camt018Generator {
    public Document createMessage(BusinessDayInfoRequest messageData) {
        GetBusinessDayInformationV03CMA getBizDayInf = new GetBusinessDayInformationV03CMA();
        getBizDayInf.setMsgHdr(this.createMsgHdr(messageData));
        getBizDayInf.setBizDayInfQryDef(this.createBizDayInfQryDef(messageData));
        Document doc = new Document();
        doc.setGetBizDayInf(getBizDayInf);
        return doc;
    }

    private CAMT018MessageHeader2CMA createMsgHdr(BusinessDayInfoRequest messageData) {
        CAMT018MessageHeader2CMA msgHdr = new CAMT018MessageHeader2CMA();
        msgHdr.setMsgId(messageData.getMsgId());
        msgHdr.setCreDtTm(DateUtility.creDtTm());
        return msgHdr;
    }

    private BusinessDayQuery1CMA createBizDayInfQryDef(BusinessDayInfoRequest messageData) {
        BusinessDayQuery1CMA bizDayInfQryDef = new BusinessDayQuery1CMA();
        bizDayInfQryDef.setCrit(new BusinessDayCriteria2Choice());
        bizDayInfQryDef.getCrit().setNewCrit(new BusinessDayCriteria1());
        bizDayInfQryDef.getCrit().getNewCrit().setSchCrit(new BusinessDaySearchCriteria1());
        bizDayInfQryDef.getCrit().getNewCrit().getSchCrit().setEvtTp(new SystemEventType2Choice());
        bizDayInfQryDef.getCrit().getNewCrit().getSchCrit().getEvtTp().setPrtry(new GenericIdentification1());
        bizDayInfQryDef.getCrit().getNewCrit().getSchCrit().getEvtTp().getPrtry().setId(messageData.getEventTypeCode().name());
        return bizDayInfQryDef;
    }

}
