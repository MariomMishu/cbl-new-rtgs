package com.cbl.cityrtgs.services.inward.service;

import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.message.MessageDefinitionIdentifier;
import com.cbl.cityrtgs.models.dto.message.MessageProcessStatus;
import com.cbl.cityrtgs.models.dto.transaction.TransactionStatus;
import com.cbl.cityrtgs.models.dto.transaction.TransactionVerificationStatus;
import com.cbl.cityrtgs.models.entitymodels.messagelog.InOutMsgLogEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.BankFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.InterBankTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.InterCustomerFundTransferEntity;
import com.cbl.cityrtgs.repositories.message.InOutMsgLogRepository;
import com.cbl.cityrtgs.repositories.transaction.b2b.BankFndTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.b2b.InterBankFundTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.CustomerFndTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.InterCustomerFundTransferRepository;
import com.cbl.cityrtgs.services.inward.factory.Inward;
import com.cbl.cityrtgs.services.transaction.MsgLogService;
import com.cbl.cityrtgs.services.transaction.b2b.BankFundTransferService;
import com.cbl.cityrtgs.services.transaction.c2c.CustomerFundTransferService;
import com.cbl.cityrtgs.services.transaction.c2c.IbFundTransferService;
import com.cbl.cityrtgs.test.TestService;
import iso20022.iso.std.iso._20022.tech.xsd.camt_054_008.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class Camt05400104Inward implements Inward {
    private final InOutMsgLogRepository inOutMsgLogRepository;
    private final InterBankFundTransferRepository interBankFundTransferRepository;
    private final InterCustomerFundTransferRepository interCustomerFundTransferRepository;
    private final BankFndTransferRepository bankFndTransferRepository;
    private final BankFundTransferService bankFundTransferService;
    private final CustomerFundTransferService customerFundTransferService;
    private final CustomerFndTransferRepository customerFndTransferRepository;
    private final MsgLogService msgLogService;
    private final IbFundTransferService ibFundTransferService;
    private final TestService testService;

    @Autowired
    public Camt05400104Inward(InOutMsgLogRepository inOutMsgLogRepository,
                              InterBankFundTransferRepository interBankFundTransferRepository,
                              InterCustomerFundTransferRepository interCustomerFundTransferRepository,
                              BankFndTransferRepository bankFndTransferRepository,
                              BankFundTransferService bankFundTransferService,
                              CustomerFundTransferService customerFundTransferService,
                              CustomerFndTransferRepository customerFndTransferRepository,
                              MsgLogService msgLogService,
                              IbFundTransferService ibFundTransferService, TestService testService) {
        this.inOutMsgLogRepository = inOutMsgLogRepository;
        this.interBankFundTransferRepository = interBankFundTransferRepository;
        this.interCustomerFundTransferRepository = interCustomerFundTransferRepository;
        this.bankFndTransferRepository = bankFndTransferRepository;
        this.bankFundTransferService = bankFundTransferService;
        this.customerFundTransferService = customerFundTransferService;
        this.customerFndTransferRepository = customerFndTransferRepository;
        this.msgLogService = msgLogService;
        this.ibFundTransferService = ibFundTransferService;
        this.testService = testService;
    }

    @Override
    public String getServiceType() {
        return MessageDefinitionIdentifier.CAMT054.value();
    }

    @Override
    public void processInward(long id, Object doc) {

        log.info("----------- Camt05400104Inward --------------");

        Document document = (iso20022.iso.std.iso._20022.tech.xsd.camt_054_008.Document) doc;
        AccountNotification171 ntfctn = document.getBkToCstmrDbtCdtNtfctn().getNtfctn();
        ReportEntry101 entry = ntfctn.getNtry();
        EntryTransaction101 txDtls = entry.getNtryDtls().getTxDtls();

        if (txDtls.getCdtDbtInd() == CreditDebitCode.DBIT) {
            Optional<InOutMsgLogEntity> inOutMsgLogEntity = inOutMsgLogRepository.getInOutMsgLogByTxnRefAndDateAndRouTypeOutgoing(txDtls.getRefs().getTxId());
            log.info("Handle 54: Inout request: " + txDtls.getRefs().getTxId() + "," + ntfctn.getCreDtTm().toGregorianCalendar().getTime());
            if (inOutMsgLogEntity.isPresent()) {
                InOutMsgLogEntity inOutMsgLog = inOutMsgLogEntity.get();
                if (inOutMsgLog.getMsgType().equals(MessageDefinitionIdentifier.PACS009.value())) {
                    Optional<InterBankTransferEntity> _interBankTransferEntity = interBankFundTransferRepository.findByBatchNumberAndRoutingTypeAndIsDeletedFalse(inOutMsgLog.getBatchNumber(), RoutingType.Outgoing);
                    if (_interBankTransferEntity.isPresent()) {
                        InterBankTransferEntity interBankTransferEntity = _interBankTransferEntity.get();
                        Optional<BankFndTransferEntity> _bankFndTransferEntity = bankFndTransferRepository.findByTransactionsAndParentBatchNumberAndIsDeletedFalse(interBankTransferEntity.getId(), interBankTransferEntity.getBatchNumber());
                        if (_bankFndTransferEntity.isPresent()) {
                            if (Objects.equals(entry.getSts().getCd(), "BOOK")) {
                                try {
                                    bankFundTransferService.updateStatus(interBankTransferEntity, _bankFndTransferEntity.get(), TransactionStatus.Confirmed, entry.getNtryRef());
                                    msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.PROCESSED), "");
                                } catch (Exception e) {
                                    log.error("{}", e.getMessage());
                                    msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.UNPROCESSED), e.getMessage());
                                }
                            } else if (Objects.equals(entry.getSts().getCd(), "PDNG")) {
                                try {
                                    bankFundTransferService.updateStatus(interBankTransferEntity, _bankFndTransferEntity.get(), TransactionStatus.Pending, entry.getNtryRef());
                                    msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.PROCESSED), "");
                                } catch (Exception e) {
                                    log.error("{}", e.getMessage());
                                    msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.UNPROCESSED), e.getMessage());
                                }
                            }
                        }
                    } else {
                        log.error("{}", "Bank to Bank Txn Not Found");
                        msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.UNPROCESSED), "Bank to Bank Txn Not Found");
                    }
                } else if (inOutMsgLog.getMsgType().equals(MessageDefinitionIdentifier.PACS008.value())) {
                    Optional<CustomerFndTransferEntity> _customerFndTransferEntity = customerFndTransferRepository.findByReferenceNumberAndRoutingType(inOutMsgLog.getTxnReferenceNumber(), RoutingType.Outgoing);
                    if (_customerFndTransferEntity.isPresent()) {
                        Optional<InterCustomerFundTransferEntity> _interCustomerFundTransferEntity = interCustomerFundTransferRepository.findByBatchNumberAndRoutingTypeAndIsDeletedFalse(inOutMsgLog.getBatchNumber(), RoutingType.Outgoing);
                        InterCustomerFundTransferEntity interCustomerFundTransferEntity = _interCustomerFundTransferEntity.get();
                        if (Objects.equals(entry.getSts().getCd(), "BOOK")) {
                            try {
                                customerFundTransferService.updateStatus(interCustomerFundTransferEntity, _customerFndTransferEntity.get(), TransactionStatus.Confirmed, entry.getNtryRef());
                                msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.PROCESSED), "");
                            } catch (Exception e) {
                                log.error("{}", e.getMessage());
                                msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.UNPROCESSED), e.getMessage());
                            }
                        } else if (Objects.equals(entry.getSts().getCd(), "PDNG")) {
                            try {
                                customerFundTransferService.updateStatus(interCustomerFundTransferEntity, _customerFndTransferEntity.get(), TransactionStatus.Pending, entry.getNtryRef());
                                msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.PROCESSED), "");
                            } catch (Exception e) {
                                log.error("{}", e.getMessage());
                                msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.UNPROCESSED), e.getMessage());
                            }
                        }
                        if (_customerFndTransferEntity.get().isIbTxn()) {
                            ibFundTransferService.updateIbTxn(_customerFndTransferEntity.get().getReferenceNumber(), TransactionStatus.Confirmed.toString(), false, "");
                        }
                    } else {
                        log.error("{}", "Customer Txn Not Found");
                        msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.UNPROCESSED), "Customer Txn Not Found");
                    }
                }
            } else {
                Optional<InOutMsgLogEntity> inOutMsgPacs004 = inOutMsgLogRepository.getInOutMsgLogByTxnRefAndDateAndRouTypeIncoming(txDtls.getRefs().getTxId());
                if (inOutMsgPacs004.isPresent()) {
                    msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.PROCESSED), "");
                } else {
                    msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.CANCELED), "");
                }
            }
        } else if (txDtls.getCdtDbtInd() == CreditDebitCode.CRDT) {
            Optional<InOutMsgLogEntity> inOutMsgLogEntity = inOutMsgLogRepository.getInOutMsgLogByTxnRefAndDateAndRouTypeIncoming(txDtls.getRefs().getTxId());
            if (inOutMsgLogEntity.isPresent()) {
                InOutMsgLogEntity inOutMsgLog = inOutMsgLogEntity.get();
                if (inOutMsgLog.getMsgType().equals(MessageDefinitionIdentifier.PACS008.value())) {
                    Optional<InterCustomerFundTransferEntity> _interCustomerFundTransferEntity = interCustomerFundTransferRepository.findByBatchNumberAndRoutingTypeAndIsDeletedFalse(inOutMsgLog.getBatchNumber(), RoutingType.Incoming);
                    if (_interCustomerFundTransferEntity.isPresent()) {
                        InterCustomerFundTransferEntity interCustomerFundTransfer = _interCustomerFundTransferEntity.get();
                        Optional<CustomerFndTransferEntity> _customerFndTransferEntity = customerFndTransferRepository.findByTransactionsAndReferenceNumber(interCustomerFundTransfer.getId(), inOutMsgLog.getTxnReferenceNumber());
                        if (_customerFndTransferEntity.isPresent()) {
                            CustomerFndTransferEntity customerFndTransferEntity = _customerFndTransferEntity.get();
                            if (interCustomerFundTransfer.getVerificationStatus() == 3 && interCustomerFundTransfer.getTxnVerificationStatus().equals(TransactionVerificationStatus.Approved)) {
                                if (Objects.equals(entry.getSts().getCd(), "BOOK")) {
                                    try {
                                        customerFundTransferService.approveInwardTransaction(interCustomerFundTransfer, customerFndTransferEntity);
                                        msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.PROCESSED), "");
                                    } catch (Exception e) {
                                        log.error("{}", e.getMessage());
                                        msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.UNPROCESSED), e.getMessage());
                                    }
                                } else if (Objects.equals(entry.getSts().getCd(), "PDNG")) {
                                    try {
                                        customerFundTransferService.updateStatus(interCustomerFundTransfer, _customerFndTransferEntity.get(), TransactionStatus.Pending, entry.getNtryRef());
                                        msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.PROCESSED), "");
                                    } catch (Exception e) {
                                        log.error("{}", e.getMessage());
                                        msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.UNPROCESSED), e.getMessage());
                                    }
                                }
                            } else {
                                try {
                                    msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.PROCESSED), "");
                                } catch (Exception e) {
                                    log.error("{}", e.getMessage());
                                    msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.UNPROCESSED), e.getMessage());
                                }
                            }
                        }
                    }
                } else if (inOutMsgLog.getMsgType().equals(MessageDefinitionIdentifier.PACS009.value())) {
                    Optional<InterBankTransferEntity> _interBankTransferEntity = interBankFundTransferRepository.findByBatchNumberAndIsDeletedFalse(inOutMsgLog.getBatchNumber());
                    if (_interBankTransferEntity.isPresent()) {
                        InterBankTransferEntity interBankFundTransfer = _interBankTransferEntity.get();
                        Optional<BankFndTransferEntity> _bankFndTransferEntity = bankFndTransferRepository.findByTransactionsAndParentBatchNumberAndIsDeletedFalse(interBankFundTransfer.getId(), interBankFundTransfer.getBatchNumber());
                        if (_bankFndTransferEntity.isPresent()) {
                            if (interBankFundTransfer.getVerificationStatus() == 3 && interBankFundTransfer.getTxnVerificationStatus().equals(TransactionVerificationStatus.Approved)) {
                                if (Objects.equals(entry.getSts().getCd(), "BOOK")) {
                                    try {
                                        bankFundTransferService.approveInwardBankFndTransfer(interBankFundTransfer, _bankFndTransferEntity.get());
                                        msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.PROCESSED), "");
                                    } catch (Exception e) {
                                        log.error("{}", e.getMessage());
                                        msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.UNPROCESSED), e.getMessage());
                                    }
                                } else if (entry.getSts().getCd().equals("PDNG")) {
                                    try {
                                        bankFundTransferService.updateStatus(interBankFundTransfer, _bankFndTransferEntity.get(), TransactionStatus.Pending, entry.getNtryRef());
                                        msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.PROCESSED), "");
                                    } catch (Exception e) {
                                        log.error("{}", e.getMessage());
                                        msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.UNPROCESSED), e.getMessage());
                                    }
                                }
                            } else {
                                try {
                                    msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.PROCESSED), "");
                                } catch (Exception e) {
                                    log.error("{}", e.getMessage());
                                    msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.UNPROCESSED), e.getMessage());
                                }
                            }
                        }
                    }
                }
            } else {
                Optional<InOutMsgLogEntity> inOutMsgLogEntityOptional = inOutMsgLogRepository.getInOutMsgLogByMsgIdAndDateAndRouTypeIncoming(entry.getNtryRef());
                if (inOutMsgLogEntityOptional.isPresent()) {
                    try {
                        msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.PROCESSED), "");
                    } catch (Exception e) {
                        log.error("{}", e.getMessage());
                        msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.UNPROCESSED), e.getMessage());
                    }
                } else {
                    msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.ARRIVED), "");
                }
            }
        }
    }
}
