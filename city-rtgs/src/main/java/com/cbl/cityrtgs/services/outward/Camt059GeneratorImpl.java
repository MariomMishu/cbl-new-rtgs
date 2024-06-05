package com.cbl.cityrtgs.services.outward;

import com.cbl.cityrtgs.common.utility.DateUtility;
import com.cbl.cityrtgs.models.dto.businessinfo.BusinessDayInfoRequest;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.BankFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.InterBankTransferEntity;
import iso20022.iso.std.iso._20022.tech.xsd.camt_059_001.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class Camt059GeneratorImpl implements Camt059Generator {
    public Document createMessage(InterBankTransferEntity outB2B, BankFndTransferEntity outB2BTxn) {
        NotificationToReceiveStatusReportV08 ntfctnToRcvStsRpt = new NotificationToReceiveStatusReportV08();
        ntfctnToRcvStsRpt.setGrpHdr(this.createGrpHdr(outB2B));
        ntfctnToRcvStsRpt.setOrgnlNtfctnAndSts(this.createOriginalNotification15(outB2B));
        Document doc = new Document();
        doc.setNtfctnToRcvStsRpt(ntfctnToRcvStsRpt);
        //  doc.getFICdtTrf().setCdtTrfTxInf(this.createTransactionEntry(outB2B, outB2BTxn));
        return doc;
    }

    private GroupHeader121 createGrpHdr(InterBankTransferEntity outB2B) {
        GroupHeader121 grpHdr = new GroupHeader121();
        grpHdr.setMsgId(outB2B.getMsgId());
        grpHdr.setCreDtTm(DateUtility.creDtTm());
        Party50Choice choice = new Party50Choice();
        BranchAndFinancialInstitutionIdentification8 identification8 = new BranchAndFinancialInstitutionIdentification8();
        BranchData5 branchData5 = new BranchData5();
        branchData5.setId("1234");
        branchData5.setLEI("Test");
        branchData5.setNm("Branch");
        PostalAddress27 address27 = new PostalAddress27();
        branchData5.setPstlAdr(address27);
        identification8.setBrnchId(branchData5);
        FinancialInstitutionIdentification23 identification23 = new FinancialInstitutionIdentification23();
        identification8.setFinInstnId(identification23);
        PartyIdentification272 identification272 = new PartyIdentification272();
        identification272.setNm("");
        // identification272.setPstlAdr("");
        identification272.setCtryOfRes("");
        // identification272.setCtctDtls("");
        choice.setAgt(identification8);
        choice.setPty(identification272);
        grpHdr.setMsgRcpt(choice);
        return grpHdr;
    }

    private OriginalNotification15 createOriginalNotification15(InterBankTransferEntity outB2B) {
        OriginalNotification15 notification15 = new OriginalNotification15();
        notification15.setOrgnlMsgId(outB2B.getMsgId());
        notification15.setOrgnlCreDtTm(DateUtility.creDtTm());
        notification15.setOrgnlNtfctnId("1");
        notification15.setNtfctnSts(NotificationStatus3Code.fromValue(""));
        notification15.setAddtlStsInf("");
        return notification15;
    }

    @Override
    public iso20022.swift.xsd.camt_018_001.Document createMessage(BusinessDayInfoRequest businessDayInfoRequest) {
        return null;
    }
}
