package com.cbl.cityrtgs.services.inward.service;

import com.cbl.cityrtgs.models.dto.message.MessageDefinitionIdentifier;
import com.cbl.cityrtgs.models.dto.message.MessageProcessStatus;
import com.cbl.cityrtgs.models.entitymodels.transaction.SettlementAccountStatementDetailEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.SettlementAccountStatementEntity;
import com.cbl.cityrtgs.repositories.message.MsgLogRepository;
import com.cbl.cityrtgs.services.inward.factory.Inward;
import com.cbl.cityrtgs.services.transaction.SettlementAccountStatementService;
import iso20022.iso.std.iso._20022.tech.xsd.camt_053_001.Document;
import iso20022.iso.std.iso._20022.tech.xsd.camt_053_001.AccountStatement4CMA;
import iso20022.iso.std.iso._20022.tech.xsd.camt_053_001.CAMT053CashBalance3;
import iso20022.iso.std.iso._20022.tech.xsd.camt_053_001.CAMT053ReportEntry4;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@NoArgsConstructor
@Service
public class Camt05300104Inward implements Inward {
    private SettlementAccountStatementService statementService;
    private MsgLogRepository msgLogRepository;

    @Autowired
    public Camt05300104Inward(SettlementAccountStatementService statementService,
                              MsgLogRepository msgLogRepository){
      this.statementService = statementService;
      this.msgLogRepository = msgLogRepository;
    }
    @Override
    public String getServiceType() {
        return MessageDefinitionIdentifier.CAMT053.value();
    }
    @Override
    public void processInward(long id, Object doc) {

        log.info("----------- Camt05300104Inward --------------");

        Document document = (iso20022.iso.std.iso._20022.tech.xsd.camt_053_001.Document) doc;

        List<SettlementAccountStatementEntity> statements = new ArrayList<>();
        Iterator<AccountStatement4CMA> accountStatement4CMAList = document.getBkToCstmrStmt().getStmt().iterator();

        while(true) {
            AccountStatement4CMA accStmt;
            SettlementAccountStatementEntity settlementAccountStatement;
            do {
                if (!accountStatement4CMAList.hasNext()) {

                    try {
                        statementService.saveAll(statements);
                        msgLogRepository.updateMsgLogEntityStatus(id, String.valueOf(MessageProcessStatus.PROCESSED));
                    }
                    catch (Exception e){
                        log.error("{}", e.getMessage());
                        msgLogRepository.updateMsgLogEntityStatus(id, String.valueOf(MessageProcessStatus.UNPROCESSED));
                        throw new RuntimeException(e.getMessage());
                    }

                    return;
                }

                accStmt = accountStatement4CMAList.next();
                settlementAccountStatement = new SettlementAccountStatementEntity();
                statements.add(settlementAccountStatement);
                settlementAccountStatement.setMessageID(document.getBkToCstmrStmt().getGrpHdr().getMsgId());
                settlementAccountStatement.setElectronicSeqNumber(accStmt.getElctrncSeqNb() == null ? 0 : accStmt.getElctrncSeqNb().intValue());
                settlementAccountStatement.setStatementID(accStmt.getId());
                settlementAccountStatement.setPageNumber(accStmt.getStmtPgntn().getPgNb());
                settlementAccountStatement.setLastPage(accStmt.getStmtPgntn().isLastPgInd());
                settlementAccountStatement.setCreateDate(accStmt.getCreDtTm().toGregorianCalendar().getTime());
                settlementAccountStatement.setCreateTime(accStmt.getCreDtTm().toGregorianCalendar().getTime());
                settlementAccountStatement.setAccountNumber(accStmt.getAcct().getId().getOthr().getId());
                settlementAccountStatement.setAccountOwner(accStmt.getAcct().getOwnr().getId().getOrgId().getAnyBIC());

                for (CAMT053CashBalance3 bal : accStmt.getBal()) {

                    switch (bal.getTp().getCdOrPrtry().getCd()) {
                        case CLAV -> settlementAccountStatement.setClosingAvailableBalance(bal.getAmt().getValue());
                        case CLBD -> settlementAccountStatement.setClosingBookedBalance(bal.getAmt().getValue());
                        case PRCD -> settlementAccountStatement.setPreviouslyClosedBookedBalance(bal.getAmt().getValue());
                    }

                    settlementAccountStatement.setCurrancyCode(bal.getAmt().getCcy());
                    settlementAccountStatement.setDebitCreditIndicator(bal.getCdtDbtInd().name());
                }

            } while(accStmt.getNtry() == null);

            List<SettlementAccountStatementDetailEntity> details = new ArrayList<>();

            for (CAMT053ReportEntry4 entry : accStmt.getNtry()) {
                SettlementAccountStatementDetailEntity detail = createSettlementAccountStatementDetail(entry);
                details.add(detail);
            }
            settlementAccountStatement.setDetails(details);
        }
    }

    private SettlementAccountStatementDetailEntity createSettlementAccountStatementDetail(CAMT053ReportEntry4 entry) {

        SettlementAccountStatementDetailEntity detail = new SettlementAccountStatementDetailEntity();
        detail.setTransactionRef(entry.getNtryRef());

        switch (entry.getCdtDbtInd()) {
            case CRDT -> detail.setCreditAmount(entry.getAmt().getValue());
            case DBIT -> detail.setDebitAmount(entry.getAmt().getValue());
        }
        detail.setTransactionCode(entry.getBkTxCd().getPrtry().getCd());
        detail.setTransactionStatus(entry.getSts().name());
        detail.setBookingDate(entry.getBookgDt() != null ? entry.getBookgDt().getDt().toGregorianCalendar().getTime() : null);
        detail.setValueDate(entry.getValDt() != null ? entry.getValDt().getDt().toGregorianCalendar().getTime() : null);
        detail.setTransactionId(entry.getNtryDtls().getTxDtls().getRefs().getTxId());

        if (entry.getNtryDtls().getBtch() != null) {
            detail.setBatch(true);
            detail.setBatchMessageId(entry.getNtryDtls().getBtch().getMsgId());
        }
        return detail;
    }
}
