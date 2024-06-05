package com.cbl.cityrtgs.services.inward.service;

import com.cbl.cityrtgs.models.dto.message.MessageDefinitionIdentifier;
import com.cbl.cityrtgs.models.dto.message.MessageProcessStatus;
import com.cbl.cityrtgs.models.entitymodels.configuration.TxnCfgSetupEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.AccountReportEntity;
import com.cbl.cityrtgs.repositories.configuration.CurrencyRepository;
import com.cbl.cityrtgs.repositories.configuration.TxnCfgSetupRepository;
import com.cbl.cityrtgs.repositories.message.MsgLogRepository;
import com.cbl.cityrtgs.repositories.transaction.AccountReportRepository;
import com.cbl.cityrtgs.services.inward.factory.Inward;
import iso20022.iso.std.iso._20022.tech.xsd.camt_052_001.AccountReport16CMA;
import iso20022.iso.std.iso._20022.tech.xsd.camt_052_001.CashBalance3;
import iso20022.iso.std.iso._20022.tech.xsd.camt_052_001.Document;
import iso20022.iso.std.iso._20022.tech.xsd.camt_052_001.GroupHeader58;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@NoArgsConstructor
@Service
public class Camt05200104Inward implements Inward {
    private TxnCfgSetupRepository transactionCfgRepository;
    private AccountReportRepository accountReportRepository;
    private MsgLogRepository msgLogRepository;
    private CurrencyRepository currencyRepository;

    @Autowired
    public Camt05200104Inward(AccountReportRepository accountReportRepository,
                              MsgLogRepository msgLogRepository,
                              CurrencyRepository currencyRepository,
                              TxnCfgSetupRepository transactionCfgRepository) {
        this.accountReportRepository = accountReportRepository;
        this.msgLogRepository = msgLogRepository;
        this.currencyRepository = currencyRepository;
        this.transactionCfgRepository = transactionCfgRepository;
    }

    @Override
    public String getServiceType() {
        return MessageDefinitionIdentifier.CAMT052.value();
    }

    @Override
    public void processInward(long id, Object doc) {

        log.info("----------- Camt05200103Inward --------------");
        Document document = (iso20022.iso.std.iso._20022.tech.xsd.camt_052_001.Document) doc;


        try {
            GroupHeader58 grpHdr = document.getBkToCstmrAcctRpt().getGrpHdr();
            List<AccountReport16CMA> reports = document.getBkToCstmrAcctRpt().getRpt();
            List<AccountReportEntity> accountReports = new ArrayList<>();

            for (AccountReport16CMA accountReport16 : reports) {
                AccountReportEntity acctReport = new AccountReportEntity();

                acctReport.setMessageID(grpHdr.getMsgId());
                acctReport.setReportID(accountReport16.getId());
                acctReport.setElectronicSeqNumber(accountReport16.getElctrncSeqNb());
                acctReport.setCreateDate(accountReport16.getCreDtTm().toGregorianCalendar().getTime());
                acctReport.setCreateTime(accountReport16.getCreDtTm().toGregorianCalendar().getTime());
                acctReport.setAccountNumber(accountReport16.getAcct().getId().getOthr().getId());
                acctReport.setAccountOwner(accountReport16.getAcct().getOwnr().getId().getOrgId().getAnyBIC());

                for (CashBalance3 bal : accountReport16.getBal()) {
                    switch (bal.getTp().getCdOrPrtry().getCd()) {
                        case CLAV -> acctReport.setClosingAvailableBalance(bal.getAmt().getValue());
                        case FWAV -> acctReport.setForwardAvailableBalance(bal.getAmt().getValue());
                        case CLBD -> acctReport.setClosingBookedBalance(bal.getAmt().getValue());
                        case OPBD -> acctReport.setOpeningBookedBalance(bal.getAmt().getValue());
                    }
                    this.outwardTxnInactive(bal.getAmt().getCcy());
                    acctReport.setCurrancyCode(bal.getAmt().getCcy());
                    acctReport.setDebitCreditIndicator(bal.getCdtDbtInd().name());
                }

                if (accountReport16.getTxsSummry() != null) {
                    acctReport.setTotalCreditEntries(accountReport16.getTxsSummry().getTtlCdtNtries().getNbOfNtries());
                    acctReport.setCreditSum(accountReport16.getTxsSummry().getTtlCdtNtries().getSum());
                    acctReport.setTotalDebitEntries(accountReport16.getTxsSummry().getTtlDbtNtries().getNbOfNtries());
                    acctReport.setDebitSum(accountReport16.getTxsSummry().getTtlDbtNtries().getSum());
                }
                accountReports.add(acctReport);
            }

            try {
                accountReportRepository.saveAll(accountReports);
                msgLogRepository.updateMsgLogEntityStatus(id, String.valueOf(MessageProcessStatus.PROCESSED));
            } catch (Exception e) {
                log.error("{}", e.getMessage());
                msgLogRepository.updateMsgLogEntityStatus(id, String.valueOf(MessageProcessStatus.UNPROCESSED));
                throw new RuntimeException(e.getMessage());
            }

        } catch (Exception e) {
            log.error("handle camt052 inward Message(): {}", e.getMessage());
        }
    }

    public void outwardTxnInactive(String currencyCode) {

        try {
            var optionalCurrency = currencyRepository.findByShortCodeAndIsDeletedFalse(currencyCode);
            if(optionalCurrency.isPresent()){
                Optional<TxnCfgSetupEntity> optional = transactionCfgRepository.findByCurrencyIdAndIsDeletedFalse(optionalCurrency.get().getId());

                if(optional.isPresent()){

                    TxnCfgSetupEntity txnCfgSetupEntity = optional.get();
                    txnCfgSetupEntity.setTxnActive(false);
                    txnCfgSetupEntity.setId(txnCfgSetupEntity.getId());
                    transactionCfgRepository.save(txnCfgSetupEntity);
                    log.info("Outward Transaction configuration updated successfully for : {}", currencyCode);
                }
                else{
                    log.error("Outward configuration not found. Please contact the admin");
                }
            }

        } catch (Exception e) {
            log.error("Outward Transaction configuration updated failed: {}", e.getMessage());
        }
    }
}
